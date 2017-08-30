CREATE KEYSPACE fit_demo WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}
    AND durable_writes = true;


CREATE TABLE fit_demo.this_day (
    date text,
    year int,
    title text,
    pages blob,
    PRIMARY KEY(date, year)
);