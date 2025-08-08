# GraphQL v/s Rest
# Push v/s pull approach?
# Load Balancer? - Nginx
# Spark v/s Stream processing

# Serialization v/s compression - Protobuf, Avro, etc??
- What is Data Serialization?
Serialization is the process of converting structured data (e.g., an object in Java/Python) into a byte stream so that it can be:
Sent over the network
Stored in Kafka, DB, files, etc.
Deserialization is the reverse — turning byte stream back into structured data.

- Why Serialization Matters in Kafka?
Kafka stores and transmits only bytes. Both producers and consumers must agree on:
What the data means
How it's encoded and decoded
This is where serialization formats like Avro and Protobuf come in.

- What Is a Schema Registry?
Schema Registry is a centralized service that stores and manages schemas used in your system.
Think of it as a contract enforcer between producers and consumers.
Key Functions:
Stores schemas by topic/version
Validates new schemas for compatibility (e.g., forward/backward)
Lets consumers fetch the right schema for decoding
Prevents breaking changes from silently corrupting data

- How do you handle schema changes in log formats?
Avro + Schema Registry
Versioned schemas
Compatibility checks before deployment

| Feature           | **Serialization**                                              | **Compression**                                    |
| ----------------- | -------------------------------------------------------------- | -------------------------------------------------- |
| **Goal**          | Convert structured data into a format for transmission/storage | Reduce size of data to save space/bandwidth        |
| **Input**         | Objects, structured data (e.g., JSON, Avro, Java objects)      | Byte streams (often serialized data)               |
| **Output**        | Byte stream (not optimized for size)                           | Smaller byte stream                                |
| **Used for**      | Data exchange between systems (e.g., Kafka, APIs)              | Reducing storage/network usage                     |
| **Examples**      | Avro, Protobuf, JSON, XML                                      | GZIP, Snappy, Zstandard (zstd), Brotli             |
| **Reversibility** | Must preserve original data format for deserialization         | Must allow decompression to get back original data |

- They’re often used together in systems like Kafka:
Serialize the data (e.g., with Avro or Protobuf)
Compress the serialized bytes (e.g., with GZIP or Snappy)

# API Gateway:
An API Gateway is a service that acts as a single entry point for all clients accessing your backend services.
Responsibilities:
- Request routing to microservices
- Authentication/Authorization
- Rate limiting
- Caching
- Load balancing
- Metrics and logging
- Protocol translation (e.g., HTTP to gRPC)
Think of it like a “smart proxy” between your clients and your backend services.
Examples:
- AWS API Gateway
- Kong
- Envoy
- NGINX (with Lua or plugins)
- Istio Ingress Gateway (for Kubernetes)