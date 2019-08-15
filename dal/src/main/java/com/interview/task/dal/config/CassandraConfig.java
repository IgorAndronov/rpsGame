package com.interview.task.dal.config;

import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.LoadBalancingPolicy;
import com.datastax.driver.core.policies.RoundRobinPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySource("classpath:dal.properties")
@EnableCassandraRepositories(basePackages = {"com.interview.task.dal"})
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${cassandra.contactpoints}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.keyspace}")
    private String keySpace;

    @Value("${cassandra.basePackages}")
    private String basePackages;

    @Value("${cassandra.jmx-enabled}")
    private boolean isMetricsEnabled;

    @Override
    protected List<String> getStartupScripts() {

        String createNamespace = "CREATE KEYSPACE IF NOT EXISTS "
                + keySpace +
                " WITH replication = {\n" +
                "\t'class' : 'NetworkTopologyStrategy',\n" +
                "\t'datacenter1' : 1,\n" +
                "\t'datacenter2' : 1\n" +
                "};";
        String createUsersTable = "";

        String createRpsGameResults = "";

        return Arrays.asList(createNamespace);
    }



    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {basePackages};
    }

    @Override
    protected boolean getMetricsEnabled() {
        return isMetricsEnabled;
    }

    @Override
    protected LoadBalancingPolicy getLoadBalancingPolicy() {
        return DCAwareRoundRobinPolicy.builder().withLocalDc("datacenter1").withUsedHostsPerRemoteDc(0).build();
    }

    public CassandraConfig() {
        super();
    }
}
