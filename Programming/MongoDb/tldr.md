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

## Query Debugging

MongoDB provides an "explain" command which can be added to query calls like find() to get details about how the results are retrieved. This is similar in practice to the explain command available in traditional databases.

Avoid COLSCAN when reasonable as this requires a lookup on every document object in the entire collection.

## Query Logic

* `$and: [ ...expression... ]`
   * Optionally use the implicit "and" by using commas: `{ field: value }, { field2: value2 }`
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

## Sorting

To reliably paginate through a set of documents from a find call, use the sort method.

Alphabetization is based on the underlying character encoding. e.g. ASCII and UTF-X is sorted with all latin upper case before lower case.

## Limit

Retrieve only the first X results returned from the query. This will be most reliable when also using a sort operation.

## Projections

To receive a filtered version of the document objects in the results, use a projection. This can reduce the network bandwidth required by only requesting the data actually needed in the result.

Projections are defined by providing a filter object which identifies the fields to be filtered, either as an inclusion whitelist by setting a 1 or an exclusion blacklist by setting a 0. 
You may only use inclusion or exclusion in the filter object, mixing is not allowed.
The _id is an exception where it is always included by default and may be excluded by setting it to 0

EXAMPLE: Return only the name, member_type, and late fields in the results
```
document {
    "name" : "Allen",
    "member_type" : "subscription",
    "dues" : 20.00,
    "flags" : {
        "late" : false,
        "high_value" : true,
        "has_locker" : false
    }
}

projection {
_id : 0,
name : 1,
member_type : 1
"flags.late" : 1
}

find({member_type: "subscription"}, {name:1, member_type:1, "flags.late":1, _id:0}).sort("name" : 1).limit(100)
```
                
# CRUD

## Basic CRUD

Basid CRUD functions work with parameters as follow
`crudFunc( filterObj, changeObj, optionsObj )`

Function naming scheme `[actionType][number]`

* findOne(filterObj, optionsObj) - return the first document matching the filterObj.
* findMany(filterObj, optionsObj) - return all documents matching the filterObj. Recommended to use sort and limit functions to manage results size.
* countDocuments(filterObj, optionsObj) - instead of returning the documents, return the count of matched documents.
* replaceOne - replace the first document matched by the filterObj with the provided changeObj.
* updateOne - update the first document matched by the filterObj with the provided fields in changeObj.
    * $set - update a vield value
    * $push - append to an array. Create the field and array if not present.
* updateMany( filterObj, updateObj, optionsObj ) - This will attempt to update all matched documents. On failure the collection is partially updated and any changes are immediately available to read operations. Additional runs will complete the operation.
* insertOne(changeObj, optionsObj) - add the provided changeObj document to the collection. This will return the inserted _id value
* insertMany(changeObj[], optionsObj) - add the list of provided changeObj documents to the collection. This will return an array of inserted _id values
* deleteOne(filterObj, optionsObj) - remove the first object matched by the filterObj.
* deleteMany(filterObj, optionsObj) - remove all objects matched by the filterObj.


Options
* upsert:&lt;boolean&gt; - (default:False) Create new document with changeObj data if the filter finds no object in the collection

## Atomic CRUD

Using basic CRUD updateOne and findOne will allow for raceconditions. By using these functions the final modified document can be retrieved as an atomic action, resolving before any other concurrently requested operations.

* findAndModify( { query: filterObj, update: changeObj, new: &lt;boolean&gt;, options... } ) Find and update the first document returned by filterObj
    * "new" option set to true will return the modified document
    * "new" option set to false is default and will return the original document before modifications

## Transactions

MongoDB handles transactions within the language drivers. This may be slightly different based on the language, but will generally follow:
1. Define a function including the sequence of actions to be taken.
    * Make sure to pass the session to any CRUD operation.
2. Initiate a new client session.
3. Use the with_transaction method to start the transaction on the session.
    * This will implement error handling
    * By default multidocument transactions must be completed within 60s

## BSON Data Types

Some data types must be handled in a special way to allow the driver to convert to and from BSON and native language objects.

* ObjectID
* Int64
* Decimal128
* regex

### Custom _id ObjectId

When creating a custom ObjectId...

# Indexing

Indexes in MongoDB are sorted datastructures that identify one or more documents adding additional write time creating a lookup which then speeds up queries based on the index.

* Indexes may be set to unique, causing an error if a duplicate document is inserted.
* Indexes can pre-sort results, speeding up sort and range queries
* For especially frequent queries requiring only a subset of a document's fields an index can "cover" all the fields. This skips normal retrieval from the collections storage, increasing speed of retrieval.

## Best practices
* Only index queries that are used frequently, to minimize write cost
* The order of compound indices matters
    1. filtering fields used in queries
    2. sorting fields used in queries
        * When multiple sorts are used additional ordering matters. Research if it applies.
    3. range fields used in queries
    4. (rarely) projected fields. To reduce data retrieval time by skipping the majority of a large document.
* Be aware of redundant indices.
    * ex: active_1_email_1 will support all queries filtering on the "active" field. So an index of just active_1 is redundant. 
* Use the "hide" command to soft delete indices, allowing the performance impact on existing queries to be verified before fully deleting an index. Hidden indices will continue to be updated when documents are written.

# Aggregation Pipelines (MongoDB Views)

Instead of traditional Views MongoDB has a concept called "Aggregation Pipelines". These are used to generate a new collections after filtering, grouping and aggregation operations are performed on the original collection datasets. As the base collections are updated, these pipeline operations are run to keep the output collections up to date.

!! WARNING !! Make sure to not have any name collisions between aggregation pipeline output collections and your base collections. If the names are the same, then the aggregation will overwrite the base collection.
