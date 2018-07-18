package com.rnett.eve.ligraph.sde

import com.github.salomonbrys.kotson.get
import com.google.gson.JsonParser
import com.kizitonwose.time.Interval
import com.kizitonwose.time.Millisecond
import com.kizitonwose.time.minutes
import com.rnett.core.Cache
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.post
import kotlinx.coroutines.experimental.runBlocking
import java.net.URLEncoder
import java.util.*

typealias ItemList = Map<invtype, Long>

fun ItemList.toMutableItemList() = this.toMutableMap()

typealias MutableItemList = MutableMap<invtype, Long>

fun MutableItemList.parse(raw: String) {
    this.clear()

    var lines = raw.split("\n").filter { !it.isBlank() }

    if (lines.isEmpty())
        lines = listOf(raw)

    lines.forEach {
        when {
            it.contains(Regex("x[0-9]")) -> // $type x$amount or $typex$amount
                try {
                    this[invtypes.fromName(it.substringBeforeLast("x").trim())!!] = it.substringAfterLast("x").toLong()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            it.contains(Regex("[ \t][0-9]")) -> // $type $amount$type x$amount
                try {
                    this[invtypes.fromName(it.replace("\t", " ").substringBeforeLast(" ").trim())!!] =
                            it.replace("\t", " ").substringAfterLast(" ").toLong()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            !it.isBlank() -> // $type
                try {
                    this[invtypes.fromName(it.trim())!!] = 1
                } catch (e: Exception) {
                    e.printStackTrace()
                }
        }
    }
}

data class Price(val buy: Double, val sell: Double) {
    operator fun plus(p: Price): Price {
        return Price(buy + p.buy, sell + p.sell)
    }

    operator fun minus(p: Price): Price {
        return Price(buy - p.buy, sell - p.sell)
    }

    operator fun times(amount: Double): Price {
        return Price(buy * amount, sell * amount)
    }

    operator fun div(amount: Double): Price {
        return Price(buy / amount, sell / amount)
    }

    operator fun times(amount: Int): Price {
        return Price(buy * amount, sell * amount)
    }

    operator fun div(amount: Int): Price {
        return Price(buy / amount, sell / amount)
    }

    operator fun times(amount: Long): Price {
        return Price(buy * amount, sell * amount)
    }

    operator fun div(amount: Long): Price {
        return Price(buy / amount, sell / amount)
    }
}

fun <E : Collection<Price>> E.sell() = this.map { it.sell }
fun <E : Collection<Price>> E.buy() = this.map { it.sell }

fun <T, E : Map<T, Price>> E.sell() = this.mapValues { it.value.sell }
fun <T, E : Map<T, Price>> E.buy() = this.mapValues { it.value.buy }

val priceCache: MutableMap<invtype, Pair<Price, Long>> = mutableMapOf()

fun getPrices(types: List<invtype>): Map<invtype, Price> {
    val neededPrices: MutableList<invtype> = mutableListOf()

    // check cache
    types.forEach {
        if (!priceCache.containsKey(it))
            neededPrices.add(it)
        else {
            val p = priceCache[it]!!
            if (Interval<Millisecond>(Calendar.getInstance().timeInMillis - p.second) > 30.minutes) {
                priceCache.remove(it)
                neededPrices.add(it)
            }
        }
    }

    // update cache
    if (neededPrices.size > 0) {
        val client = HttpClient(Apache)

        val url = "https://evepraisal.com/appraisal.json?market=jita&raw_textarea=${URLEncoder.encode(neededPrices
                .joinToString("%0A") { it.typeName })}${"&persist=no"}"

        val text: String = runBlocking {
            client.post<String>(url) {
                headers["User-Agent"] = "Ligraph/jnett96@gmail.com.  Calls are cached."
            }
        }

        val json = JsonParser().parse(text).asJsonObject

        val newPricesIDs = json["appraisal"]["items"].asJsonArray.map {
            Pair(it["typeID"].asInt,
                    Price(it["prices"]["buy"]["max"].asDouble,
                            it["prices"]["sell"]["min"].asDouble)
            )
        }.toMap()

        val newPrices = neededPrices.map { Pair(it, newPricesIDs[it.typeID]!!) }.toMap()
        newPrices.entries.forEach {
            priceCache[it.key] = Pair(it.value, Calendar.getInstance().timeInMillis)
        }
    }
    return types.map { Pair(it, priceCache[it]!!.first) }.toMap()
}

data class Appraisal(val prices: Map<invtype, Price>, val amounts: ItemList) {
    val totalPrices: Map<invtype, Price> = prices.mapValues { it.value * (amounts[it.key] ?: 0) }
    val totalPrice: Price = totalPrices.values.reduce { a, b -> a + b }
}

fun appraise(types: List<invtype>) = types.map { Pair(it, 1.toLong()) }.toMap().appraise()

fun ItemList.appraise() = Appraisal(getPrices(), this)

fun ItemList.getPrices() = getPrices(this.keys.toList())

val evepraisalCache = Cache<ItemList, String>(10.minutes) {
    val client = HttpClient(Apache)

    val url = "https://evepraisal.com/appraisal.json?market=jita&raw_textarea=${URLEncoder.encode(it.entries
            .joinToString("%0A") { it.key.typeName + "%09" + it.value.toString() })}"

    val text: String = runBlocking {
        client.post<String>(url) {
            headers["User-Agent"] = "Ligraph/jnett96@gmail.com.  Calls are cached."
        }
    }
    JsonParser().parse(text).asJsonObject["appraisal"]["id"].asString
}

fun ItemList.evePraisal(): String = evepraisalCache[this]
