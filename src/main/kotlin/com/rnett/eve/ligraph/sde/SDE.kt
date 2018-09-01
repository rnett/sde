package com.rnett.eve.ligraph.sde

import com.google.gson.TypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction


object industryactivityrecipes : IntIdTable(columnName = "typeID\" << 8 | \"activityID\" << 16 | \"productTypeID\" << 24 | \"materialTypeID") {

    // Database columns


    val typeID = integer("typeID")
    val activityID = integer("activityID")
    val productTypeID = integer("productTypeID")
    val productQuantity = integer("productQuantity")
    val materialTypeID = integer("materialTypeID")
    val materialQuantity = integer("materialQuantity")
    val probability = decimal("probability", 100, 100)


    // Foreign keys

    // Many to One
    val materialType = reference("materialTypeID", invtypes)
    val productType = reference("productTypeID", invtypes)
    val type = reference("typeID", invtypes)

    // Helper methods

    fun idFromPKs(typeID: Int, activityID: Int, productTypeID: Int, materialTypeID: Int): Int {
        return typeID shl 8 or activityID shl 16 or productTypeID shl 24 or materialTypeID
    }
    fun findFromPKs(typeID: Int, activityID: Int, productTypeID: Int, materialTypeID: Int): industryactivityrecipe? {
        return industryactivityrecipe.findById(idFromPKs(typeID, activityID, productTypeID, materialTypeID))
    }

}

class industryactivityrecipe(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<industryactivityrecipe>(industryactivityrecipes)

    // Database columns

    var typeID by industryactivityrecipes.typeID
    var activityID by industryactivityrecipes.activityID
    var productTypeID by industryactivityrecipes.productTypeID
    var productQuantity by industryactivityrecipes.productQuantity
    var materialTypeID by industryactivityrecipes.materialTypeID
    var materialQuantity by industryactivityrecipes.materialQuantity
    var probability by industryactivityrecipes.probability


    // Foreign keys

    // Many to One
    val materialType by invtype referencedOn industryactivityrecipes.materialType
    val productType by invtype referencedOn industryactivityrecipes.productType
    val type by invtype referencedOn industryactivityrecipes.type


    // Helper Methods

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is industryactivityrecipe)
            return false

        other
        return other.typeID == typeID && other.activityID == activityID && other.productTypeID == productTypeID && other.materialTypeID == materialTypeID
    }

    override fun hashCode(): Int {
        return industryactivityrecipes.idFromPKs(typeID, activityID, productTypeID, materialTypeID)
    }


}


object invtypes : IntIdTable(columnName = "typeID") {

    // Database columns


    val typeID = integer("typeID")
    val groupID = integer("groupID")
    var typeName = varchar("typeName", 100)
    val description = text("description")
    var mass = decimal("mass", 200, 200)
    var volume = decimal("volume", 200, 200)
    var capacity = decimal("capacity", 200, 200)
    val portionSize = integer("portionSize")
    val raceID = integer("raceID")
    var basePrice = decimal("basePrice", 19, 4)
    val published = bool("published")
    val marketGroupID = integer("marketGroupID")
    val iconID = integer("iconID")
    val soundID = integer("soundID")
    val graphicID = integer("graphicID")


    // Foreign keys

    // Many to One
    val group = reference("groupID", invgroups)
    val marketGroup = reference("marketGroupID", invmarketgroups)

    // One to Many (not present in object)


    // Helper methods

    fun findFromPKs(typeID: Int): invtype? {
        return invtype.findById(typeID)
    }

}

class InvTypeAdapter : TypeAdapter<invtype>() {
    override fun read(input: JsonReader): invtype? {
        input.beginObject()
        input.nextName()
        val c = transaction { invtypes.fromID(input.nextInt()) }
        input.endObject()

        return c
    }

    override fun write(out: JsonWriter, value: invtype) {
        out.beginObject()
        out.name("typeId").value(value.id.value)
        out.endObject()
    }
}

@JsonAdapter(InvTypeAdapter::class)
class invtype(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<invtype>(invtypes)

    // Database columns

    var typeID by invtypes.typeID
    var groupID by invtypes.groupID
    var typeName by invtypes.typeName
    var description by invtypes.description
    var mass by invtypes.mass
    var volume by invtypes.volume
    var capacity by invtypes.capacity
    var portionSize by invtypes.portionSize
    var raceID by invtypes.raceID
    var basePrice by invtypes.basePrice
    var published by invtypes.published
    var marketGroupID by invtypes.marketGroupID
    var iconID by invtypes.iconID
    var soundID by invtypes.soundID
    var graphicID by invtypes.graphicID


    // Foreign keys

    // Many to One
    val group by invgroup referencedOn invtypes.group
    val marketGroup by invmarketgroup referencedOn invtypes.marketGroup

    // One to Many
    val invtype_industryactivityrecipes_materialType by industryactivityrecipe referrersOn industryactivityrecipes.materialType
    val invtype_industryactivityrecipes_productType by industryactivityrecipe referrersOn industryactivityrecipes.productType
    val invtype_industryactivityrecipes_type by industryactivityrecipe referrersOn industryactivityrecipes.type
    val invtype_invtypematerials_materialType by invtypematerial referrersOn invtypematerials.materialType
    val invtype_invtypematerials_type by invtypematerial referrersOn invtypematerials.type
    val invtype_dgmtypeattributes_type by dgmtypeattribute referrersOn dgmtypeattributes.type
    val invtype_dgmtypeeffects_type by dgmtypeeffect referrersOn dgmtypeeffects.type
    val invtype_dgmexpressia_expressionType by dgmexpression referrersOn dgmexpressions.expressionType


    // Helper Methods

    override fun toString(): String {
        return this.typeName
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is invtype)
            return false

        other
        return other.typeID == typeID
    }

    override fun hashCode(): Int {
        return typeID
    }


}


object invgroups : IntIdTable(columnName = "groupID") {

    // Database columns


    val groupID = integer("groupID")
    val categoryID = integer("categoryID")
    var groupName = varchar("groupName", 100)
    val iconID = integer("iconID")
    val useBasePrice = bool("useBasePrice")
    val anchored = bool("anchored")
    val anchorable = bool("anchorable")
    val fittableNonSingleton = bool("fittableNonSingleton")
    val published = bool("published")


    // Foreign keys

    // Many to One
    val category = reference("categoryID", invcategories)

    // One to Many (not present in object)


    // Helper methods

    fun findFromPKs(groupID: Int): invgroup? {
        return invgroup.findById(groupID)
    }

}

class invgroup(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<invgroup>(invgroups)

    // Database columns

    var groupID by invgroups.groupID
    var categoryID by invgroups.categoryID
    var groupName by invgroups.groupName
    var iconID by invgroups.iconID
    var useBasePrice by invgroups.useBasePrice
    var anchored by invgroups.anchored
    var anchorable by invgroups.anchorable
    var fittableNonSingleton by invgroups.fittableNonSingleton
    var published by invgroups.published


    // Foreign keys

    // Many to One
    val category by invcategory referencedOn invgroups.category

    // One to Many
    val invgroup_invtypes_group by invtype referrersOn invtypes.group
    val invgroup_dgmexpressia_expressionGroup by dgmexpression referrersOn dgmexpressions.expressionGroup


    // Helper Methods

    override fun toString(): String {
        return this.groupName
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is invgroup)
            return false

        other
        return other.groupID == groupID
    }

    override fun hashCode(): Int {
        return groupID
    }


}


object invcategories : IntIdTable(columnName = "categoryID") {

    // Database columns


    val categoryID = integer("categoryID")
    var categoryName = varchar("categoryName", 100)
    val iconID = integer("iconID")
    val published = bool("published")


    // Foreign keys

    // One to Many (not present in object)


    // Helper methods

    fun findFromPKs(categoryID: Int): invcategory? {
        return invcategory.findById(categoryID)
    }

}

class invcategory(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<invcategory>(invcategories)

    // Database columns

    var categoryID by invcategories.categoryID
    var categoryName by invcategories.categoryName
    var iconID by invcategories.iconID
    var published by invcategories.published


    // Foreign keys

    // One to Many
    val invcategory_invgroups_category by invgroup referrersOn invgroups.category


    // Helper Methods

    override fun toString(): String {
        return this.categoryName
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is invcategory)
            return false

        other
        return other.categoryID == categoryID
    }

    override fun hashCode(): Int {
        return categoryID
    }


}


object invtypematerials : IntIdTable(columnName = "typeID\" << 8 | \"materialTypeID") {

    // Database columns


    val typeID = integer("typeID")
    val materialTypeID = integer("materialTypeID")
    val quantity = integer("quantity")


    // Foreign keys

    // Many to One
    val materialType = reference("materialTypeID", invtypes)
    val type = reference("typeID", invtypes)

    // Helper methods

    fun idFromPKs(typeID: Int, materialTypeID: Int): Int {
        return typeID shl 8 or materialTypeID
    }
    fun findFromPKs(typeID: Int, materialTypeID: Int): invtypematerial? {
        return invtypematerial.findById(idFromPKs(typeID, materialTypeID))
    }

}

class invtypematerial(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<invtypematerial>(invtypematerials)

    // Database columns

    var typeID by invtypematerials.typeID
    var materialTypeID by invtypematerials.materialTypeID
    var quantity by invtypematerials.quantity


    // Foreign keys

    // Many to One
    val materialType by invtype referencedOn invtypematerials.materialType
    val type by invtype referencedOn invtypematerials.type


    // Helper Methods

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is invtypematerial)
            return false

        other
        return other.typeID == typeID && other.materialTypeID == materialTypeID
    }

    override fun hashCode(): Int {
        return invtypematerials.idFromPKs(typeID, materialTypeID)
    }


}


object dgmattributetypes : IntIdTable(columnName = "attributeID") {

    // Database columns


    val attributeID = integer("attributeID")
    var attributeName = varchar("attributeName", 100)
    var description = varchar("description", 1000)
    val iconID = integer("iconID")
    var defaultValue = decimal("defaultValue", 200, 200)
    val published = bool("published")
    var displayName = varchar("displayName", 150)
    val unitID = integer("unitID")
    val stackable = bool("stackable")
    val highIsGood = bool("highIsGood")
    val categoryID = integer("categoryID")


    // Foreign keys

    // One to Many (not present in object)


    // Helper methods

    fun findFromPKs(attributeID: Int): dgmattributetype? {
        return dgmattributetype.findById(attributeID)
    }

}

class dgmattributetype(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<dgmattributetype>(dgmattributetypes)

    // Database columns

    var attributeID by dgmattributetypes.attributeID
    var attributeName by dgmattributetypes.attributeName
    var description by dgmattributetypes.description
    var iconID by dgmattributetypes.iconID
    var defaultValue by dgmattributetypes.defaultValue
    var published by dgmattributetypes.published
    var displayName by dgmattributetypes.displayName
    var unitID by dgmattributetypes.unitID
    var stackable by dgmattributetypes.stackable
    var highIsGood by dgmattributetypes.highIsGood
    var categoryID by dgmattributetypes.categoryID


    // Foreign keys

    // One to Many
    val dgmattributetype_dgmtypeattributes_attribute by dgmtypeattribute referrersOn dgmtypeattributes.attribute
    val dgmattributetype_dgmexpressia_expressionAttribute by dgmexpression referrersOn dgmexpressions.expressionAttribute


    // Helper Methods

    override fun toString(): String {
        return this.attributeName
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is dgmattributetype)
            return false

        other
        return other.attributeID == attributeID
    }

    override fun hashCode(): Int {
        return attributeID
    }


}


object dgmeffects : IntIdTable(columnName = "effectID") {

    // Database columns


    val effectID = integer("effectID")
    var effectName = varchar("effectName", 400)
    val effectCategory = integer("effectCategory")
    val preExpression = integer("preExpression").nullable()
    val postExpression = integer("postExpression").nullable()
    var description = varchar("description", 1000)
    var guid = varchar("guid", 60)
    val iconID = integer("iconID")
    val isOffensive = bool("isOffensive")
    val isAssistance = bool("isAssistance")
    val durationAttributeID = integer("durationAttributeID")
    val trackingSpeedAttributeID = integer("trackingSpeedAttributeID")
    val dischargeAttributeID = integer("dischargeAttributeID")
    val rangeAttributeID = integer("rangeAttributeID")
    val falloffAttributeID = integer("falloffAttributeID")
    val disallowAutoRepeat = bool("disallowAutoRepeat")
    val published = bool("published")
    var displayName = varchar("displayName", 100)
    val isWarpSafe = bool("isWarpSafe")
    val rangeChance = bool("rangeChance")
    val electronicChance = bool("electronicChance")
    val propulsionChance = bool("propulsionChance")
    val distribution = integer("distribution")
    var sfxName = varchar("sfxName", 20)
    val npcUsageChanceAttributeID = integer("npcUsageChanceAttributeID")
    val npcActivationChanceAttributeID = integer("npcActivationChanceAttributeID")
    val fittingUsageChanceAttributeID = integer("fittingUsageChanceAttributeID")
    val modifierInfo = text("modifierInfo")


    // Foreign keys

    // Many to One
    val postExpressionExpression = optReference("postExpression", dgmexpressions)
    val preExpressionExpression = optReference("preExpression", dgmexpressions)

    // One to Many (not present in object)


    // Helper methods

    fun findFromPKs(effectID: Int): dgmeffect? {
        return dgmeffect.findById(effectID)
    }

}

class dgmeffect(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<dgmeffect>(dgmeffects)

    // Database columns

    var effectID by dgmeffects.effectID
    var effectName by dgmeffects.effectName
    var effectCategory by dgmeffects.effectCategory
    var preExpression by dgmeffects.preExpression
    var postExpression by dgmeffects.postExpression
    var description by dgmeffects.description
    var guid by dgmeffects.guid
    var iconID by dgmeffects.iconID
    var isOffensive by dgmeffects.isOffensive
    var isAssistance by dgmeffects.isAssistance
    var durationAttributeID by dgmeffects.durationAttributeID
    var trackingSpeedAttributeID by dgmeffects.trackingSpeedAttributeID
    var dischargeAttributeID by dgmeffects.dischargeAttributeID
    var rangeAttributeID by dgmeffects.rangeAttributeID
    var falloffAttributeID by dgmeffects.falloffAttributeID
    var disallowAutoRepeat by dgmeffects.disallowAutoRepeat
    var published by dgmeffects.published
    var displayName by dgmeffects.displayName
    var isWarpSafe by dgmeffects.isWarpSafe
    var rangeChance by dgmeffects.rangeChance
    var electronicChance by dgmeffects.electronicChance
    var propulsionChance by dgmeffects.propulsionChance
    var distribution by dgmeffects.distribution
    var sfxName by dgmeffects.sfxName
    var npcUsageChanceAttributeID by dgmeffects.npcUsageChanceAttributeID
    var npcActivationChanceAttributeID by dgmeffects.npcActivationChanceAttributeID
    var fittingUsageChanceAttributeID by dgmeffects.fittingUsageChanceAttributeID
    var modifierInfo by dgmeffects.modifierInfo


    // Foreign keys

    // Many to One
    val postExpressionExpression by dgmexpression optionalReferencedOn dgmeffects.postExpressionExpression
    val preExpressionExpression by dgmexpression optionalReferencedOn dgmeffects.preExpressionExpression

    // One to Many
    val dgmeffect_dgmtypeeffects_effect by dgmtypeeffect referrersOn dgmtypeeffects.effect


    // Helper Methods

    override fun toString(): String {
        return this.effectName
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is dgmeffect)
            return false

        other
        return other.effectID == effectID
    }

    override fun hashCode(): Int {
        return effectID
    }


}


object dgmtypeattributes : IntIdTable(columnName = "typeID\" << 8 | \"attributeID") {

    // Database columns


    val typeID = integer("typeID")
    val attributeID = integer("attributeID")
    val valueInt = integer("valueInt")
    var valueFloat = decimal("valueFloat", 200, 200)


    // Foreign keys

    // Many to One
    val attribute = reference("attributeID", dgmattributetypes)
    val type = reference("typeID", invtypes)

    // Helper methods

    fun idFromPKs(typeID: Int, attributeID: Int): Int {
        return typeID shl 8 or attributeID
    }
    fun findFromPKs(typeID: Int, attributeID: Int): dgmtypeattribute? {
        return dgmtypeattribute.findById(idFromPKs(typeID, attributeID))
    }

}

class dgmtypeattribute(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<dgmtypeattribute>(dgmtypeattributes)

    // Database columns

    var typeID by dgmtypeattributes.typeID
    var attributeID by dgmtypeattributes.attributeID
    var valueInt by dgmtypeattributes.valueInt
    var valueFloat by dgmtypeattributes.valueFloat


    // Foreign keys

    // Many to One
    val attribute by dgmattributetype referencedOn dgmtypeattributes.attribute
    val type by invtype referencedOn dgmtypeattributes.type


    // Helper Methods

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is dgmtypeattribute)
            return false

        other
        return other.typeID == typeID && other.attributeID == attributeID
    }

    override fun hashCode(): Int {
        return dgmtypeattributes.idFromPKs(typeID, attributeID)
    }


}


object dgmtypeeffects : IntIdTable(columnName = "typeID\" << 8 | \"effectID") {

    // Database columns


    val typeID = integer("typeID")
    val effectID = integer("effectID")
    val isDefault = bool("isDefault")


    // Foreign keys

    // Many to One
    val effect = reference("effectID", dgmeffects)
    val type = reference("typeID", invtypes)

    // Helper methods

    fun idFromPKs(typeID: Int, effectID: Int): Int {
        return typeID shl 8 or effectID
    }
    fun findFromPKs(typeID: Int, effectID: Int): dgmtypeeffect? {
        return dgmtypeeffect.findById(idFromPKs(typeID, effectID))
    }

}

class dgmtypeeffect(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<dgmtypeeffect>(dgmtypeeffects)

    // Database columns

    var typeID by dgmtypeeffects.typeID
    var effectID by dgmtypeeffects.effectID
    var isDefault by dgmtypeeffects.isDefault


    // Foreign keys

    // Many to One
    val effect by dgmeffect referencedOn dgmtypeeffects.effect
    val type by invtype referencedOn dgmtypeeffects.type


    // Helper Methods

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is dgmtypeeffect)
            return false

        other
        return other.typeID == typeID && other.effectID == effectID
    }

    override fun hashCode(): Int {
        return dgmtypeeffects.idFromPKs(typeID, effectID)
    }


}


object invmarketgroups : IntIdTable(columnName = "marketGroupID") {

    // Database columns


    val marketGroupID = integer("marketGroupID")
    val parentGroupID = integer("parentGroupID")
    var marketGroupName = varchar("marketGroupName", 100)
    var description = varchar("description", 3000)
    val iconID = integer("iconID")
    val hasTypes = bool("hasTypes")


    // Foreign keys

    // Many to One
    val parentGroup = reference("parentGroupID", invmarketgroups)

    // One to Many (not present in object)


    // Helper methods

    fun findFromPKs(marketGroupID: Int): invmarketgroup? {
        return invmarketgroup.findById(marketGroupID)
    }

}

class invmarketgroup(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<invmarketgroup>(invmarketgroups)

    // Database columns

    var marketGroupID by invmarketgroups.marketGroupID
    var parentGroupID by invmarketgroups.parentGroupID
    var marketGroupName by invmarketgroups.marketGroupName
    var description by invmarketgroups.description
    var iconID by invmarketgroups.iconID
    var hasTypes by invmarketgroups.hasTypes


    // Foreign keys

    // Many to One
    val parentGroup by invmarketgroup referencedOn invmarketgroups.parentGroup

    // One to Many
    val invmarketgroup_invtypes_marketGroup by invtype referrersOn invtypes.marketGroup
    val invmarketgroup_invmarketgroups_parentGroup by invmarketgroup referrersOn invmarketgroups.parentGroup


    // Helper Methods

    override fun toString(): String {
        return this.marketGroupName
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is invmarketgroup)
            return false

        other
        return other.marketGroupID == marketGroupID
    }

    override fun hashCode(): Int {
        return marketGroupID
    }


}


object dgmexpressions : IntIdTable(columnName = "expressionID") {

    // Database columns


    val expressionID = integer("expressionID")
    val operandID = integer("operandID")
    val arg1 = integer("arg1").nullable()
    val arg2 = integer("arg2").nullable()
    var expressionValue = varchar("expressionValue", 100)
    var description = varchar("description", 1000)
    var expressionName = varchar("expressionName", 500)
    val expressionTypeID = integer("expressionTypeID")
    val expressionGroupID = integer("expressionGroupID")
    val expressionAttributeID = integer("expressionAttributeID")


    // Foreign keys

    // Many to One
    val expressionAttribute = reference("expressionAttributeID", dgmattributetypes)
    val arg1Expression = optReference("", dgmexpressions)
    val arg2Expression = optReference("", dgmexpressions)
    val expressionGroup = reference("expressionGroupID", invgroups)
    val expressionType = reference("expressionTypeID", invtypes)

    // One to Many (not present in object)


    // Helper methods

    fun findFromPKs(expressionID: Int): dgmexpression? {
        return dgmexpression.findById(expressionID)
    }

}

class dgmexpression(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<dgmexpression>(dgmexpressions)

    // Database columns

    var expressionID by dgmexpressions.expressionID
    var operandID by dgmexpressions.operandID
    var arg1 by dgmexpressions.arg1
    var arg2 by dgmexpressions.arg2
    var expressionValue by dgmexpressions.expressionValue
    var description by dgmexpressions.description
    var expressionName by dgmexpressions.expressionName
    var expressionTypeID by dgmexpressions.expressionTypeID
    var expressionGroupID by dgmexpressions.expressionGroupID
    var expressionAttributeID by dgmexpressions.expressionAttributeID


    // Foreign keys

    // Many to One
    val expressionAttribute by dgmattributetype referencedOn dgmexpressions.expressionAttribute
    val arg1Expression by dgmexpression optionalReferencedOn dgmexpressions.arg1Expression
    val arg2Expression by dgmexpression optionalReferencedOn dgmexpressions.arg2Expression
    val expressionGroup by invgroup referencedOn dgmexpressions.expressionGroup
    val expressionType by invtype referencedOn dgmexpressions.expressionType

    // One to Many
    val dgmexpression_dgmeffects_postExpression by dgmeffect optionalReferrersOn dgmeffects.postExpressionExpression
    val dgmexpression_dgmeffects_preExpression by dgmeffect optionalReferrersOn dgmeffects.preExpressionExpression
    val dgmexpression_arg1s by dgmexpression optionalReferrersOn dgmexpressions.arg1Expression
    val dgmexpression_arg2s by dgmexpression optionalReferrersOn dgmexpressions.arg2Expression


    // Helper Methods

    override fun toString(): String {
        return this.expressionName
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is dgmexpression)
            return false

        other
        return other.expressionID == expressionID
    }

    override fun hashCode(): Int {
        return expressionID
    }


}


object mapsolarsystems : IntIdTable(columnName = "solarSystemID") {

    // Database columns


    val regionID = integer("regionID")
    val constellationID = integer("constellationID")
    val solarSystemID = integer("solarSystemID")
    var solarSystemName = varchar("solarSystemName", 100)
    var x = decimal("x", 200, 200)
    var y = decimal("y", 200, 200)
    var z = decimal("z", 200, 200)
    var xMin = decimal("xMin", 200, 200)
    var xMax = decimal("xMax", 200, 200)
    var yMin = decimal("yMin", 200, 200)
    var yMax = decimal("yMax", 200, 200)
    var zMin = decimal("zMin", 200, 200)
    var zMax = decimal("zMax", 200, 200)
    var luminosity = decimal("luminosity", 200, 200)
    val border = bool("border")
    val fringe = bool("fringe")
    val corridor = bool("corridor")
    val hub = bool("hub")
    val international = bool("international")
    val regional = bool("regional")
    val hasConstellation = bool("constellation")
    var security = decimal("security", 200, 200)
    val factionID = integer("factionID")
    var radius = decimal("radius", 200, 200)
    val sunTypeID = integer("sunTypeID")
    var securityClass = varchar("securityClass", 2)


    // Foreign keys

    // Many to One
    val constellation = reference("constellationID", mapconstellations)
    val region = reference("regionID", mapregions)

    // One to Many (not present in object)


    // Helper methods

    fun findFromPKs(solarSystemID: Int): mapsolarsystem? {
        return mapsolarsystem.findById(solarSystemID)
    }

}

class mapsolarsystem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<mapsolarsystem>(mapsolarsystems)

    // Database columns

    var regionID by mapsolarsystems.regionID
    var constellationID by mapsolarsystems.constellationID
    var solarSystemID by mapsolarsystems.solarSystemID
    var solarSystemName by mapsolarsystems.solarSystemName
    var x by mapsolarsystems.x
    var y by mapsolarsystems.y
    var z by mapsolarsystems.z
    var xMin by mapsolarsystems.xMin
    var xMax by mapsolarsystems.xMax
    var yMin by mapsolarsystems.yMin
    var yMax by mapsolarsystems.yMax
    var zMin by mapsolarsystems.zMin
    var zMax by mapsolarsystems.zMax
    var luminosity by mapsolarsystems.luminosity
    var border by mapsolarsystems.border
    var fringe by mapsolarsystems.fringe
    var corridor by mapsolarsystems.corridor
    var hub by mapsolarsystems.hub
    var international by mapsolarsystems.international
    var regional by mapsolarsystems.regional
    var hasConstellation by mapsolarsystems.constellation
    var security by mapsolarsystems.security
    var factionID by mapsolarsystems.factionID
    var radius by mapsolarsystems.radius
    var sunTypeID by mapsolarsystems.sunTypeID
    var securityClass by mapsolarsystems.securityClass


    // Foreign keys

    // Many to One
    val constellation by mapconstellation referencedOn mapsolarsystems.constellation
    val region by mapregion referencedOn mapsolarsystems.region

    // One to Many
    val mapsolarsystem_mapsolarsystemjumps_fromSolarSystem by mapsolarsystemjump referrersOn mapsolarsystemjumps.fromSolarSystem
    val mapsolarsystem_mapsolarsystemjumps_toSolarSystem by mapsolarsystemjump referrersOn mapsolarsystemjumps.toSolarSystem


    // Helper Methods

    override fun toString(): String {
        return this.solarSystemName
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is mapsolarsystem)
            return false

        other
        return other.solarSystemID == solarSystemID
    }

    override fun hashCode(): Int {
        return solarSystemID
    }


}


object mapsolarsystemjumps : IntIdTable(columnName = "fromSolarSystemID\" << 8 | \"toSolarSystemID") {

    // Database columns


    val fromRegionID = integer("fromRegionID")
    val fromConstellationID = integer("fromConstellationID")
    val fromSolarSystemID = integer("fromSolarSystemID")
    val toSolarSystemID = integer("toSolarSystemID")
    val toConstellationID = integer("toConstellationID")
    val toRegionID = integer("toRegionID")


    // Foreign keys

    // Many to One
    val fromConstellation = reference("fromConstellationID", mapconstellations)
    val toConstellation = reference("toConstellationID", mapconstellations)
    val fromRegion = reference("fromRegionID", mapregions)
    val toRegion = reference("toRegionID", mapregions)
    val fromSolarSystem = reference("fromSolarSystemID", mapsolarsystems)
    val toSolarSystem = reference("toSolarSystemID", mapsolarsystems)

    // Helper methods

    fun idFromPKs(fromSolarSystemID: Int, toSolarSystemID: Int): Int {
        return fromSolarSystemID shl 8 or toSolarSystemID
    }

    fun findFromPKs(fromSolarSystemID: Int, toSolarSystemID: Int): mapsolarsystemjump? {
        return mapsolarsystemjump.findById(idFromPKs(fromSolarSystemID, toSolarSystemID))
    }

}

class mapsolarsystemjump(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<mapsolarsystemjump>(mapsolarsystemjumps)

    // Database columns

    var fromRegionID by mapsolarsystemjumps.fromRegionID
    var fromConstellationID by mapsolarsystemjumps.fromConstellationID
    var fromSolarSystemID by mapsolarsystemjumps.fromSolarSystemID
    var toSolarSystemID by mapsolarsystemjumps.toSolarSystemID
    var toConstellationID by mapsolarsystemjumps.toConstellationID
    var toRegionID by mapsolarsystemjumps.toRegionID


    // Foreign keys

    // Many to One
    val fromConstellation by mapconstellation referencedOn mapsolarsystemjumps.fromConstellation
    val toConstellation by mapconstellation referencedOn mapsolarsystemjumps.toConstellation
    val fromRegion by mapregion referencedOn mapsolarsystemjumps.fromRegion
    val toRegion by mapregion referencedOn mapsolarsystemjumps.toRegion
    val fromSolarSystem by mapsolarsystem referencedOn mapsolarsystemjumps.fromSolarSystem
    val toSolarSystem by mapsolarsystem referencedOn mapsolarsystemjumps.toSolarSystem


    // Helper Methods

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is mapsolarsystemjump)
            return false

        other
        return other.fromSolarSystemID == fromSolarSystemID && other.toSolarSystemID == toSolarSystemID
    }

    override fun hashCode(): Int {
        return mapsolarsystemjumps.idFromPKs(fromSolarSystemID, toSolarSystemID)
    }


}


object mapregions : IntIdTable(columnName = "regionID") {

    // Database columns


    val regionID = integer("regionID")
    var regionName = varchar("regionName", 100)
    var x = decimal("x", 200, 200)
    var y = decimal("y", 200, 200)
    var z = decimal("z", 200, 200)
    var xMin = decimal("xMin", 200, 200)
    var xMax = decimal("xMax", 200, 200)
    var yMin = decimal("yMin", 200, 200)
    var yMax = decimal("yMax", 200, 200)
    var zMin = decimal("zMin", 200, 200)
    var zMax = decimal("zMax", 200, 200)
    val factionID = integer("factionID")
    var radius = decimal("radius", 200, 200)


    // Foreign keys

    // One to Many (not present in object)


    // Helper methods

    fun findFromPKs(regionID: Int): mapregion? {
        return mapregion.findById(regionID)
    }

}

class mapregion(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<mapregion>(mapregions)

    // Database columns

    var regionID by mapregions.regionID
    var regionName by mapregions.regionName
    var x by mapregions.x
    var y by mapregions.y
    var z by mapregions.z
    var xMin by mapregions.xMin
    var xMax by mapregions.xMax
    var yMin by mapregions.yMin
    var yMax by mapregions.yMax
    var zMin by mapregions.zMin
    var zMax by mapregions.zMax
    var factionID by mapregions.factionID
    var radius by mapregions.radius


    // Foreign keys

    // One to Many
    val mapregion_mapsolarsystems_region by mapsolarsystem referrersOn mapsolarsystems.region
    val mapregion_mapsolarsystemjumps_fromRegion by mapsolarsystemjump referrersOn mapsolarsystemjumps.fromRegion
    val mapregion_mapsolarsystemjumps_toRegion by mapsolarsystemjump referrersOn mapsolarsystemjumps.toRegion
    val mapregion_mapregionjumps_fromRegion by mapregionjump referrersOn mapregionjumps.fromRegion
    val mapregion_mapregionjumps_toRegion by mapregionjump referrersOn mapregionjumps.toRegion
    val mapregion_mapconstellatia_region by mapconstellation referrersOn mapconstellations.region
    val mapregion_mapconstellationjumps_fromRegion by mapconstellationjump referrersOn mapconstellationjumps.fromRegion
    val mapregion_mapconstellationjumps_toRegion by mapconstellationjump referrersOn mapconstellationjumps.toRegion


    // Helper Methods

    override fun toString(): String {
        return this.regionName
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is mapregion)
            return false

        other
        return other.regionID == regionID
    }

    override fun hashCode(): Int {
        return regionID
    }


}


object mapregionjumps : IntIdTable(columnName = "fromRegionID\" << 8 | \"toRegionID") {

    // Database columns


    val fromRegionID = integer("fromRegionID")
    val toRegionID = integer("toRegionID")


    // Foreign keys

    // Many to One
    val fromRegion = reference("fromRegionID", mapregions)
    val toRegion = reference("toRegionID", mapregions)

    // Helper methods

    fun idFromPKs(fromRegionID: Int, toRegionID: Int): Int {
        return fromRegionID shl 8 or toRegionID
    }

    fun findFromPKs(fromRegionID: Int, toRegionID: Int): mapregionjump? {
        return mapregionjump.findById(idFromPKs(fromRegionID, toRegionID))
    }

}

class mapregionjump(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<mapregionjump>(mapregionjumps)

    // Database columns

    var fromRegionID by mapregionjumps.fromRegionID
    var toRegionID by mapregionjumps.toRegionID


    // Foreign keys

    // Many to One
    val fromRegion by mapregion referencedOn mapregionjumps.fromRegion
    val toRegion by mapregion referencedOn mapregionjumps.toRegion


    // Helper Methods

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is mapregionjump)
            return false

        other
        return other.fromRegionID == fromRegionID && other.toRegionID == toRegionID
    }

    override fun hashCode(): Int {
        return mapregionjumps.idFromPKs(fromRegionID, toRegionID)
    }


}


object mapconstellations : IntIdTable(columnName = "constellationID") {

    // Database columns


    val regionID = integer("regionID")
    val constellationID = integer("constellationID")
    var constellationName = varchar("constellationName", 100)
    var x = decimal("x", 200, 200)
    var y = decimal("y", 200, 200)
    var z = decimal("z", 200, 200)
    var xMin = decimal("xMin", 200, 200)
    var xMax = decimal("xMax", 200, 200)
    var yMin = decimal("yMin", 200, 200)
    var yMax = decimal("yMax", 200, 200)
    var zMin = decimal("zMin", 200, 200)
    var zMax = decimal("zMax", 200, 200)
    val factionID = integer("factionID")
    var radius = decimal("radius", 200, 200)


    // Foreign keys

    // Many to One
    val region = reference("regionID", mapregions)

    // One to Many (not present in object)


    // Helper methods

    fun findFromPKs(constellationID: Int): mapconstellation? {
        return mapconstellation.findById(constellationID)
    }

}

class mapconstellation(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<mapconstellation>(mapconstellations)

    // Database columns

    var regionID by mapconstellations.regionID
    var constellationID by mapconstellations.constellationID
    var constellationName by mapconstellations.constellationName
    var x by mapconstellations.x
    var y by mapconstellations.y
    var z by mapconstellations.z
    var xMin by mapconstellations.xMin
    var xMax by mapconstellations.xMax
    var yMin by mapconstellations.yMin
    var yMax by mapconstellations.yMax
    var zMin by mapconstellations.zMin
    var zMax by mapconstellations.zMax
    var factionID by mapconstellations.factionID
    var radius by mapconstellations.radius


    // Foreign keys

    // Many to One
    val region by mapregion referencedOn mapconstellations.region

    // One to Many
    val mapconstellation_mapsolarsystems_constellation by mapsolarsystem referrersOn mapsolarsystems.constellation
    val mapconstellation_mapsolarsystemjumps_fromConstellation by mapsolarsystemjump referrersOn mapsolarsystemjumps.fromConstellation
    val mapconstellation_mapsolarsystemjumps_toConstellation by mapsolarsystemjump referrersOn mapsolarsystemjumps.toConstellation
    val mapconstellation_mapconstellationjumps_fromConstellation by mapconstellationjump referrersOn mapconstellationjumps.fromConstellation
    val mapconstellation_mapconstellationjumps_toConstellation by mapconstellationjump referrersOn mapconstellationjumps.toConstellation


    // Helper Methods

    override fun toString(): String {
        return this.constellationName
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is mapconstellation)
            return false

        other
        return other.constellationID == constellationID
    }

    override fun hashCode(): Int {
        return constellationID
    }


}


object mapconstellationjumps : IntIdTable(columnName = "fromConstellationID\" << 8 | \"toConstellationID") {

    // Database columns


    val fromRegionID = integer("fromRegionID")
    val fromConstellationID = integer("fromConstellationID")
    val toConstellationID = integer("toConstellationID")
    val toRegionID = integer("toRegionID")


    // Foreign keys

    // Many to One
    val fromConstellation = reference("fromConstellationID", mapconstellations)
    val toConstellation = reference("toConstellationID", mapconstellations)
    val fromRegion = reference("fromRegionID", mapregions)
    val toRegion = reference("toRegionID", mapregions)

    // Helper methods

    fun idFromPKs(fromConstellationID: Int, toConstellationID: Int): Int {
        return fromConstellationID shl 8 or toConstellationID
    }

    fun findFromPKs(fromConstellationID: Int, toConstellationID: Int): mapconstellationjump? {
        return mapconstellationjump.findById(idFromPKs(fromConstellationID, toConstellationID))
    }

}

class mapconstellationjump(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<mapconstellationjump>(mapconstellationjumps)

    // Database columns

    var fromRegionID by mapconstellationjumps.fromRegionID
    var fromConstellationID by mapconstellationjumps.fromConstellationID
    var toConstellationID by mapconstellationjumps.toConstellationID
    var toRegionID by mapconstellationjumps.toRegionID


    // Foreign keys

    // Many to One
    val fromConstellation by mapconstellation referencedOn mapconstellationjumps.fromConstellation
    val toConstellation by mapconstellation referencedOn mapconstellationjumps.toConstellation
    val fromRegion by mapregion referencedOn mapconstellationjumps.fromRegion
    val toRegion by mapregion referencedOn mapconstellationjumps.toRegion


    // Helper Methods

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is mapconstellationjump)
            return false

        other
        return other.fromConstellationID == fromConstellationID && other.toConstellationID == toConstellationID
    }

    override fun hashCode(): Int {
        return mapconstellationjumps.idFromPKs(fromConstellationID, toConstellationID)
    }


}
