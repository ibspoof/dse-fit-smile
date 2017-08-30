package com.github.ibspoof.dse.webapp.repositories;


import com.datastax.driver.mapping.MappingManager;
import com.github.ibspoof.dse.webapp.services.CassandraService;
import org.springframework.stereotype.Repository;

@Repository
abstract class AbstractRepository {

    private static final CassandraService cassandraService = new CassandraService();

    static MappingManager mappingManager = new MappingManager(cassandraService.getSession());


    AbstractRepository() {
    }

}
