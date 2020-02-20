<p># roundup<br />opening to public since no feedback was provided</p>
<p>I attempted to solve Starling Interview challenge using reactive-stack web framework, Spring WebFlux. There were educational benefits to chosing this stack. It is a Maven project based on Spring Boot that runs embedded Netty server on JDK8. One should build it with Maven 3.6.3 or newer.</p>
<p>To start, add maven to the path and navigate to the project root.</p>
<p>To build:</p>
<p>1) $ mvn compile</p>
<p>To run:</p>
<p>2) $ mvn spring-boot:run</p>
<p>To terminate:</p>
<p>3) kill it</p>
<p>To test:</p>
<p>4) Save the token in a file called "token" in src/main/resources. Use your default encoding - JVM default encoding. If something went wrong&nbsp; - first test will fail with appropriate error.</p>
<p>5) $ mvn test</p>
<p><br />Once the Netty is running you can hit the endpoints. You must pass Starling client authorization token with each request header. ("Authorization: Bearer {yourAccessToken}")</p>
<p>The endpoints mirror starling endpoints and require matching bodies/parameters where necessary...</p>
<p style="padding-left: 30px;">.GET "/accounts"<br /> .GET "/account/{accountUid}/savings-goals"<br /> .PUT "/account/{accountUid}/savings-goals"<br />.DELETE "/account/{accountUid}/savings-goals/{savingsGoalUid}"<br /> .PUT "/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}"<br />.GET "/account/{accountUid}/category/{categoryUid}"<br /> .GET "/account/{accountUid}/category/{categoryUid}/transactions-between"<br /> <br />One exception is the below, which does the following:</p>
<p style="padding-left: 30px;">.GET "/roundup"<br /> <br />for each client account <br />&nbsp; retrieve all SETTLED transactions updated within last week <br />&nbsp; of type MASTER_CARD,FASTER_PAYMENTS_OUT with the OUT direction<br />&nbsp; &nbsp; roundup each transaction to the nearest pound and create a total<br />create new saving goal<br />transfer total to the goal</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
