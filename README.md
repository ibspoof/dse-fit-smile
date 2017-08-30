## About

A DataStax Enterprise (DSE) example application that demonstrates how to use DSE FieldTransformers to 
parse binary blob data stored in Cassandra to index specific fields in DSE Search without being present in a CQL column.

The webapp pulls events on this day from Wikipedia.org (https://en.wikipedia.org/api/rest_v1/feed/onthisday/events/08/24) and inserts via CQL into the DB.
Each days events on a year will represent a single row in the Cassandra table (`this_day`). For each sub-page as part of the days event will be encoded to a binary file using Jackson SMILE encoder and inserted into the table.

The transformer.jar and configurations for DSE will while indexing:
1. parse the pages column blob
1. deserialize back to a POJO
1. extract the requested fields
    1. lang -> pages_lang
    1. pageid -> pages_pageid
    1. timestamp -> pages_timestamp
1. Index the fields above and expose them to searches via the Solr Admin page or CQL

## Usage

### Requirements
1. DataStax Enterprise 5.1.2+
1. Gradle 3.5+
1. Java 1.8

### Installation
1. Build the transformer project and place the `transformer-1.0.0.jar` in the class path for all DSE nodes for the test Cluster and restart the nodes to load the jar
1. Apply CQL Schema in `setup/schema.cql` to Cluster
1. Apply Solr Schema for core `fit_demo.this_day` with Solr file in `setup/solr_*`
1. Build and launch webapp project (SpringBoot)
    1. Check `webapp/src/main/resources/application.properties` to match local setup before building
1. Visit [http://localhost:8080/loader/08/24](http://localhost:8080/loader/08/24) to load This Day values from Wikipedia for 08/24
    1. Any valid month/day can be used to populate the local DSE cluster with data
1. Visit cluster Solr Admin or via CQL query 
