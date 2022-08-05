# Raw Data Import Process

## Purpose
The stakeholder will need to import a raw dataset into the database once a year. 
A script is useful to help automate this task.
The stakeholder is able to use a command line to initiate the script.

## Overview
- Use a Python script to clean the original `Asylum Case Data` and convert to .csv 
- Use AWS implementation for bulk .csv ingestion to import cleaned data to DynamoDB

## Import Process
### Prerequisites
1. IAM User with AWS permissions
   - CloudFormation
   - DynamoDB 
   - Lambda
   - S3 
2. AWS CLI Installed _(Latest Version)_
   - aws_access_key_id
   - aws_secret_access_key
3. Python3 Installed with Pandas Library _(Latest Versions)_

### Requirements
- [COW2021001887-I589Data.xlsx](https://bloomtech.notion.site/HRF-Asylum-Report-Generator-412062ca7640457682c33295b21a25c3#:~:text=FE%3A%20React%20(JS)-,Resources,-Asylum%20decision%20data) _(Resources Section)_
- [asylumCaseXlsxToCleanCsv.py](/configurations/asylumCaseXlsxToCleanCsv.py)
- [CSVToDynamoDB.template.json](/configurations/CSVToDynamo.template.json)

### Importing
#### Deploy Stack 
1. Open a CLI in the root of this project
2. Execute the CLI command for us-west-2 `aws cloudformation create-stack --region us-west-2 --stack-name asylum-rg-be-a --template-body file://configurations/CSVToDynamo.template.json --capabilities CAPABILITY_IAM`
3. The stack will a build DynamoDB Table `asylum_cases`, S3 Bucket named `asylum-cases` with lambda trigger, and a Lambda Function

#### Clean Data
The current configuration requires the [Excel file](https://bloomtech.notion.site/HRF-Asylum-Report-Generator-412062ca7640457682c33295b21a25c3#:~:text=FE%3A%20React%20(JS)-,Resources,-Asylum%20decision%20data) 
and [Python script](/configurations/asylumCaseXlsxToCleanCsv.py) to reside in the same directory
1. Place the files in the same directory `(ex. Data_Import/_both files_)`
2. Change the name of the Excel file to `data.xlsx`
3. Open a CLI in the same path as the files
4. Execute the CLI command `python3 asylumCaseXlsxToCleanCsv.py`
5. The previous command will generate `asylum-cases-data.csv` in the same directory in accordance with S3 bucket naming standards<sup>2</sup>
  
#### Upload Data to S3 Bucket
- Open a CLI in the same directory as `asylum-cases-data.csv`
- Execute the CLI command `aws s3 cp <your-directory>/asylum-cases-data.csv s3://asylum-cases/asylum-cases-data.csv`

## Functionality
### Cleaning Data
The [script](/configurations/asylumCaseXlsxToCleanCsv.py) reads the .xlsx file into Pandas<sup>5, 6</sup> DataFrame. It then generates a UUID for each row and 
sets the UUID as the index<sup>1</sup>. Next it removes unwanted columns. 
Then it renames column names to desired name in accordance
with DynamoDB naming standards<sup>3</sup>.

| Original Header | affirmative\_case\_id | asylum\_office | citizenship                 | race\_or\_ethnicity | case\_outcome | completion\_date   | data\_current\_as\_of |
| --------------- | --------------------- | -------------- | --------------------------- | ------------------- | ------------- | -------------------| --------------------- |
| New Header      | id                    | asylumOffice   | citizenship                 |                     | caseOutcome   | completionDate     | dateRecieved          |
| Original Format | n/a                   | ZSF            | CHINA, PEOPLE'S REPUBLIC OF |                     | Deny/Referral |  YYYY-MM-DD        | 2021-05-28T05:04:44.000Z
| New Format      | UUID4 (generated)     | ZSF            | CHINA, PEOPLE'S REPUBLIC OF |                     | Deny/Referral |  YYYY-MM-DD        | 2021-05-28T05:04:44.000Z

_(Table-1 Data Format Changes)_

### Stack Deployment
The AWS CLI Cloudformation command<sup>4</sup> with the location of the JSON file will create the stack. The JSON file is preconfigured
with the necessary default parameters, resources, Lambda code, and permissions to create a stack to digest .csv files to DynamoDB.

### Upload
The AWS CLI command<sup>4</sup> will upload the .csv file to S3. The file upload sends a S3 event. That event is used as a 
trigger to a lambda function. The lambda function batch uploads the file to DynamoDB.

_Alternately the S3 upload can be baked into the Python script<sup>7</sup>._

## Technical References
### AWS
1. [Implementing Bulk CSV Ingestion To AWS DynmoDB](https://aws.amazon.com/blogs/database/implementing-bulk-csv-ingestion-to-amazon-dynamodb/)
2. [AWS User Guide: Creating object key names](https://docs.aws.amazon.com/AmazonS3/latest/userguide/object-keys.html)
3. [AWS Dev Guide: Supported data types and naming rules in Amazon DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.NamingRulesDataTypes.html#HowItWorks.NamingRules)
4. [AWS CLI Command Reference](https://docs.aws.amazon.com/cli/latest/index.html)
### Python
5. [Pandas Cleaning Example: Robert Sharp](https://github.com/BrokenShell/PandasCleaning)
6. [Pandas: API reference](https://pandas.pydata.org/docs/reference/index.html)

### (Alternatives)
7. [How to Upload And Download Files From AWS S3 Using Python (2022)](https://towardsdatascience.com/how-to-upload-and-download-files-from-aws-s3-using-python-2022-4c9b787b15f2#:~:text=Let%27s%20start%20with%20the%20download.%20After%20importing%20the%20package%2C%20create%20an%20S3%20class%20using%20the%20client%20function%3A)
