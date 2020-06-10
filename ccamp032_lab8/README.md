# Lab 8

<br/>

### Student Information
- <u>Name</u>: Christian Campos
- <u>Email</u>: ccamp032@ucr.edu
- <u>NetID</u>: ccamp032
- <u>SID</u>: 862080812

<br/>

## Lab Questions

#### 1. Insert the sample JSON file into a new collection named contacts.

    db.createCollection("contacts")
    
    db.contacts.insert([ contents of contacts.json file ] )

#### 2. Retrieve all the users sorted by name.

    db.contacts.find().sort( { Name: 1 } )

#### 3. List only the id and names sorted in reverse alphabetical order by name (Z-to-A).

    db.contacts.find({}, { _id:1, Name:1 } ).sort( { Name: -1} )

#### 4. Is the comparison of the attribute name case-sensitive? Show how you try this with the previous query and include your answer.

	yes, the comparison of the attribute name is case-sensitive.
	If we run the query ; db.contacts.find({}, { _id:1, name:1 } ).sort( { Name: -1} ) then we only get the id results.
	This shows that name is case-sensitive since Name exist.	

#### 5. Repeat Q3 above but do not show the _id field.

    db.contacts.find({}, { _id:0, Name:1 } ).sort( { Name: -1} )

#### 6. Does MongoDB accept this document while the name field has a different type than other records?
    
    db.contacts.insert({Name: {First:"David", Last: "Bark"}})
    

	Yes, MongoDB does accept this document and it will create it as an object.

#### 7. Where do you expect the new record to be located in the sort order? Verify the answer and explain.

    db.contacts.find({}, { _id:1, Name:1 } ).sort( { Name: -1} )

	I expect the new record to be located in the top sorted order because it's an object and descending order.
	
#### 8. Where do you expect the new document to appear in the sort order. Verify your answer and explain after running the query.

    db.contacts.insert({Name: ["David", "Bark"]})
    db.contacts.find({}, { _id:1, Name:1 } ).sort( { Name: -1} )

    I expect the new document to be between the name "Hayes Weaver" and "Craft Parks" since we are sorting in descending order. 

#### 9. Where do you expect the last inserted record, {Name: [“David”, “Bark”]} to appear this time? Does it appear in the same position relative to the other records? Explain why or why not.

    db.contacts.find({}, { _id:1, Name:1 } ).sort( { Name: 1} )
    
    I expect the new document to be between the name "Aimee Mcintosh" and "Cooke Schroeder" since we are sorting in ascending order. 
	
#### 10. Build an index on the Name field for the users collection. Is MongoDB able to build the index on that field with the different value types stored in the Name field?

    db.contacts.createIndex({"Name":1})

    MongoDB is able to build the index on the Name field with the different values types stored in the Name field. 
	Ouput: 

    > db.contacts.createIndex({"Name":1})
    {
        "createdCollectionAutomatically" : false,
        "numIndexesBefore" : 1,
        "numIndexesAfter" : 2,
        "ok" : 1
    }

    This shows that MongoDB successfully created the index.