package com.rnett.eve.ligraph.sde

import org.jetbrains.exposed.sql.transactions.transaction

val typeCache: Cache<Int, invtype?> = Cache {
    val type = transaction { invtypes.findFromPKs(it) }

    if (type != null)
        nameCache.put(type.typeName, type)

    type
}

fun invtypes.fromID(typeID: Int): invtype? = typeCache[typeID]

val nameCache = Cache<String, invtype?> {
    val type = transaction { invtype.find { invtypes.typeName eq it }.firstOrNull() }

    if (type != null)
        typeCache.put(type.typeID, type)

    type
}

fun invtypes.fromName(typeName: String): invtype? = nameCache[typeName]

fun invtype.imageURL(width: Int) = "https://image.eveonline.com/Type/${typeID}_$width.png"

fun invtype.renderURL(width: Int) = "https://image.eveonline.com/Render/${typeID}_$width.png"

//TODO moongo

val invtype.isMineral: Boolean
    get() = this.groupID == 18

val oreCache: MutableMap<invtype, Boolean> = mutableMapOf()

val invtype.isOre: Boolean
    get() = oreCache.computeIfAbsent(this@isOre) { transaction { this@isOre.group.categoryID == 25 } }
//

class Ore(val type: invtype) {

    val materials: ItemList

    val Tritanium: Long
    val Pyerite: Long
    val Mexallon: Long
    val Isogen: Long
    val Nocxium: Long
    val Zydrine: Long
    val Megacyte: Long
    val Morphite: Long

    init {
        if (!type.isOre)
            throw IllegalArgumentException("type must be an ore type!")

        materials = transaction { type.invtype_invtypematerials_type.map { Pair(it.materialType, it.quantity.toLong()) }.toMap() }



        Tritanium = materials.filterKeys { it.typeName == "Tritanium" }.values.firstOrNull() ?: 0

        Pyerite = materials.filterKeys { it.typeName == "Pyerite" }.values.firstOrNull() ?: 0

        Mexallon = materials.filterKeys { it.typeName == "Mexallon" }.values.firstOrNull() ?: 0

        Isogen = materials.filterKeys { it.typeName == "Isogen" }.values.firstOrNull() ?: 0

        Nocxium = materials.filterKeys { it.typeName == "Nocxium" }.values.firstOrNull() ?: 0

        Zydrine = materials.filterKeys { it.typeName == "Zydrine" }.values.firstOrNull() ?: 0

        Megacyte = materials.filterKeys { it.typeName == "Megacyte" }.values.firstOrNull() ?: 0

        Morphite = materials.filterKeys { it.typeName == "Morphite" }.values.firstOrNull() ?: 0
    }

    operator fun get(typeName: String): Long =
            materials.filterKeys { it.typeName == "typeName" }.values.firstOrNull() ?: 0


    operator fun get(type: invtype): Long = materials[type] ?: 0

}

//TODO store these in a file
val oreTypes: List<invtype> = transaction { invcategories.findFromPKs(25)!!.invcategory_invgroups_category.flatMap { it.invgroup_invtypes_group }.toList() }

val minerals: List<invtype> = transaction { invgroups.findFromPKs(18)!!.invgroup_invtypes_group.toList() }

val compressedOreTypes: List<invtype> = transaction { oreTypes.filter { it.typeName.contains("Compressed", true) } }

//val ores: List<com.rnett.eve.ligraph.sde.Ore> = transaction{ com.rnett.eve.ligraph.sde.invcategories.findFromPKs(25)!!.invcategory_invgroups_category.flatMap { it.invgroup_invtypes_group }.toList() }.map{com.rnett.eve.ligraph.sde.Ore(it)}

val compressedOres: List<Ore> = transaction { oreTypes.filter { it.typeName.contains("Compressed", true) } }.map { Ore(it) }
