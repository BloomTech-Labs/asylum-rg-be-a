# Raw Data Import Process

## Purpose
The stakeholder will need to import a raw dataset into the database once a year. A script is useful to help automate this task.
The stakeholder is able to use a command line to initiate the script.

## Overview
- Use a Python script to clean the original `Asylum Case Data` and convert to .csv. 
- Use AWS implementation for bulk .csv ingestion to import cleaned data to DynamoDB.

## Import Process
### Prerequisites
1. IAM User with AWS permissions
   - CloudFormation
   - DynamoDB 
   - Lambda
   - S3 
2. AWS CLI Installed
      - aws_access_key_id
      - aws_secret_access_key
3. Python3 

### Requirements
- [COW2021001887-I589Data.xlsx](https://bloomtech.notion.site/HRF-Asylum-Report-Generator-412062ca7640457682c33295b21a25c3#:~:text=FE%3A%20React%20(JS)-,Resources,-Asylum%20decision%20data) _(Resources Section)_
- AsylumCaseXlsxToCleanCsv.py
- CSVToDynamoDB.template.json

[comment]: <> (Todo link resources above)

### Importing
#### 1. Clean Data - `Execute` AsylumCaseXlsxToCleanCsv.py

- different ways to execute command
  - Current setup
  - Lambda
  
#### 2. Deploy Stack 
- ClI command
#### 3. Upload Data to S3 Bucket <sup>1<sup>
- CLI command
- Add to script

## Functionality
### Cleaning Data
- read
- uuid column set as index with UUID
- remove Unwanted Columns
- rename column names to desired name and in accordance with DynamoDB nameing standards

### Stack Deployment
- json file explainer
- params
- resources
- code
- command
- 
### Upload
- Enter AWS cli command
- trigger event
- Lambda 
- dynamodb

## Technical References
### Python
- [Pandas Cleaning Example: Robert Sharp](https://github.com/BrokenShell/PandasCleaning)
- [Pandas: API reference](https://pandas.pydata.org/docs/reference/index.html)

### AWS
- [Implementing Bulk CSV Ingestion To AWS DynmoDB](https://aws.amazon.com/blogs/database/implementing-bulk-csv-ingestion-to-amazon-dynamodb/)
- [AWS User Guide: Creating object key names](https://docs.aws.amazon.com/AmazonS3/latest/userguide/object-keys.html)
- [AWS Dev Guide: Supported data types and naming rules in Amazon DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.NamingRulesDataTypes.html#HowItWorks.NamingRules)
- [AWS CLI Command Reference](https://docs.aws.amazon.com/cli/latest/index.html)

### (Alternatives)
1. [How to Upload And Download Files From AWS S3 Using Python (2022)](https://towardsdatascience.com/how-to-upload-and-download-files-from-aws-s3-using-python-2022-4c9b787b15f2#:~:text=Let%27s%20start%20with%20the%20download.%20After%20importing%20the%20package%2C%20create%20an%20S3%20class%20using%20the%20client%20function%3A)
