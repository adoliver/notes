# Overview

    * Based on storing flexible json objects instead of table rows.
    * Objects stored in a collection are expected to be indexed similarly
    * May reference objects from other collections
    * Optionally include json structure validation
    * Focus data design on the data access patterns
    * _id index is mandatory and uniquely identifies a top-level json object within a collection
    * Any combination of one or more document fields may be used to define an index
    * CRUD operations happen immediately, including partial updates due to errors. Keep this in mind.

# Queries

Queries follow the generic pattern of
```
{ <logic>, ... }
<logic> = <logical operator> OR implicit "and"

<logical operator> = $op: { field: comparison, ... }

field = dot notation reference of the field to be compared

<comparison> = $type: value
```

EXAMPLE QUERY:

All documents that have both valueA and valueA2 when looking at both fieldA and fieldB.sub-fieldA arrays
```
{
    $and: [
        $or: [ 
            { fieldA: { $eq: num_valueA1 } }, 
            { "fieldB.sub-fieldA": "string_valueA1" }
        ],
        $or: [
            { fieldA: num_valueA2 },
            { "fieldB.sub-fieldA": "string_valueA2" } ]
         ]
    ]
}
```
RESULT:
```
[
    {
        "fieldA" : [ num_valueA1, num_valueA3 ],
        "fieldB" : {
            "sub-fieldA" : [ "string_valueA2", "string_valueA4" ]
        }
    },
    ...
]
```

## Query Logic

* `$and: [ ...expression... ]`
   * Optionally use the implicit "and" by using commas: `{ field: value, field2, value2 }`
* `$or: [ ...expression... ]`
   * implicit "or" combined with implicit "and" exposes limitation of json as the query object. The re-use of the $or attibute causes the first value to be overwritten.
        ```
        {
            $or: [ { fieldA: valueA }, { fieldB: valueB } ],
            $or: [ { fieldC: valueC }, { fieldD: valueD } ]
        }
        ```
        results in the following query due to json parsing rules
        ```
        {
            $or: [ { fieldC: valueC }, { fieldD: valueD } ]
        }
        ```
   * Recommend explicit logical operators

## Query comparison

 * Use dot notation to reference sub-objects by wrapping the dot chain in quotes
 * When referencing an array field use dot notation to reference a field in the array of objects
     * When referencing a simple array of values list the expected value within the array `{ "some_array": "text-match" }`
     * The value provided will work with a simple array OR a simple field. `{ "sometimes_array": "value" }` will match both
         * `{ "sometimes_array": value }` AND `{ "sometimes_array": [ value1, value, value2 ] }`
 * value can be a raw numeric or string value matched exactly OR comparisons may be used
     * `$comparison: value_compared`
     * $eq: same as an implicit exact match
     * $ne: field value is not equal to value_compared
     * $gt: field value is greater than value_compared
     * $lt: field value is less than value_compared
     * $ltw: field value is less than or equal to value_compared
     * $gte: field value is greater than or equal to value_compared
                
# CRUD

## Basic CRUD

Basid CRUD functions work with parameters as follow
`crudFunc( filterObj, changeObj, optionsObj )`

Function naming scheme `[actionType][number]`

* replaceOne - replace the first document matched by the filterObj with the provided changeObj
* updateOne - update the first document matched by the filterObj with the provided fields in changeObj
    * $set - update a vield value
    * $push - append to an array. Create the field and array if not present.
* updateMany( filterObj, updateObj, optionsObj ) - This will attempt to update all matched documents. On failure the collection is partially updated and any changes are immediately available to read operations. Additional runs will complete the operation.
* deleteOne(filterObj, optionsObj) - remove the first object matched by the filterObj
* deleteMany(filterObj, optionsObj) - remove all objects matched by the filterObj

Options
* upsert:&lt;boolean&gt; - (default:False) Create new document with changeObj data if the filter finds no object in the collection

## Atomic CRUD

Using basic CRUD updateOne and findOne will allow for raceconditions. By using these functions the final modified document can be retrieved as an atomic action, resolving before any other concurrently requested operations.

* findAndModify( { query: filterObj, update: changeObj, new: &lt;boolean&gt;, options... } ) Find and update the first document returned by filterObj
    * "new" option set to true will return the modified document
    * "new" option set to false is default and will return the original document before modifications
