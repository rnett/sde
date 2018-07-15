# Kotlin EVE SDE
EVE SDE DAO Drivers + helper functions

A library that provides Kotlin Exposed DAO wrapers for the EVE SDE.

Designed to be used with the fuzzworks posgresql database, with a custom table (industryactivityrecipe).

Tables will be added as I need them.


Also contains `ItemList` and `MutableItemList` data structures for dealing with item stacks, as well as the ability to get prices from the [evepraisal.com](https://evepraisal.com/) api (they are cached).
