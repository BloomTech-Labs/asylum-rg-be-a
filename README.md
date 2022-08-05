# Asylum Report Generator Backend

A **search tool** to give a user-friendly view into a large dataset of asylum case decisions. <br/>
This concept is similar to the [TRAC database](https://trac.syr.edu/phptools/immigration/asylum/) — a popular 
existing tool, but with a far more robust ability to select and filter data.
* A user can generate a table of results, filtered by multiple parameters
* A user can generate a real-time graph to represent what trend looks
  like over time (Release 4)
* A user can generate a heatmap of asylum case outcomes (Release 5)
* A user can download the results of their search (Release 6)

### Design Document / Repos / Setup
Document can be found [**HERE**](documentation/design_document.md) <br/>
Docs repo can be found [**HERE**](https://github.com/BloomTech-Labs/asylum-rg-docs) <br/>
Frontend repo can be found [**HERE**](https://github.com/BloomTech-Labs/asylum-rg-fe) <br/>
Backend Setup instructions can be found [**HERE**](https://www.notion.so/bloomtech/BE-Local-Setup-794f197185c046ccb9e2e9f073268cbe)


### Tech Stack

- Backend: Java + Spring Boot
- Cloud Storage: Amazon S3
- Database: Amazon DynamoDB
<p align="left">
<a href="https://www.java.com" target="_blank" rel="noreferrer"> 
<img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="60" height="60"/> </a>
<a href="https://spring.io/projects/spring-boot" target="_blank" rel="noreferrer"> 
<img src="/documentation/images/spring_boot_logo.png" alt="spring" width="60" height="60"/> </a>
<a href="https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Introduction.html" target="_blank" rel="noreferrer"> 
<img src= "documentation/images/aws_dynamodb.svg" alt="DynamoDB" width="60" height="60"/> </a>
<a href="https://docs.aws.amazon.com/AmazonS3/latest/userguide/Welcome.html" target="_blank" rel="noreferrer"> 
<img src= "documentation/images/aws_s3.png" alt="S3" width="60" height="60"/> </a>
</p>

- Frontend: React + Redux + Plotly
<p align="left">
<a href="https://reactjs.org/" target="_blank" rel="noreferrer"> 
<img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/react/react-original-wordmark.svg" alt="react" width="60" height="60"/> </a>
<a href="https://redux.js.org/" target="_blank" rel="noreferrer"> 
<img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/redux/redux-original.svg" alt="react" width="60" height="60"/> </a>
<a href="https://plotly.com/" target="_blank" rel="noreferrer"> 
<img src= "documentation/images/plotly.png" alt="Plotly" width="165" height="53"/> </a>
</p>

