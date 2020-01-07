package com.example.demo.config;

import org.apache.activemq.artemis.api.core.BroadcastEndpointFactory;
import org.apache.activemq.artemis.api.core.DiscoveryGroupConfiguration;
import org.apache.activemq.artemis.api.core.UDPBroadcastEndpointFactory;
import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@Configuration
public class DemoConfig {


    @Bean
    public ConnectionFactory connectionFactory() throws Exception {

        DiscoveryGroupConfiguration discConf = new DiscoveryGroupConfiguration();
        discConf.setName("meine-discovery-gruppe");

        BroadcastEndpointFactory factoryEndpoint = new UDPBroadcastEndpointFactory();
        ((UDPBroadcastEndpointFactory) factoryEndpoint).setGroupAddress("231.7.7.7");
        ((UDPBroadcastEndpointFactory) factoryEndpoint).setGroupPort(9876);
        discConf.setBroadcastEndpointFactory(factoryEndpoint);

        ConnectionFactory factory = ActiveMQJMSClient.createConnectionFactoryWithHA(discConf, JMSFactoryType.CF);
        ((ActiveMQJMSConnectionFactory) factory).setUser("admin");
        ((ActiveMQJMSConnectionFactory) factory).setPassword("admin");
        ((ActiveMQJMSConnectionFactory) factory).setReconnectAttempts(5);
        ((ActiveMQJMSConnectionFactory) factory).setRetryIntervalMultiplier(1.0);
        ((ActiveMQJMSConnectionFactory) factory).setRetryInterval(1000);
        ((ActiveMQJMSConnectionFactory) factory).setFailoverOnInitialConnection(true);
        /*((ActiveMQConnectionFactory) factory).setInitialConnectAttempts(10);*/
        ((ActiveMQJMSConnectionFactory) factory).setPreAcknowledge(false);
        return factory;
    }
}
