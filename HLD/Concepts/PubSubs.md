## 3. Cron job
## 4. SQS + Lambda function
## 7. Cloud watch
## 8. At least once message delivery?

# Pub Sub models

## 1. Kafka and Flink/Spark Stream
- How do you ensure message durability and prevent loss?
✅ Kafka-Level Guarantees
a. Replication Factor
Each Kafka topic has a replication factor (usually 2 or 3).
Each partition has one leader and several followers.
Messages are replicated across brokers so if one goes down, the others still have the data.

b. Acknowledgment Settings (acks)
acks=0: Producer doesn't wait for ack. Fastest but not durable.
acks=1: Leader acknowledges after writing to its log. Quick but if leader dies before replication, data may be lost.
✅ acks=all (recommended for durability): Leader waits for all in-sync replicas (ISRs) to write the message before acknowledging.
This ensures durability at the cost of latency.

c. Min In-Sync Replicas (ISR)
Setting min.insync.replicas = 2 ensures that at least two replicas must have the message. If not, Kafka will reject the write.

- Flink/Spark Durability
Both Flink and Spark Structured Streaming support checkpointing and exactly-once semantics.
If job crashes, it can restart from the last checkpoint and reprocess from the correct Kafka offset.

-How would you scale this system?
🔀 Kafka Scaling
a. Increase Partitions
Kafka scales by partitioning topics. More partitions → more throughput → more parallelism.
But also increases overhead (more metadata, open file handles).

b. Add Brokers
Horizontal scaling: Add more Kafka brokers to handle increased load and partitions.
Replication is managed automatically.

- Stream Processing (Flink/Spark) Scaling
a. Flink: Parallelism
Flink jobs can be split into parallel operators (tasks).
The parallelism can match the number of Kafka partitions for balanced processing.

- Kafka does not support retries automatically, but to implement it, publish to another topic and then retry later for exponential delay. So, also publish with time after which it should be attempted.

- Dead Letter Queue: Messages which cannot be processed stored here to decide what needs to be done.

- When a message is processed, there is an acknowledgment until and only then will the other consumers be not allowed to read that message. Thus at-least once. And then messages are eventually deleted when at TTL/running low on queue memory.

## 2. Flink v/s Batch & Daemon Application
| Capability                       | **Apache Flink (Built-in)**                     | **Spring Daemon / Batch (You Build)**                                           | Notes                                                    |
| -------------------------------- | ----------------------------------------------- | ------------------------------------------------------------------------------- | -------------------------------------------------------- |
| **Real-time processing**         | ✅ Yes                                           | ✅ Yes (Daemon), ❌ No (Batch)                                                    | Batch is scheduled; Daemon can stream                    |
| **Kafka Integration**            | ✅ Kafka Source & Sink with offset mgmt          | ✅ Spring Kafka (`@KafkaListener`)                                               | Need to manage consumer group, retries, commits manually |
| **Fault tolerance**              | ✅ Checkpointing, restart strategies             | ❌ Must build custom retry, rollback, or idempotency                             |                                                          |
| **Stateful processing**          | ✅ Native support with Flink State API           | ❌ You build state store (e.g., Redis, DB)                                       |                                                          |
| **Windowing** (time/event-based) | ✅ Native time/event windows, watermarks         | ❌ You implement window logic yourself                                           |                                                          |
| **Backpressure handling**        | ✅ Built-in, auto-propagated                     | ❌ You monitor queue lag, throttle manually                                      |                                                          |
| **Parallelism & Scaling**        | ✅ Parallel operators, autoscaling               | ❌ Multithreading, manual partition handling                                     |                                                          |
| **Exactly-once semantics**       | ✅ With Kafka + checkpointing                    | ❌ Difficult, typically at-least-once unless you use transactions or idempotency |                                                          |
| **Aggregation / Joins**          | ✅ Native keyed joins, co-groups, interval joins | ❌ Manual implementation, likely with in-memory maps or DB                       |                                                          |
| **Monitoring / Metrics**         | ✅ REST API, Flink UI                            | ❌ You must expose custom Prometheus/Actuator metrics                            |                                                          |
| **Deployment**                   | ✅ Docker, K8s, Yarn, Flink Cluster              | ✅ Easier to deploy Spring Boot on VM/K8s                                        |                                                          |
| **Maintenance**                  | Medium (needs Flink expertise)                  | Lower complexity (if logic is simple)                                           |                                                          |


# Kafka:
Kafka Supports Multiple Types of Clients
- Multiple Producers
Kafka supports many producers writing to:
The same topic
Different topics
Even the same partition (though ordering guarantees only apply per producer per partition)
Producers can operate concurrently from many microservices, applications, or instances.

- Multiple Consumers
Kafka allows many consumers to read:
From the same topic independently (via different consumer groups)
In a load-balanced manner (via the same consumer group)
Each partition in a topic is consumed by only one consumer in a group at a time for parallelism and ordering.

- Multiple Consumer Groups
Each group has an independent offset and thus can consume the same data at different speeds or times.
Enables use cases like:
Group A: Real-time alerting
Group B: Long-term analytics
Group C: Archiving to S3

- How do you deal with log spikes (e.g., during peak traffic)?
Kafka buffering capability
Consumer autoscaling
Backpressure handling in Flink
Batching writes to downstream stores

# Back - Pressure?
- Backpressure is a concept in stream processing and asynchronous systems that refers to the mechanism for handling situations where producers are sending data faster than consumers can process it.

Kafka acts as the buffer and shock absorber.
If Flink is slow, Kafka will retain data temporarily, but only within retention limits.
If Kafka fills up, producers (services) get blocked → this naturally creates backpressure.


# Dead letter queues?
- A Dead Letter Queue is a queue to which messages are sent if they cannot be processed after a certain number of retries or due to specific errors.
| System               | DLQ Support                                                                                                  |
| -------------------- | ------------------------------------------------------------------------------------------------------------ |
| **Kafka**            | Not built-in, but can be implemented by sending failed messages to a separate topic (e.g., `topic_name.DLQ`) |
| **AWS SQS**          | Native support; configure DLQ and max receive count                                                          |
| **RabbitMQ**         | Via dead-letter exchange routing                                                                             |
| **Flink / Spark**    | Manually push bad records to a DLQ topic or sink                                                             |
| **gRPC / REST APIs** | DLQ pattern can be implemented using a separate store or queue                                               |

| Benefit                              | Explanation                                                     |
| ------------------------------------ | --------------------------------------------------------------- |
| **Fault isolation**                  | Prevents a single bad message from blocking the entire pipeline |
| **Observability**                    | Helps monitor and inspect failed messages                       |
| **Manual or automated reprocessing** | DLQ messages can be reviewed, fixed, and replayed               |
| **Improved resilience**              | Keeps main processing pipeline healthy even under failure       |
