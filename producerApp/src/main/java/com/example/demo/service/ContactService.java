package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.model.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.FADD;
import org.apache.activemq.artemis.api.core.BroadcastEndpointFactory;
import org.apache.activemq.artemis.api.core.DiscoveryGroupConfiguration;
import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.core.UDPBroadcastEndpointFactory;
import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ClientProducer;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;
import org.apache.activemq.artemis.api.core.client.ServerLocator;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory;
import org.apache.activemq.artemis.spi.core.remoting.ConnectorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.io.IOException;
import java.util.HashMap;

@EnableJms
@Service
public class ContactService {

    /*@Autowired
    private PlayerRepository playerRepository;*/

    @Value("${jms.queue.destination}")
    private String queueName;

    @Value("classpath:example_message.json")
    private Resource jsonExampleMessageResource;

    private JmsTemplate jmsTemplate ;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ConnectionFactory factory;

    private static Integer MESSAGE_COUNTER = 0;


    private Address address;
    private Player player;

    /*@Transactional
    public Player savePlayer(Player player){
        return playerRepository.save(player);
    }*/

    private ClientProducer producer;

    public ContactService() throws Exception{
        this.address = new Address("Goethestraße 14", "Dresden", 11390);
        this.player = new Player("gerrit", "manning", 123456789, address);

        /*ConnectionFactory factory = new ActiveMQConnectionFactory("(tcp://activemq1:61616,tcp://activemq2:61616)?ha=true");*/
        /*ConnectionFactory factory = new ActiveMQConnectionFactory("udp://231.7.7.7:9876");*/

    }

    public void chat() throws IOException, JMSException {
        jmsTemplate = new JmsTemplate(factory);
        /*String jsonMessage = new String(Files.readAllBytes(jsonExampleMessageResource.getFile().toPath()));*/
        jmsTemplate.convertAndSend(queueName, mapper.writeValueAsString(player) + "---" + MESSAGE_COUNTER++);
    }
}
