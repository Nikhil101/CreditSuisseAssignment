# Credit Suisse Log File Handling Problem Summary

Our custom-build server logs different events to a file named logfile.txt. Every event has 2 entries in the file - one entry when the event was started and another when the event was finished. The entries in the file have no specific order (a finish event could occur before a start event for a given id)
Every line in the file is a JSON object.
The program should:
- Take the path to logfile.txt as an input argument
- Parse the contents of logfile.txt
- Flag any long events that take longer than 4ms
- Write the found event details to file-based HSQLDB (http://hsqldb.org/) in the working folder
- The application should create a new table if necessary and store the following values:
> Event id

> Event duration

> Type and Host if applicable

> Alert


## Implementation Details
- The program takes server logs text file as an input as part of command line argument (please provide the full path of input file).
- I am making one entry in the database table - 'Event' for every unique event specified in log file on the basis of ID i.e. ID is considered as primary key in the table and irrespective of event lifecycle (STARTED,FINISHED an entry is made). As the events are asyncronous and in assignment it is not mentioned whether to have validation on event lifecycle hence, such implementation
- I have tried to log errors and info logs wherever possible. I didn't establish throwing exception till the root call hierarchy as the program didn't demand such implementation however that was also one of the exception implementation ways that could've been done
- I have also written test cases which covers more than 50% test coverage to cover the major parts except data layer testing. 
- The reason to implement with below stack was to showcase ability and knowledge over different libraries and framework. Other approach could have been via simple core java and JDBC implementation as well which I tried to avoid
- Code comments are written wherever possible for better understanding of the implementation
- In application.properties, I have set `spring.jpa.hibernate.ddl-auto=create`, so that table gets created everytime while execution. There has been nothing mentioned about persisting the data hence such property defined. Rest of the properties which are used can be check in application.properties file
- Once the jar completes it's execution, you can see the result printed in the command prompt. I will add a screenshot as well below
- There is also a sample input.txt created in `src\main\resources` for reference


#### Technical Stack/Libraries Used

- Java 8 - Java is used as programming language. Some java 8 features used in project includes - parallel streams, lambda
- Spring Boot Framework - For stand-alone spring based application
- Spring  Data - For integration with database/persistence layer
- Lombok - To minimize the boilerplate code like - getters, setters, logger, constructors, etc.
- HSQLDB - To store data in file type database 
- Google gson - To convert java objects into their JSON representation and vice-versa
- Slf4j - For logging purpose. This has been inherited as part of project lombok hence used
- JUnit - For unit test cases and coverage
- Maven - As project build tool

## Requirements
- Java - You can check if java is installed in you machine by executing following command in command prompt/shell.
```
java -version
```
- Maven - You can check if java is installed in you machine by executing following command in command prompt/shell 
```
mvn -version
```
Also please ensure that %JAVA_HOME% and Maven Path are been set on your system.

## How to Run the program
The program takes one command line argument as an input. This argument should be the path to the file you want to give as an input which has server log events.

Below are the steps you need to perform once the project is cloned to your system -
- Step 1: Open Command Prompt and Go to Folder Location where repository is cloned
```
example - cd <project-directory>
```
- Step 2: Run below commands in command prompt
```
mvn clean install
```
```
mvn clean package
```
- Step 3: Now execute jar with below command
```
java -jar LogFile-0.0.1-SNAPSHOT.jar E:\input.txt
```

## Results
After successful execution you can see below output in command prompt where the jar was executed

```
****************************Result****************************


Event(eventId=scsmbstgra, timestamp=5, type=APPLICATION_LOG, host=12345, longEventFlag=true)
Event(eventId=scsmbstgrb, timestamp=3, type=null, host=null, longEventFlag=false)
Event(eventId=scsmbstgrc, timestamp=8, type=null, host=null, longEventFlag=true)


**************************************************************
```