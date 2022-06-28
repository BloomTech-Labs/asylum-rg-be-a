package com.bloomtech.asylumrgbea.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * An API to map json to a java object using Http requests.
 */
@DynamoDBTable(tableName = "asylum_cases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsylumCase {

    @Id
    @DynamoDBHashKey(attributeName = "id")
    private String id;

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

    @DynamoDBAttribute
    private String asylumOffice;
}
