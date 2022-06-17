package com.bloomtech.asylumrgbea.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamoDBTable(tableName = "AsylumCase")
@Data
@NoArgsConstructor
public class AsylumCase {

    @DynamoDBHashKey(attributeName = "id")
    private Integer id;
    @DynamoDBAttribute
    private String asylumOffice;
    @DynamoDBAttribute
    private String citizenship;
    @DynamoDBAttribute
    private String raceOrEthnicity;
    @DynamoDBAttribute
    private String caseOutcome;
    @DynamoDBAttribute
    private String completion;
    @DynamoDBAttribute
    private String currentDate;
}
