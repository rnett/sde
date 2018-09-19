create table if not exists agtagents
(
	"agentID" integer not null
		constraint "agtAgents_pkey"
			primary key,
	"divisionID" integer,
	"corporationID" integer,
	"locationID" integer,
	level integer,
	quality integer,
	"agentTypeID" integer,
	"isLocator" boolean
);

create index if not exists "ix_agtAgents_corporationID"
	on agtagents ("corporationID");
create index if not exists "ix_agtAgents_locationID"
	on agtagents ("locationID");
create table if not exists agtagenttypes
(
	"agentTypeID" integer not null
		constraint "agtAgentTypes_pkey"
			primary key,
	"agentType" varchar(50)
);

create table if not exists agtresearchagents
(
	"agentID" integer not null,
	"typeID" integer not null,
	constraint "agtResearchAgents_pkey"
		primary key ("agentID", "typeID")
);

create index if not exists "ix_agtResearchAgents_typeID"
	on agtresearchagents ("typeID");
create table if not exists certcerts
(
	"certID" integer not null
		constraint "certCerts_pkey"
			primary key,
	description text,
	"groupID" integer,
	name varchar(255)
);

create table if not exists certmasteries
(
	"typeID" integer,
	"masteryLevel" integer,
	"certID" integer
);

create table if not exists certskills
(
	"certID" integer,
	"skillID" integer,
	"certLevelInt" integer,
	"skillLevel" integer,
	"certLevelText" varchar(8)
);

create index if not exists "ix_certSkills_skillID"
	on certskills ("skillID");
create table if not exists chrancestries
(
	"ancestryID" integer not null
		constraint "chrAncestries_pkey"
			primary key,
	"ancestryName" varchar(100),
	"bloodlineID" integer,
	description varchar(1000),
	perception integer,
	willpower integer,
	charisma integer,
	memory integer,
	intelligence integer,
	"iconID" integer,
	"shortDescription" varchar(500)
);

create table if not exists chrattributes
(
	"attributeID" integer not null
		constraint "chrAttributes_pkey"
			primary key,
	"attributeName" varchar(100),
	description varchar(1000),
	"iconID" integer,
	"shortDescription" varchar(500),
	notes varchar(500)
);

create table if not exists chrbloodlines
(
	"bloodlineID" integer not null
		constraint "chrBloodlines_pkey"
			primary key,
	"bloodlineName" varchar(100),
	"raceID" integer,
	description varchar(1000),
	"maleDescription" varchar(1000),
	"femaleDescription" varchar(1000),
	"shipTypeID" integer,
	"corporationID" integer,
	perception integer,
	willpower integer,
	charisma integer,
	memory integer,
	intelligence integer,
	"iconID" integer,
	"shortDescription" varchar(500),
	"shortMaleDescription" varchar(500),
	"shortFemaleDescription" varchar(500)
);

create table if not exists chrfactions
(
	"factionID" integer not null
		constraint "chrFactions_pkey"
			primary key,
	"factionName" varchar(100),
	description varchar(1000),
	"raceIDs" integer,
	"solarSystemID" integer,
	"corporationID" integer,
	"sizeFactor" double precision,
	"stationCount" integer,
	"stationSystemCount" integer,
	"militiaCorporationID" integer,
	"iconID" integer
);

create table if not exists chrraces
(
	"raceID" integer not null
		constraint "chrRaces_pkey"
			primary key,
	"raceName" varchar(100),
	description varchar(1000),
	"iconID" integer,
	"shortDescription" varchar(500)
);

create table if not exists crpactivities
(
	"activityID" integer not null
		constraint "crpActivities_pkey"
			primary key,
	"activityName" varchar(100),
	description varchar(1000)
);

create table if not exists crpnpccorporationdivisions
(
	"corporationID" integer not null,
	"divisionID" integer not null,
	size integer,
	constraint "crpNPCCorporationDivisions_pkey"
		primary key ("corporationID", "divisionID")
);

create table if not exists crpnpccorporationresearchfields
(
	"skillID" integer not null,
	"corporationID" integer not null,
	constraint "crpNPCCorporationResearchFields_pkey"
		primary key ("skillID", "corporationID")
);

create table if not exists crpnpccorporations
(
	"corporationID" integer not null
		constraint "crpNPCCorporations_pkey"
			primary key,
	size char,
	extent char,
	"solarSystemID" integer,
	"investorID1" integer,
	"investorShares1" integer,
	"investorID2" integer,
	"investorShares2" integer,
	"investorID3" integer,
	"investorShares3" integer,
	"investorID4" integer,
	"investorShares4" integer,
	"friendID" integer,
	"enemyID" integer,
	"publicShares" integer,
	"initialPrice" integer,
	"minSecurity" double precision,
	scattered boolean,
	fringe integer,
	corridor integer,
	hub integer,
	border integer,
	"factionID" integer,
	"sizeFactor" double precision,
	"stationCount" integer,
	"stationSystemCount" integer,
	description varchar(4000),
	"iconID" integer
);

create table if not exists crpnpccorporationtrades
(
	"corporationID" integer not null,
	"typeID" integer not null,
	constraint "crpNPCCorporationTrades_pkey"
		primary key ("corporationID", "typeID")
);

create table if not exists crpnpcdivisions
(
	"divisionID" integer not null
		constraint "crpNPCDivisions_pkey"
			primary key,
	"divisionName" varchar(100),
	description varchar(1000),
	"leaderType" varchar(100)
);

create table if not exists dgmattributecategories
(
	"categoryID" integer not null
		constraint "dgmAttributeCategories_pkey"
			primary key,
	"categoryName" varchar(50),
	"categoryDescription" varchar(200)
);

create table if not exists dgmattributetypes
(
	"attributeID" integer not null
		constraint "dgmAttributeTypes_pkey"
			primary key,
	"attributeName" varchar(100),
	description varchar(1000),
	"iconID" integer,
	"defaultValue" double precision,
	published boolean,
	"displayName" varchar(150),
	"unitID" integer,
	stackable boolean,
	"highIsGood" boolean,
	"categoryID" integer
);

create table if not exists dgmeffects
(
	"effectID" integer not null
		constraint "dgmEffects_pkey"
			primary key,
	"effectName" varchar(400),
	"effectCategory" integer,
	"preExpression" integer,
	"postExpression" integer,
	description varchar(1000),
	guid varchar(60),
	"iconID" integer,
	"isOffensive" boolean,
	"isAssistance" boolean,
	"durationAttributeID" integer,
	"trackingSpeedAttributeID" integer,
	"dischargeAttributeID" integer,
	"rangeAttributeID" integer,
	"falloffAttributeID" integer,
	"disallowAutoRepeat" boolean,
	published boolean,
	"displayName" varchar(100),
	"isWarpSafe" boolean,
	"rangeChance" boolean,
	"electronicChance" boolean,
	"propulsionChance" boolean,
	distribution integer,
	"sfxName" varchar(20),
	"npcUsageChanceAttributeID" integer,
	"npcActivationChanceAttributeID" integer,
	"fittingUsageChanceAttributeID" integer,
	"modifierInfo" text
);

create table if not exists evegraphics
(
	"graphicID" integer not null
		constraint "eveGraphics_pkey"
			primary key,
	"sofFactionName" varchar(100),
	"graphicFile" varchar(100),
	"sofHullName" varchar(100),
	"sofRaceName" varchar(100),
	description text
);

create table if not exists eveicons
(
	"iconID" integer not null
		constraint "eveIcons_pkey"
			primary key,
	"iconFile" varchar(500),
	description text
);

create table if not exists eveunits
(
	"unitID" integer not null
		constraint "eveUnits_pkey"
			primary key,
	"unitName" varchar(100),
	"displayName" varchar(50),
	description varchar(1000)
);

create table if not exists industryactivityraces
(
	"typeID" integer,
	"activityID" integer,
	"productTypeID" integer,
	"raceID" integer
);

create index if not exists "ix_industryActivityRaces_productTypeID"
	on industryactivityraces ("productTypeID");
create index if not exists "ix_industryActivityRaces_typeID"
	on industryactivityraces ("typeID");
create table if not exists industryblueprints
(
	"typeID" integer not null
		constraint "industryBlueprints_pkey"
			primary key,
	"maxProductionLimit" integer
);

create table if not exists invcategories
(
	"categoryID" integer not null
		constraint "invCategories_pkey"
			primary key,
	"categoryName" varchar(100),
	"iconID" integer,
	published boolean
);

create table if not exists invcontrabandtypes
(
	"factionID" integer not null,
	"typeID" integer not null,
	"standingLoss" double precision,
	"confiscateMinSec" double precision,
	"fineByValue" double precision,
	"attackMinSec" double precision,
	constraint "invContrabandTypes_pkey"
		primary key ("factionID", "typeID")
);

create index if not exists "ix_invContrabandTypes_typeID"
	on invcontrabandtypes ("typeID");
create table if not exists invcontroltowerresourcepurposes
(
	purpose integer not null
		constraint "invControlTowerResourcePurposes_pkey"
			primary key,
	"purposeText" varchar(100)
);

create table if not exists invflags
(
	"flagID" integer not null
		constraint "invFlags_pkey"
			primary key,
	"flagName" varchar(200),
	"flagText" varchar(100),
	"orderID" integer
);

create table if not exists invgroups
(
	"groupID" integer not null
		constraint "invGroups_pkey"
			primary key,
	"categoryID" integer
		constraint invcategories_invgroups_fk
			references invcategories,
	"groupName" varchar(100),
	"iconID" integer,
	"useBasePrice" boolean,
	anchored boolean,
	anchorable boolean,
	"fittableNonSingleton" boolean,
	published boolean
);

create index if not exists "ix_invGroups_categoryID"
	on invgroups ("categoryID");
create table if not exists invitems
(
	"itemID" integer not null
		constraint "invItems_pkey"
			primary key,
	"typeID" integer not null,
	"ownerID" integer not null,
	"locationID" integer not null,
	"flagID" integer not null,
	quantity integer not null
);

create index if not exists "items_IX_OwnerLocation"
	on invitems ("ownerID", "locationID");
create index if not exists "ix_invItems_locationID"
	on invitems ("locationID");
create table if not exists invmarketgroups
(
	"marketGroupID" integer not null
		constraint "invMarketGroups_pkey"
			primary key,
	"parentGroupID" integer
		constraint invmarketgroups_parent__fk
			references invmarketgroups,
	"marketGroupName" varchar(100),
	description varchar(3000),
	"iconID" integer,
	"hasTypes" boolean
);

create table if not exists invmetagroups
(
	"metaGroupID" integer not null
		constraint "invMetaGroups_pkey"
			primary key,
	"metaGroupName" varchar(100),
	description varchar(1000),
	"iconID" integer
);

create table if not exists invmetatypes
(
	"typeID" integer not null
		constraint "invMetaTypes_pkey"
			primary key,
	"parentTypeID" integer,
	"metaGroupID" integer
);

create table if not exists invnames
(
	"itemID" integer not null
		constraint "invNames_pkey"
			primary key,
	"itemName" varchar(200) not null
);

create table if not exists invpositions
(
	"itemID" integer not null
		constraint "invPositions_pkey"
			primary key,
	x double precision not null,
	y double precision not null,
	z double precision not null,
	yaw real,
	pitch real,
	roll real
);

create table if not exists invtypereactions
(
	"reactionTypeID" integer not null,
	input boolean not null,
	"typeID" integer not null,
	quantity integer,
	constraint "invTypeReactions_pkey"
		primary key ("reactionTypeID", input, "typeID")
);

create table if not exists invtypes
(
	"typeID" integer not null
		constraint "invTypes_pkey"
			primary key,
	"groupID" integer
		constraint invgroups_invtypes_fk
			references invgroups,
	"typeName" varchar(100),
	description text,
	mass double precision,
	volume double precision,
	capacity double precision,
	"portionSize" integer,
	"raceID" integer,
	"basePrice" numeric(19,4),
	published boolean,
	"marketGroupID" integer
		constraint invmarketgroups_invtypes_fk
			references invmarketgroups,
	"iconID" integer,
	"soundID" integer,
	"graphicID" integer
);

create table if not exists dgmexpressions
(
	"expressionID" integer not null
		constraint "dgmExpressions_pkey"
			primary key,
	"operandID" integer,
	arg1 integer,
	arg2 integer,
	"expressionValue" varchar(100),
	description varchar(1000),
	"expressionName" varchar(500),
	"expressionTypeID" integer
		constraint dgmexpressions_invtypes_typeid_fk
			references invtypes,
	"expressionGroupID" integer
		constraint dgmexpressions_invgroups_groupid_fk
			references invgroups,
	"expressionAttributeID" integer
		constraint dgmexpressions_dgmattributetypes_attributeid_fk
			references dgmattributetypes
);

create table if not exists dgmtypeattributes
(
	"typeID" integer not null
		constraint invtypes_dgmtypeattributes_fk
			references invtypes,
	"attributeID" integer not null
		constraint dgmattributetypes_dgmtypeattributes_fk
			references dgmattributetypes,
	"valueInt" integer,
	"valueFloat" double precision,
	constraint "dgmTypeAttributes_pkey"
		primary key ("typeID", "attributeID")
);

create index if not exists "ix_dgmTypeAttributes_attributeID"
	on dgmtypeattributes ("attributeID");
create table if not exists dgmtypeeffects
(
	"typeID" integer not null
		constraint invtypes_dgmtypeeffects_fk
			references invtypes,
	"effectID" integer not null
		constraint dgmeffects_dgmtypeeffects_fk
			references dgmeffects,
	"isDefault" boolean,
	constraint "dgmTypeEffects_pkey"
		primary key ("typeID", "effectID")
);

create table if not exists industryactivity
(
	"typeID" integer not null
		constraint industryactivity_invtypes_typeid_fk
			references invtypes,
	"activityID" integer not null,
	time integer,
	constraint "industryActivity_pkey"
		primary key ("typeID", "activityID")
);

create index if not exists "ix_industryActivity_activityID"
	on industryactivity ("activityID");
create table if not exists industryactivitymaterials
(
	"typeID" integer not null
		constraint industryactivitymaterials_invtypes_typeid_fk
			references invtypes,
	"activityID" integer not null,
	"materialTypeID" integer not null
		constraint industryactivitymaterials_invtypesmaterialtypeid_fk
			references invtypes,
	quantity integer,
	constraint industryactivitymaterials_typeid_activityid_materialtypeid_pk
		primary key ("typeID", "activityID", "materialTypeID"),
	constraint industryactivitymaterials_industryactivity_typeid_activityid_fk
		foreign key ("typeID", "activityID") references industryactivity
);

create index if not exists "industryActivityMaterials_idx1"
	on industryactivitymaterials ("typeID", "activityID");
create index if not exists "ix_industryActivityMaterials_typeID"
	on industryactivitymaterials ("typeID");
create table if not exists industryactivityprobabilities
(
	"typeID" integer not null
		constraint industryactivityprobabilities_invtypes_typeid_fk
			references invtypes,
	"activityID" integer not null,
	"productTypeID" integer not null
		constraint industryactivityprobabilities_invtypes_producttypeid_fk
			references invtypes,
	probability numeric(3,2),
	constraint industryactivityprobabilities_typeid_activityid_producttypeid_p
		primary key ("typeID", "activityID", "productTypeID"),
	constraint industryactivityprobabilities_industryactivity_typeid_activityi
		foreign key ("typeID", "activityID") references industryactivity
);

create index if not exists "ix_industryActivityProbabilities_productTypeID"
	on industryactivityprobabilities ("productTypeID");
create index if not exists "ix_industryActivityProbabilities_typeID"
	on industryactivityprobabilities ("typeID");
create table if not exists industryactivityproducts
(
	"typeID" integer not null
		constraint industryactivityproducts_invtypes_typeid_fk
			references invtypes,
	"activityID" integer not null,
	"productTypeID" integer not null
		constraint industryactivityproducts_invtypes_producttypeid_fk
			references invtypes,
	quantity integer,
	constraint industryactivityproducts_typeid_activityid_producttypeid_pk
		primary key ("typeID", "activityID", "productTypeID"),
	constraint industryactivityproducts_industryactivity_typeid_activityid_fk
		foreign key ("typeID", "activityID") references industryactivity
);

create index if not exists "ix_industryActivityProducts_productTypeID"
	on industryactivityproducts ("productTypeID");
create index if not exists "ix_industryActivityProducts_typeID"
	on industryactivityproducts ("typeID");
create table if not exists industryactivityskills
(
	"typeID" integer
		constraint industryactivityskills_invtypes_typeid_fk
			references invtypes,
	"activityID" integer,
	"skillID" integer
		constraint industryactivityskills_invtypes_skillid_fk
			references invtypes,
	level integer,
	constraint industryactivityskills_industryactivity_typeid_activityid_fk
		foreign key ("typeID", "activityID") references industryactivity
);

create index if not exists "industryActivitySkills_idx1"
	on industryactivityskills ("typeID", "activityID");
create index if not exists "ix_industryActivitySkills_skillID"
	on industryactivityskills ("skillID");
create index if not exists "ix_industryActivitySkills_typeID"
	on industryactivityskills ("typeID");
create table if not exists invtraits
(
	"traitID" serial not null
		constraint "invTraits_pkey"
			primary key,
	"typeID" integer
		constraint invtraits_invtypes_typeid_fk
			references invtypes,
	"skillID" integer,
	bonus double precision,
	"bonusText" text,
	"unitID" integer
);

create table if not exists invcontroltowerresources
(
	"controlTowerTypeID" integer not null
		constraint invcontroltowerresources_invtypes_controltowertypeid_fk
			references invtypes,
	"resourceTypeID" integer not null
		constraint invcontroltowerresources_invtypes_resourcetypeid_fk
			references invtypes,
	purpose integer,
	quantity integer,
	"minSecurityLevel" double precision,
	"factionID" integer,
	constraint "invControlTowerResources_pkey"
		primary key ("controlTowerTypeID", "resourceTypeID")
);

create table if not exists invtypematerials
(
	"typeID" integer not null
		constraint invtypematerials_invtypes_typeid_fk
			references invtypes,
	"materialTypeID" integer not null
		constraint invtypematerials_invtypes_materialtypeid_fk
			references invtypes,
	quantity integer not null,
	constraint "invTypeMaterials_pkey"
		primary key ("typeID", "materialTypeID")
);

create index if not exists "ix_invTypes_groupID"
	on invtypes ("groupID");
create table if not exists invuniquenames
(
	"itemID" integer not null
		constraint "invUniqueNames_pkey"
			primary key,
	"itemName" varchar(200) not null,
	"groupID" integer
);

create index if not exists "invUniqueNames_IX_GroupName"
	on invuniquenames ("groupID", "itemName");
create unique index if not exists "ix_invUniqueNames_itemName"
	on invuniquenames ("itemName");
create table if not exists invvolumes
(
	"typeID" integer not null
		constraint "invVolumes_pkey"
			primary key,
	volume integer
);

create table if not exists mapcelestialstatistics
(
	"celestialID" integer not null
		constraint "mapCelestialStatistics_pkey"
			primary key,
	temperature double precision,
	"spectralClass" varchar(10),
	luminosity double precision,
	age double precision,
	life double precision,
	"orbitRadius" double precision,
	eccentricity double precision,
	"massDust" double precision,
	"massGas" double precision,
	fragmented boolean,
	density double precision,
	"surfaceGravity" double precision,
	"escapeVelocity" double precision,
	"orbitPeriod" double precision,
	"rotationRate" double precision,
	locked boolean,
	pressure double precision,
	radius double precision,
	mass integer
);

create table if not exists mapdenormalize
(
	"itemID" integer not null
		constraint "mapDenormalize_pkey"
			primary key,
	"typeID" integer,
	"groupID" integer,
	"solarSystemID" integer,
	"constellationID" integer,
	"regionID" integer,
	"orbitID" integer,
	x double precision,
	y double precision,
	z double precision,
	radius double precision,
	"itemName" varchar(100),
	security double precision,
	"celestialIndex" integer,
	"orbitIndex" integer
);

create index if not exists "ix_mapDenormalize_constellationID"
	on mapdenormalize ("constellationID");
create index if not exists "ix_mapDenormalize_orbitID"
	on mapdenormalize ("orbitID");
create index if not exists "ix_mapDenormalize_regionID"
	on mapdenormalize ("regionID");
create index if not exists "ix_mapDenormalize_solarSystemID"
	on mapdenormalize ("solarSystemID");
create index if not exists "ix_mapDenormalize_typeID"
	on mapdenormalize ("typeID");
create index if not exists "mapDenormalize_IX_groupConstellation"
	on mapdenormalize ("groupID", "constellationID");
create index if not exists "mapDenormalize_IX_groupRegion"
	on mapdenormalize ("groupID", "regionID");
create index if not exists "mapDenormalize_IX_groupSystem"
	on mapdenormalize ("groupID", "solarSystemID");
create table if not exists mapjumps
(
	"stargateID" integer not null
		constraint "mapJumps_pkey"
			primary key,
	"destinationID" integer
);

create table if not exists maplandmarks
(
	"landmarkID" integer not null
		constraint "mapLandmarks_pkey"
			primary key,
	"landmarkName" varchar(100),
	description text,
	"locationID" integer,
	x double precision,
	y double precision,
	z double precision,
	"iconID" integer
);

create table if not exists maplocationscenes
(
	"locationID" integer not null
		constraint "mapLocationScenes_pkey"
			primary key,
	"graphicID" integer
);

create table if not exists maplocationwormholeclasses
(
	"locationID" integer not null
		constraint "mapLocationWormholeClasses_pkey"
			primary key,
	"wormholeClassID" integer
);

create table if not exists mapregions
(
	"regionID" integer not null
		constraint "mapRegions_pkey"
			primary key,
	"regionName" varchar(100),
	x double precision,
	y double precision,
	z double precision,
	"xMin" double precision,
	"xMax" double precision,
	"yMin" double precision,
	"yMax" double precision,
	"zMin" double precision,
	"zMax" double precision,
	"factionID" integer,
	radius double precision
);

create table if not exists mapconstellations
(
	"regionID" integer
		constraint mapconstellations_mapregions_regionid_fk
			references mapregions,
	"constellationID" integer not null
		constraint "mapConstellations_pkey"
			primary key,
	"constellationName" varchar(100),
	x double precision,
	y double precision,
	z double precision,
	"xMin" double precision,
	"xMax" double precision,
	"yMin" double precision,
	"yMax" double precision,
	"zMin" double precision,
	"zMax" double precision,
	"factionID" integer,
	radius double precision
);

create table if not exists mapconstellationjumps
(
	"fromRegionID" integer
		constraint mapconstellationjumps_mapregions_fromregionid_fk
			references mapregions,
	"fromConstellationID" integer not null
		constraint mapconstellationjumps_mapconstellations_fromconstellationid_fk
			references mapconstellations,
	"toConstellationID" integer not null
		constraint mapconstellationjumps_mapconstellations_toconstellationid_fk
			references mapconstellations,
	"toRegionID" integer
		constraint mapconstellationjumps_mapregions_toregionid_fk
			references mapregions,
	constraint "mapConstellationJumps_pkey"
		primary key ("fromConstellationID", "toConstellationID")
);

create table if not exists mapregionjumps
(
	"fromRegionID" integer not null
		constraint mapregionjumps_mapregions_fromregionid_fk
			references mapregions,
	"toRegionID" integer not null
		constraint mapregionjumps_mapregions_toregionid_fk
			references mapregions,
	constraint "mapRegionJumps_pkey"
		primary key ("fromRegionID", "toRegionID")
);

create table if not exists mapsolarsystems
(
	"regionID" integer
		constraint mapsolarsystems_mapregions_regionid_fk
			references mapregions,
	"constellationID" integer
		constraint mapsolarsystems_mapconstellations_constellationid_fk
			references mapconstellations,
	"solarSystemID" integer not null
		constraint "mapSolarSystems_pkey"
			primary key,
	"solarSystemName" varchar(100),
	x double precision,
	y double precision,
	z double precision,
	"xMin" double precision,
	"xMax" double precision,
	"yMin" double precision,
	"yMax" double precision,
	"zMin" double precision,
	"zMax" double precision,
	luminosity double precision,
	border boolean,
	fringe boolean,
	corridor boolean,
	hub boolean,
	international boolean,
	regional boolean,
	constellation boolean,
	security double precision,
	"factionID" integer,
	radius double precision,
	"sunTypeID" integer,
	"securityClass" varchar(2)
);

create table if not exists mapsolarsystemjumps
(
	"fromRegionID" integer
		constraint mapsolarsystemjumps_mapregions_fromregionid_fk
			references mapregions,
	"fromConstellationID" integer
		constraint mapsolarsystemjumps_mapconstellations_fromconstellationid_fk
			references mapconstellations,
	"fromSolarSystemID" integer not null
		constraint mapsolarsystemjumps_mapsolarsystems_fromsolarsystemid_fk
			references mapsolarsystems,
	"toSolarSystemID" integer not null
		constraint mapsolarsystemjumps_mapsolarsystems_tosolarsystemid_fk
			references mapsolarsystems,
	"toConstellationID" integer
		constraint mapsolarsystemjumps_mapconstellations_toconstellationid_fk
			references mapconstellations,
	"toRegionID" integer
		constraint mapsolarsystemjumps_mapregions_toregionid_fk
			references mapregions,
	constraint "mapSolarSystemJumps_pkey"
		primary key ("fromSolarSystemID", "toSolarSystemID")
);

create index if not exists "ix_mapSolarSystems_constellationID"
	on mapsolarsystems ("constellationID");
create index if not exists "ix_mapSolarSystems_regionID"
	on mapsolarsystems ("regionID");
create index if not exists "ix_mapSolarSystems_security"
	on mapsolarsystems (security);
create table if not exists mapuniverse
(
	"universeID" integer not null
		constraint "mapUniverse_pkey"
			primary key,
	"universeName" varchar(100),
	x double precision,
	y double precision,
	z double precision,
	"xMin" double precision,
	"xMax" double precision,
	"yMin" double precision,
	"yMax" double precision,
	"zMin" double precision,
	"zMax" double precision,
	radius double precision
);

create table if not exists planetschematics
(
	"schematicID" integer not null
		constraint "planetSchematics_pkey"
			primary key,
	"schematicName" varchar(255),
	"cycleTime" integer
);

create table if not exists planetschematicspinmap
(
	"schematicID" integer not null,
	"pinTypeID" integer not null,
	constraint "planetSchematicsPinMap_pkey"
		primary key ("schematicID", "pinTypeID")
);

create table if not exists planetschematicstypemap
(
	"schematicID" integer not null,
	"typeID" integer not null,
	quantity integer,
	"isInput" boolean,
	constraint "planetSchematicsTypeMap_pkey"
		primary key ("schematicID", "typeID")
);

create table if not exists ramactivities
(
	"activityID" integer not null
		constraint "ramActivities_pkey"
			primary key,
	"activityName" varchar(100),
	"iconNo" varchar(5),
	description varchar(1000),
	published boolean
);

create table if not exists ramassemblylinestations
(
	"stationID" integer not null,
	"assemblyLineTypeID" integer not null,
	quantity integer,
	"stationTypeID" integer,
	"ownerID" integer,
	"solarSystemID" integer,
	"regionID" integer,
	constraint "ramAssemblyLineStations_pkey"
		primary key ("stationID", "assemblyLineTypeID")
);

create index if not exists "ix_ramAssemblyLineStations_ownerID"
	on ramassemblylinestations ("ownerID");
create index if not exists "ix_ramAssemblyLineStations_regionID"
	on ramassemblylinestations ("regionID");
create index if not exists "ix_ramAssemblyLineStations_solarSystemID"
	on ramassemblylinestations ("solarSystemID");
create table if not exists ramassemblylinetypedetailpercategory
(
	"assemblyLineTypeID" integer not null,
	"categoryID" integer not null,
	"timeMultiplier" double precision,
	"materialMultiplier" double precision,
	"costMultiplier" double precision,
	constraint "ramAssemblyLineTypeDetailPerCategory_pkey"
		primary key ("assemblyLineTypeID", "categoryID")
);

create table if not exists ramassemblylinetypedetailpergroup
(
	"assemblyLineTypeID" integer not null,
	"groupID" integer not null,
	"timeMultiplier" double precision,
	"materialMultiplier" double precision,
	"costMultiplier" double precision,
	constraint "ramAssemblyLineTypeDetailPerGroup_pkey"
		primary key ("assemblyLineTypeID", "groupID")
);

create table if not exists ramassemblylinetypes
(
	"assemblyLineTypeID" integer not null
		constraint "ramAssemblyLineTypes_pkey"
			primary key,
	"assemblyLineTypeName" varchar(100),
	description varchar(1000),
	"baseTimeMultiplier" double precision,
	"baseMaterialMultiplier" double precision,
	"baseCostMultiplier" double precision,
	volume double precision,
	"activityID" integer,
	"minCostPerHour" double precision
);

create table if not exists raminstallationtypecontents
(
	"installationTypeID" integer not null,
	"assemblyLineTypeID" integer not null,
	quantity integer,
	constraint "ramInstallationTypeContents_pkey"
		primary key ("installationTypeID", "assemblyLineTypeID")
);

create table if not exists skinlicense
(
	"licenseTypeID" integer not null
		constraint "skinLicense_pkey"
			primary key,
	duration integer,
	"skinID" integer
);

create table if not exists skinmaterials
(
	"skinMaterialID" integer not null
		constraint "skinMaterials_pkey"
			primary key,
	"displayNameID" integer,
	"materialSetID" integer
);

create table if not exists skins
(
	"skinID" integer not null
		constraint skins_pkey
			primary key,
	"internalName" varchar(70),
	"skinMaterialID" integer
);

create table if not exists skinship
(
	"skinID" integer,
	"typeID" integer
);

create index if not exists "ix_skinShip_skinID"
	on skinship ("skinID");
create index if not exists "ix_skinShip_typeID"
	on skinship ("typeID");
create table if not exists staoperations
(
	"activityID" integer,
	"operationID" integer not null
		constraint "staOperations_pkey"
			primary key,
	"operationName" varchar(100),
	description varchar(1000),
	fringe integer,
	corridor integer,
	hub integer,
	border integer,
	ratio integer,
	"caldariStationTypeID" integer,
	"minmatarStationTypeID" integer,
	"amarrStationTypeID" integer,
	"gallenteStationTypeID" integer,
	"joveStationTypeID" integer
);

create table if not exists staoperationservices
(
	"operationID" integer not null,
	"serviceID" integer not null,
	constraint "staOperationServices_pkey"
		primary key ("operationID", "serviceID")
);

create table if not exists staservices
(
	"serviceID" integer not null
		constraint "staServices_pkey"
			primary key,
	"serviceName" varchar(100),
	description varchar(1000)
);

create table if not exists stastations
(
	"stationID" bigint not null
		constraint "staStations_pkey"
			primary key,
	security double precision,
	"dockingCostPerVolume" double precision,
	"maxShipVolumeDockable" double precision,
	"officeRentalCost" integer,
	"operationID" integer,
	"stationTypeID" integer,
	"corporationID" integer,
	"solarSystemID" integer,
	"constellationID" integer,
	"regionID" integer,
	"stationName" varchar(100),
	x double precision,
	y double precision,
	z double precision,
	"reprocessingEfficiency" double precision,
	"reprocessingStationsTake" double precision,
	"reprocessingHangarFlag" integer
);

create index if not exists "ix_staStations_constellationID"
	on stastations ("constellationID");
create index if not exists "ix_staStations_corporationID"
	on stastations ("corporationID");
create index if not exists "ix_staStations_operationID"
	on stastations ("operationID");
create index if not exists "ix_staStations_regionID"
	on stastations ("regionID");
create index if not exists "ix_staStations_solarSystemID"
	on stastations ("solarSystemID");
create index if not exists "ix_staStations_stationTypeID"
	on stastations ("stationTypeID");
create table if not exists stastationtypes
(
	"stationTypeID" integer not null
		constraint "staStationTypes_pkey"
			primary key,
	"dockEntryX" double precision,
	"dockEntryY" double precision,
	"dockEntryZ" double precision,
	"dockOrientationX" double precision,
	"dockOrientationY" double precision,
	"dockOrientationZ" double precision,
	"operationID" integer,
	"officeSlots" integer,
	"reprocessingEfficiency" double precision,
	conquerable boolean
);

create table if not exists translationtables
(
	"sourceTable" varchar(200) not null,
	"destinationTable" varchar(200),
	"translatedKey" varchar(200) not null,
	"tcGroupID" integer,
	"tcID" integer,
	constraint "translationTables_pkey"
		primary key ("sourceTable", "translatedKey")
);

create table if not exists trntranslationcolumns
(
	"tcGroupID" integer,
	"tcID" integer not null
		constraint "trnTranslationColumns_pkey"
			primary key,
	"tableName" varchar(256) not null,
	"columnName" varchar(128) not null,
	"masterID" varchar(128)
);

create table if not exists trntranslationlanguages
(
	"numericLanguageID" integer not null
		constraint "trnTranslationLanguages_pkey"
			primary key,
	"languageID" varchar(50),
	"languageName" varchar(200)
);

create table if not exists trntranslations
(
	"tcID" integer not null,
	"keyID" integer not null,
	"languageID" varchar(50) not null,
	text text not null,
	constraint "trnTranslations_pkey"
		primary key ("tcID", "keyID", "languageID")
);

create table if not exists warcombatzones
(
	"combatZoneID" integer not null
		constraint "warCombatZones_pkey"
			primary key,
	"combatZoneName" varchar(100),
	"factionID" integer,
	"centerSystemID" integer,
	description varchar(500)
);

create table if not exists warcombatzonesystems
(
	"solarSystemID" integer not null
		constraint "warCombatZoneSystems_pkey"
			primary key,
	"combatZoneID" integer
);

