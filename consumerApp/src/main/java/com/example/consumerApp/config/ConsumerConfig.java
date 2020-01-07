package com.example.consumerApp.config;

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
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@EnableJms
@Configuration
public class ConsumerConfig {

    @Bean
    public ConnectionFactory connectionFactory() throws Exception {
        //ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQClient.createServerLocator("udp://231.7.7.7:9876"));

        DiscoveryGroupConfiguration discConf = new DiscoveryGroupConfiguration();
        discConf.setName("meine-discovery-gruppe");

        BroadcastEndpointFactory factoryEndpoint = new UDPBroadcastEndpointFactory();
        ((UDPBroadcastEndpointFactory) factoryEndpoint).setGroupAddress("231.7.7.7");
        ((UDPBroadcastEndpointFactory) factoryEndpoint).setGroupPort(9876);
        discConf.setBroadcastEndpointFactory(factoryEndpoint);

        ConnectionFactory factory = ActiveMQJMSClient.createConnectionFactoryWithHA(discConf, JMSFactoryType.CF);

        //ConnectionFactory factory = new ActiveMQJMSConnectionFactory(ActiveMQClient.createServerLocator("udp://231.7.7.7:9876"));

        ((ActiveMQJMSConnectionFactory) factory).setUser("admin");
        ((ActiveMQJMSConnectionFactory) factory).setPassword("admin");
        ((ActiveMQJMSConnectionFactory) factory).setReconnectAttempts(-1);
        ((ActiveMQJMSConnectionFactory) factory).setRetryIntervalMultiplier(1.0);
        ((ActiveMQJMSConnectionFactory) factory).setRetryInterval(1000);
        ((ActiveMQJMSConnectionFactory) factory).setFailoverOnInitialConnection(true);
        /*((ActiveMQConnectionFactory) factory).setInitialConnectAttempts(10);*/
        ((ActiveMQJMSConnectionFactory) factory).setPreAcknowledge(false);

        /*factory.setUser("admin");
        factory.setPassword("admin");
        factory.setReconnectAttempts(-1);*/
        return factory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws Exception {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        //factory.setSessionTransacted(true);
        //factory.setConcurrency("3-10");
        return factory;
    }

}
