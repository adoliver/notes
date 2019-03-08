# Amazon Services technology

## Amazon Document DB
Similar to mongoDB nosql database. Includes mongoDB compatibility

### Biggest Challenge
Biggest challenge is to handle the replication time of large sets of data. Some customers have weeks long replication instead of hours.

* Compute and storage decoupled
* distribute smaller partitions of data. Data split into nodes no larger than 10GB
* increase the replication of data in the storage layer. 6 way replication across 3 AZ(amazon zones)

#### Traffic spike scaling
The compute layer can be spun up independently of the data to handle more traffic

#### Data node failure
Each data node has 6 copies. One zone can go down removing 2 copies and you still have 4 copy majority. If any more copies go bad each node is only 10GB and can be more rapidly replicated within the storage system.

Data durability is not tied to the number of instances. 6 by 3 replication happens with even one installation. MongoDB relies on separate entire installations to replicate data.

##### Quorum
A majoity of storage nodes must accept the write before returning success to the client. So successful writes will be very durable. 

### MongoDB compatibility
* add features as requested by customers
* can use AWS DMS to migrate from MongoDB to DocumentDB

### Security & Compliance
* encryption at rest with KMS
* encryption in transit
* best practices by default
* Network isolation with Amazon Virtual Private Cloud(VPC)
* PCI compliant by default

### Limitations
* not integrated with Amazon Autoscaling
* 16MB document restriction
* aggregation operators currently limited and support is increasing
* no "change streams" feature yet. Comitted to adding in "near" future.
