# Concepts:

## Change Data Capture

## Pipelines:
1. Server (Producer) -> Kafka (Message Broker) -> Flink (Consumer for heavy processing) -> Elastic Search -> Kibana.

OR:
Server (Producer) -> LogStash (Consumer for light processing) -> Elastic Search -> Kibana.

2. For things with an expiry, try considering using Redis with TTl

3. Searching is through Elastic Searches which uses Inverted Index, and CDC can be used to update it when there are changes in the underlying Data.

4. Show API Gateway as the entry point to our application for clients.