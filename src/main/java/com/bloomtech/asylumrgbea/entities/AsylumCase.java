package com.bloomtech.asylumrgbea.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "asylum_cases")
@Data
@NoArgsConstructor
public class AsylumCase {

    @Id
    @DynamoDBHashKey(attributeName = "id")
    private String id;

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
