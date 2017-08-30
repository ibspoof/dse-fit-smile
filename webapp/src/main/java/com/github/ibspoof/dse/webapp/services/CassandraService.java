package com.github.ibspoof.dse.webapp.services;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.LoggingRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.github.ibspoof.dse.webapp.configs.CassandraProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CassandraService {

    private static final String CHAR_COMMA = ",";

    private static final Logger logger = LoggerFactory.getLogger(CassandraService.class.getSimpleName());

    @Autowired
    private CassandraProperties cassandraProperties;

    private static DseSession dseSession = null;

    public DseSession getSession() {

        if (dseSession == null) {
            logger.error("Not connected...");
            createSession();
        }

        return dseSession;
    }


    @PostConstruct
    private void createSession() {

        DseCluster.Builder clusterBuilder = DseCluster.builder();

        /*
          Hosts should be in order of closest hosts (same DC) to farthest (remote DC).

          Max of 2-4 hosts hosts per DC are needed as the first one connected to will be used to
          determine all the clusters nodes and DCs
         */

        String[] hostList = cassandraProperties.getLocalHosts().split(CHAR_COMMA);
        clusterBuilder.addContactPoints(hostList).withPort(cassandraProperties.getLocalPort());

        clusterBuilder.withQueryOptions(new QueryOptions()
                .setConsistencyLevel(ConsistencyLevel.LOCAL_QUORUM)
                .setSerialConsistencyLevel(ConsistencyLevel.LOCAL_SERIAL)
        );


//        clusterBuilder.

        /*
          The single line method is deprecated, the builder is 2.1.10+ method

          If usedHostsPerRemoteDc > 0, then if for a query no host in the local datacenter can be reached and if the consistency
          level of the query is not LOCAL_ONE or LOCAL_QUORUM, then up to usedHostsPerRemoteDc hosts per remote
          datacenter will be tried by the policy as a fallback.

          By default, no remote host will be used for LOCAL_ONE and LOCAL_QUORUM, since this would change the meaning
          of the consistency level, somewhat breaking the consistency contract
          (this can be overridden with allowRemoteDCsForLocalConsistencyLevel()).


          If allowRemoteDCsForLocalConsistencyLevel() is used it allows the policy to return remote hosts when building
          query plans for queries having consistency level LOCAL_ONE or LOCAL_QUORUM.

          When used in conjunction with usedHostsPerRemoteDc > 0, this overrides the policy of
          never using remote datacenter nodes for LOCAL_ONE and LOCAL_QUORUM queries. It is however inadvisable to do
          so in almost all cases, as this would potentially break consistency guarantees and if you are fine with that,
          it's probably better to use a weaker consistency like ONE, TWO or THREE. As such, this method should generally be
          avoided; use it only if you know and understand what you do.
         */
        TokenAwarePolicy tokenAwarePolicy = new TokenAwarePolicy(
                DCAwareRoundRobinPolicy.builder()
                        .withLocalDc(cassandraProperties.getLocalDcName())
                        .withUsedHostsPerRemoteDc(0)
                        .build()
        );
        clusterBuilder.withLoadBalancingPolicy(tokenAwarePolicy);


        /*
          Set local DC to use min 1 connection (1024 threads) up to 3 max

          Set remote DC to use min 1 and max 1 connection
          If you set DCAwareRoundRobinPolicy.withUsedHostsPerRemoteDc(0) then
          setConnectionsPerHost(HostDistance.REMOTE, 0, 0) can be used to limit the number of connections to a node
          opened.

          With a large number of application hosts (50+) this can reduce the amount of concurrent connections to a
          single C* node.

          To increase the number of concurrent requests/queries per connection use setMaxRequestsPerConnection() to a value
          between 1024 (default) and 34,000 (max).
         */
        PoolingOptions poolingOptions = new PoolingOptions()
                .setConnectionsPerHost(HostDistance.LOCAL, cassandraProperties.getConnectionsMin(), cassandraProperties.getConnectionsMax())
                .setConnectionsPerHost(HostDistance.REMOTE, 0, 0)
                .setMaxRequestsPerConnection(HostDistance.LOCAL, 1024);
        clusterBuilder.withPoolingOptions(poolingOptions);


        /*
          DefaultRetryPolicy.INSTANCE:
           - onReadTimeout = retry query same host
           - onWriteTimeout = retry query same host
           - onUnavailable = tryNextHost
           - onConnectionTimeout = tryNextHost

           Logging classes are to be included in your local logback.xml connection:
            {@link com.datastax.driver.core.policies.RetryPolicy.RetryDecision.Type#RETRY RETRY} and
            {@link com.datastax.driver.core.policies.RetryPolicy.RetryDecision.Type#IGNORE IGNORE} decisions (since
            {@link com.datastax.driver.core.policies.RetryPolicy.RetryDecision.Type#RETHROW RETHROW} decisions
         */
        clusterBuilder.withRetryPolicy(new LoggingRetryPolicy(DefaultRetryPolicy.INSTANCE));


        /*
         * OR
         *
         * Bubble up all exceptions from the driver to the app to chose what direction/action should be taken
         */
        //clusterBuilder.withRetryPolicy(FallthroughRetryPolicy.INSTANCE);


        /*
        Add authentication if present
         */
        if (StringUtils.isNotEmpty(cassandraProperties.getAuthUsername()) &&
                StringUtils.isNotEmpty(cassandraProperties.getAuthPassword())) {
            clusterBuilder.withCredentials(cassandraProperties.getAuthUsername(), cassandraProperties.getAuthPassword());
        }


        /*
          Finalize the builder options to use when connecting
         */
        DseCluster cluster = clusterBuilder.build();


        /*
          Create dseSession/connect to all nodes with above settings
         */
        try {
            dseSession = cluster.connect(cassandraProperties.getDefaultKeyspace());

            logger.debug("Connected to DSE...");

        } catch (Exception e) {
            logger.error("Unable to connect to DSE.");
            throw new IllegalStateException("Unable to connection to C*");
        }

    }

}
