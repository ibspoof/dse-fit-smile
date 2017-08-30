package com.github.ibspoof.dse.webapp.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties
public class CassandraProperties {

    @Value("${cassandra.local.hosts}")
    private String localHosts;

    @Value("${cassandra.local.port}")
    private Integer localPort;

    @Value("${cassandra.local.dcName}")
    private String localDcName;

    @Value("${cassandra.global.auth.username}")
    private String authUsername;

    @Value("${cassandra.global.auth.username}")
    private String authPassword;

    @Value("${cassandra.global.connectionsMin}")
    private Integer connectionsMin;

    @Value("${cassandra.global.connectionsMax}")
    private Integer connectionsMax;

    @Value("${cassandra.global.defaultKeyspace}")
    private String defaultKeyspace;


    public String getAuthUsername() {
        return authUsername;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public String getLocalHosts() {
        return localHosts;
    }

    public Integer getLocalPort() {
        return localPort;
    }

    public String getLocalDcName() {
        return localDcName;
    }

    public Integer getConnectionsMin() {
        return connectionsMin;
    }

    public Integer getConnectionsMax() {
        return connectionsMax;
    }

    public String getDefaultKeyspace() {
        return defaultKeyspace;
    }
}