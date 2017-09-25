**Personal Stock Manager is a sample Java Spring application to monitor Stock Portfolio Items and portfolio total value.**

It is using Yahoo Finance to query stock prices and and after adding queried item to portfolio starts quering items prices via a background task job (Spring Batch) every 5 sec.

Java Spring sample webservice endpoint:

http://localhost:8080/stockManager/api/myPortfolio

will show all my portfolio items after they were added via Web GUI Panel:

http://localhost:8080/stockManager/

For building run 

mvn clean install

from stockManager directory. It will create file stockManager/target/stockManager.war , which can be deployed to tomcat webapps directory.


**Technologies used**

- Java Spring
- Spring Batch
- Spring Webservices
- Spring test
- JUnit
- HSQL EMBEDDED DATABASE
- Yahoo Finance API
- HTML
- JQUERY
- BOOTSTRAP CSS
- MAVEN




