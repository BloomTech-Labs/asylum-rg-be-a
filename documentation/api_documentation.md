
# The Asylum Case Generator API

The **Human Rights First: Asylum Report Generator** is a public REST API for retrieving records of government asylum 
case data that has been provided for public use by Freedom of Information Act requests. The API currently only supports
`GET` method requests to a single endpoint `/cases` which retrieves asylum case records provided as `CaseResponseDto`
objects encapsulated within a `PageResponseDto` object. There is no Authentication nor Authorization required to access
the API at this time.

### Data Models

As described above, the response to the `/cases` endpoint returns a `PageResponseDto` which is illustrated below in a
general JSON format. A `PageResponseDto` consists of two attributes. First, `totalPages` is an `Integer` representing
the total number of pages of asylum cases that match the query parameters. Second, `page` is the `Iterable` collection
containing a given number of asylum cases that match the criteria provided in the query parameters, encapsulated in
`CaseResponseDto` objects. The amount of cases and which cases are returned is based on the `limit` and `page` query
parameters.

```
PageResponseDto {
    "totalPages": Number,
    "page": [
        CaseResponseDto {
            "citizenship": "String",
            "caseOutcome": "String",
            "completionDate": "yyyy-MM-dd",
            "asylumOffice": "String"
        }
    ]
}
```

In the instance that no asylum cases can be found, such as providing a combination of query parameters that leads to
an empty result or providing invalid query parameter values, an `ErrorDto` will be returned with a specific message
describing the error.

```
ErrorDto {
    "message": String
}
```

### Query Parameters

The table below describes the different query parameters that can be appended to the `GET` request to narrow and refine
the returned data by one or more attributes. Multiple values can be provided for the `citizenship` category insofar as 
the values are delimited by a zero character; the `outcome` and `office` categories can also have multiple values
insofar as the values are delimited by a comma character.

The `from` and `to` categories are parameters to filter for ranges of dates, specifically the completion date. Providing
only the `from` parameter will filter dates **from** the floor. Providing only the `to` parameter will filter dates
**to** the ceiling, inclusive. When providing both categories, a range will be formed between the `from` and `to` values
inclusive.

| Filtering Parameter                  | Parameter Description                                                                          |
|-----------------------------------|------------------------------------------------------------------------------------------------|
| **limit** (Integer)                   | The maximum number of asylum cases to populate per page (minimum value of 1)                   |
| **page**	(Integer)                   | The page number to access in the returned pages (minimum value of 1)                        |
| **citizenship** (String, 0-delimited) | The asylum case petitioners nation of origin, per their birth record                           |
| **outcome** (String, comma-delimited) | The asylum case result (Admin Close/Dismissal, Deny/Referral, Grant)                           |
| **from**	(ISO-8061 yyyy-MM-dd)       | The minimum completion date to include (floor)                                          |
| **to** (ISO-8061 yyyy-MM-dd)          | The maximum completion date to include (ceiling)                                              |
| **office** (String, comma-delimited)  | The three letter String representing the US Asylum Office district in which the case was heard |

## Example `/cases` Endpoint `GET` Calls using Limit and Page Parameters

A `GET` request is made to the `/cases` endpoint without any filtering parameters. All asylum cases will be returned with
a default `limit` value of 10 and a default `page` value of 1. In this example there are 13 entries within the database.

```
http://localhost:8080/cases
```
```
{
    "totalPages": 2,
    "page": [
        {
            "citizenship": "CHINA, PEOPLE'S REPUBLIC OF",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2018-10-18",
            "asylumOffice": "ZLA"
        }
        {
            "citizenship": "ARMENIA",
            "caseOutcome": "Admin Close/Dismissal",
            "completionDate": "2017-3-7",
            "asylumOffice": "ZSF"
        }
        
        ...
        
        {
            "citizenship": "CHINA, PEOPLE'S REPUBLIC OF",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2018-10-18",
            "asylumOffice": "ZLA"
        }
    ]
}
```

Following the previous example, a `GET` request is made to the `/cases` endpoint with a `limit` value of 5 and a `page`
value of 2 with no other query parameters or filtering criteria. In this case, pages of 5 asylum cases will be
generated and the second page of the results will be returned, following the same assumption of 13 entries in the
database.
```
http://localhost:8080/cases?limit=5&page=2
```
```
{
    "totalPages": 3,
    "page": [
        {
            "citizenship": "RUSSIA",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2018-10-18",
            "asylumOffice": "ZLA"
        }
        {
            "citizenship": "ARMENIA",
            "caseOutcome": "Admin Close/Dismissal",
            "completionDate": "2017-3-7",
            "asylumOffice": "ZSF"
        } 
        
        ...
        
        {
            "citizenship": "CHINA, PEOPLE'S REPUBLIC OF",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2018-10-18",
            "asylumOffice": "ZLA"
        }
    ]
}
```

However, if the requested `page` number exceeds the number of possible pages based on the parameters of the request,
then an `ErrorDto` will be returned with a message describing the cause. In this case where the provided `page` is 5 and
the previous assumptions remain the same, the error message will specify that the client is attempting to reach an
unreachable page as there are only 3 possible pages given those parameters.
```
http://localhost:8080/cases?limit=5&page=5
```
```
{
    "message": "Error: No pages left to access..."
}
```

## Example `/cases` Endpoint `GET` Calls using Filtering Parameters

A `GET` request is made to the `/cases` endpoint with a single filtering parameter, a `citizenship` value of `RUSSIA`, 
so only asylum cases with a citizenship value of Russia will be returned.  In this example there are only three asylum
cases with a citizenship value of Russia in the database.
```
http://localhost:8080/cases?citizenship=RUSSIA
```

```
{
    "totalPages": 1,
    "page": [
        {
            "citizenship": "RUSSIA",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2018-10-18",
            "asylumOffice": "ZLA"
        }
        {
            "citizenship": "RUSSIA",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2017-3-7",
            "asylumOffice": "ZSF"
        }    
        {
            "citizenship": "RUSSIA",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2018-10-18",
            "asylumOffice": "ZLA"
        }
    ]
}
```
Following the previous example, a `GET` request is made to the `/cases` endpoint with a single filtering parameter:
a `citizenship` value of `RUSSIA0ARMENIA.` In this case, all asylum cases with the citizenship value of Russia or
Armenia will be returned. As before, there are only 3 asylum cases with a citizenship value of Russia and 1 asylum case
with a citizenship value of Armenia in the database.

```
http://localhost:8080/cases?citizenship=RUSSIA0AREMENIA
```

```
{
    "totalPages": 1,
    "page": [
        {
            "citizenship": "RUSSIA",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2018-10-18",
            "asylumOffice": "ZLA"
        }
        {
            "citizenship": "RUSSIA",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2017-3-7",
            "asylumOffice": "ZSF"
        }    
        {
            "citizenship": "RUSSIA",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2018-10-18",
            "asylumOffice": "ZLA"
        }
        {
            "citizenship": "ARMENIA",
            "caseOutcome": "Admin Close/Dismissal",
            "completionDate": "2017-3-7",
            "asylumOffice": "ZSF"
        }
    ]
}
```

However, a `GET` request  made to the `/cases` endpoint with two filtering parameters, `outcome` with a value of `Grant`
and `citizenship` with a value of `RUSSIA`, will result in an `ErrorDto.` Notice that in the previous examples, all the
asylum cases with citizenship value of Russia also had an outcome value of Deny/Referral.

```
http://localhost:8080/cases?citizenship=RUSSIA&outcome=Grant
```

```
{
    "message": "ERROR: No cases were found..."
}
```

## Example `/cases` Endpoint `GET` Calls using Range Filtering Parameters

A `GET` request is made to the `/cases` endpoint with two filtering parameters: `citizenship` with a value of
`RUSSIA0ARMENIA` and `from` with a value of `2018-01-01`. In this case, only asylum cases with a citizenship value of
Russia or Armenia and with a completion date on or after January 1st, 2018 will be returned. Notice the completion
dates from the previous example.

```
http://localhost:8080/cases?citizenship=RUSSIA0AREMENIA&from=2018-01-01
```

```
{
    "totalPages": 1,
    "page": [
        {
            "citizenship": "RUSSIA",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2018-10-18",
            "asylumOffice": "ZLA"
        } 
        {
            "citizenship": "RUSSIA",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2018-10-18",
            "asylumOffice": "ZLA"
        }
    ]
}
```

A `GET` request is made to the `/cases` endpoint with two filtering parameters: `citizenship` with a value of
`RUSSIA0ARMENIA` and `to` with a value of `2018-01-01`. In this case, all asylum cases with the citizenship value of
Russia or Armenia will be returned with a completion date on or prior to January 1st of 2018. Notice the completion
dates from the previous example.

```
http://localhost:8080/cases?citizenship=RUSSIA0AREMENIA&to=2018-01-01
```

```
{
    "totalPages": 1,
    "page": [
        {
            "citizenship": "RUSSIA",
            "caseOutcome": "Deny/Referral",
            "completionDate": "2017-3-7",
            "asylumOffice": "ZSF"
        }    
        {
            "citizenship": "ARMENIA",
            "caseOutcome": "Admin Close/Dismissal",
            "completionDate": "2017-3-7",
            "asylumOffice": "ZSF"
        }
    ]
}
```

Finally, a `GET` request is made to the `/cases` endpoint with three filtering parameters: `citizenship` with a value of
`RUSSIA0ARMENIA`, `from` with a value of `2018-01-01`, and finally `to` with a value of `2018-09-01`. In this case, an
`ErrorDto` will be returned as there are no asylum cases with a citizenship value of Russia or Armenia and a completion
date between January 1st of 2018 to September 1st of 2018. Notice the completion dates of the previous examples.

```
http://localhost:8080/cases?citizenship=RUSSIA0AREMENIA&from=2018-01-01&to=2018-09-01
```
```
{
    "message": "ERROR: No cases were found..."
}
```