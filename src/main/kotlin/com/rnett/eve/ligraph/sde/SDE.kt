package com.rnett.eve.ligraph.sde

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable


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
    val preExpression = integer("preExpression")
    val postExpression = integer("postExpression")
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
    val postExpressionExpression = reference("postExpression", dgmexpressions)
    val preExpressionExpression = reference("preExpression", dgmexpressions)

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
    val postExpressionExpression by dgmexpression referencedOn dgmeffects.postExpressionExpression
    val preExpressionExpression by dgmexpression referencedOn dgmeffects.preExpressionExpression

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
    val arg1 = integer("arg1")
    val arg2 = integer("arg2")
    var expressionValue = varchar("expressionValue", 100)
    var description = varchar("description", 1000)
    var expressionName = varchar("expressionName", 500)
    val expressionTypeID = integer("expressionTypeID")
    val expressionGroupID = integer("expressionGroupID")
    val expressionAttributeID = integer("expressionAttributeID")


    // Foreign keys

    // Many to One
    val expressionAttribute = reference("expressionAttributeID", dgmattributetypes)
    val arg1Expression = reference("arg1", dgmexpressions)
    val arg2Expression = reference("arg2", dgmexpressions)
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
    val arg1Expression by dgmexpression referencedOn dgmexpressions.arg1Expression
    val arg2Expression by dgmexpression referencedOn dgmexpressions.arg2Expression
    val expressionGroup by invgroup referencedOn dgmexpressions.expressionGroup
    val expressionType by invtype referencedOn dgmexpressions.expressionType

    // One to Many
    val dgmexpression_dgmeffects_postExpression by dgmeffect referrersOn dgmeffects.postExpressionExpression
    val dgmexpression_dgmeffects_preExpression by dgmeffect referrersOn dgmeffects.preExpressionExpression
    val dgmexpression_arg1s_ by dgmexpression referrersOn dgmexpressions.arg1Expression
    val dgmexpression_arg2s_ by dgmexpression referrersOn dgmexpressions.arg2Expression


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
