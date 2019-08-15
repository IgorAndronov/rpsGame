# Rock-Paper-Scissors Game App
Code for Rock-Paper-Scissors game services

##Architecture

According to the business requirements the solution should fulfill SLA 1M requests per day.
So as target architecture the micro-service architecture were selected. It gives the possibility of good horizontal scaling and spreading of the load between regions.
For data storing purposes Cassandra DB were chosen as it gives very high performance for simple read/writes operations and easy to scale.

The solution is built on Spring Boot framework && Cloud stack and consist of the following elements:
UI Gatway - ZUUL
Srvice registry/discovery - EUREKA (monitoring url(default): http://localhost:8761)
Config server
Game service
Statistics service
Cassandra cluster


![Alt solution](./architecture.png?raw=true "Architecture")

##Game Algoritm explanation:
Assumptions:

1. According to the requirements the user is bad in generation random combinations. It means the user strategy will be individual and can be changed in time based on previous results.
2. As many different users generates individual sequences of game combinations it has no sense to analyze overall user statistics as it will lead to uniform distribution. Instead of this individual user statistics should be taken into account to consider personal nature of the user strategy
3. As user strategy can be changed in time the server algorithm should adjust its behavior based on recent last user actions.
For that purposes period for annalists shouldn't be taken very big as in this case server wouldn't react dynamically enough and can produce similar results that user can mention and make a profit from this.

###Algoritm description:
"game movement(gm)" -user combination (stone or scissors or paper)
"gs" game sequence. Sequence of "gm". Example "sc" ("s" -stone; c -scissors, p- paper):
"s", "p", "c",..."p","s", "p","s", "p"

If statistics data is collected in "enough for prediction" amount(parametrised) then the following algoritm is applied:
Server simply collects last user "game movement" statistics and analize frequeency of last combination(gm).
First iteration:
select last combination(gs) ("p" in example) and search the frequency of the upcomming step(gs). Upcomming gs is the gs that goes immidiatly after current one(in example "c" 1 time and "s" 2 times). If probability of some "gm"("s" in the example) is hire enough(parametrised) then this "gm" is considered as probable user next step(gm).
Second iteration: 
if probability level of step one is not high enough then combination of last two "gm"("s""p") is analised and algoritm is repeated
Iteration N:
same for n last "gm"
If probabilty level is not fullfiled for n-iterations then just more frequent "gm" is found and considered as most probable next user step(gm) 
If statistics amount is not enough for the prediction(parametrised) then random combination is used.


How to Run:



## How to run?

### Start Cassandra
docker pull cassandra
docker run --name cas1 -p 9042:9042 -e CASSANDRA_CLUSTER_NAME=AcmeGamesCluster -e CASSANDRA_ENDPOINT_SNITCH=GossipingPropertyFileSnitch -e CASSANDRA_DC=datacenter1 -d cassandra
docker run --name cas2 -p 19042:9042 -e CASSANDRA_SEEDS="$(docker inspect --format='{{ .NetworkSettings.IPAddress }}' cas1)" -e CASSANDRA_CLUSTER_NAME=AcmeGamesCluster -e CASSANDRA_ENDPOINT_SNITCH=GossipingPropertyFileSnitch -e CASSANDRA_DC=datacenter1 -d cassandra

check statusess:
docker exec -ti cas1 nodetool status

###Start servicess:
1. mvn clean install
2. start Cassandra cluster in docker (remember docker address)
3. start ConfigServerApplication
    a) change Cassandra endpoint in ./config-server/src/main/resources/config-repo/application.properties
       example: cassandra.contactpoints=192.168.99.102 (docker address)
    b) ./config-server>mvn spring-boot:run
4. start ServiceRegistryApplication
    ./service-registry>mvn spring-boot:run
5. start UiProxyApplication 
      ./proxy>mvn spring-boot:run
6. start RpsGameApplication 
      ./game-service>mvn spring-boot:run
7. start RpsStatsApplication 
      ./statistics-service>mvn spring-boot:run       

Monitor services:
http://localhost:8761/  
            

             
