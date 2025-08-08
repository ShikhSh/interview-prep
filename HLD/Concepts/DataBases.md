# DataBases:

## SQL v/s NoSQL or more preferably, Relational v/s Non-relational DB:
# Cassandra v/s MongoDB
# SQL v/s Postgres
- ACID systems, so take care of locking and everything.

## Relational DB - SQL v/s PostGres


## Non-relational DB - MongoDB and Cassandra
https://www.youtube.com/watch?v=6bxin9cZL_w&list=PLjTveVh7FakLdTmm42TMxbN8PvVn5g4KJ&index=36&ab_channel=Jordanhasnolife
- Store data in a document format or not in form of relational tables:
- Updating values - tough

## Scaling DB:
Storage Tier Scaling
a. NoSQL DB (e.g., Cassandra, DynamoDB)
Horizontal scaling with sharding and partition keys.
Can handle high write throughput.

b. Relational DB (e.g., MySQL)
Might need read replicas, write sharding, or partitioned tables.
MySQL is less suited for high-ingestion log workloads unless used sparingly.

c. Time-Series DB (e.g., InfluxDB, Prometheus TSDB)
Built to handle large, write-heavy, timestamped data.
Often scaled by retention policies, bucket sharding, and high-cardinality compression.

d. Data Warehouse (e.g., Redshift, BigQuery, S3 + Athena)
Suited for batch analysis and can be decoupled from real-time processing.
Auto-scaling or serverless nature (e.g., BigQuery or Athena) helps in bursty loads.

## Sharding v/s Partitioning
1. Partitioning (General Concept)
- Definition:
Partitioning means splitting data into distinct, manageable chunks based on some key, often to improve performance, scalability, and manageability.
- Applies to:
Databases (logical tables)
Message queues (Kafka partitions)
File systems (HDFS blocks)
- Types:
Horizontal partitioning: Rows are split (e.g., users A-M vs N-Z)
Vertical partitioning: Columns are split (e.g., separate PII columns)
- Used in:
Kafka (partitions in a topic)
Spark/Flink (parallel data partitions)
SQL tables (PostgreSQL table partitioning)
Key Point: Partitioning is the act of dividing data logically or physically — it may be internal or external to the system.

2. Sharding (Partitioning + Distribution)
- Definition:
Sharding is a form of partitioning where each partition (called a shard) is stored on a different node or machine.
- Applies to:
Distributed databases (MongoDB, Cassandra, DynamoDB, etc.)
Scalable key-value stores and search engines (Elasticsearch)
- Shards = Partition + Distribution
Each shard is an independent database unit (often with its own compute/storage).
Typically includes replication for durability.
- Used in:
MongoDB → each shard is a full replica set
Elasticsearch → each index has primary + replica shards
DynamoDB → partitions mapped to physical storage nodes

## Time Series Databases, Hyper Table + Chunk Tables

## Postgres

## HDFS

## Parquet files