# Write back v/s Write through
# Redis:
- Single Threaded?!
- Partitioning: multi-master setup too, with a follower of each master. Just need to ensure that the data for one user goes inside a particular shard always.
- how does sharding with single leader replication work? - Each shard range of databases have their own leader?

# CDN:
- eg. AWS CloudFront