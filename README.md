# Artemis ActiveMQ

## Goal
- create an environment in order to simulate various use cases (docker compose)
- install and configure several artemis activemq brokers, that participate in a cluster
- brokers should be high available (automatic failover, loadbalancing)
- producer & consumer applications should be configured to communicate with brokers
- play around with configuration (user/roles properties and security conf)


## Implementation
- docker compose with vromero/activemq-artemis:2.10.1 image
- 4 brokers (2 live and 2 backup)
- producerApp (spring-boot) which sends messages when accessing /chat endpoint (connect over udp broadcast)
- consumerApp (spring-boot) which connects to the cluster over udp and broadcast
- configuration files for user/roles/high-availability/cluster that are attached to the container (docker volumes)

The producer sends messages to the cluster. And the consumer connects to the cluster (only to one broker) and consumes 
the messages and prints them out. The cluster is **symmetric**, means every node in the cluster is connected to every 
other node over one hop. The live servers(activemq0, activemq2) in the cluster are connected over cluster connection. The 
load is distributed among them. The backup server become active, when the live server shuts down. 
## Build & Run
first the java application (producer) has to be compiled and linked! <br/>
**/messaging_poc_infrastructure/demo$**
        `mvn clean install`

then the docker environment has to be set up <br />
**/messaging_poc_infrastructure$**
```docker-compose up -d --build```

to shutdown the environment <br />
**/messaging_poc_infrastructure$**
```docker-compose down```

call producer endpoint /chat in order to generate test message on testQueue topic <br />
**/messaging_poc_infrastructure$**
```curl http://localhost:8081/chat```

to print the log outputs of consumer <br />
**/messaging_poc_infrastructure$**
```docker logs -f messaging_poc_infrastructure_consumer_app_1```


login on  http://localhost:8161/console<br />
**user: admin** <br/>
**password: admin**


## Use cases
http://localhost:8081/chatLoop?loop=true&duration=30&sleep=100

- sends messages in a loop 
- duration means the amount of messages
- sleep is the timeout between each message
- send the messages and the consumer will display them in the console output (docker logs)

### 2 brokers in one cluster
- broker balance the load between 2 nodes

