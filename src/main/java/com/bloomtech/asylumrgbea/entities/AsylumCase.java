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
 * Defines the POJO entity to represent an asylum case within the DynamoDB database.
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
    private String caseOutcome;

    @DynamoDBAttribute
    private String completionDate;

    @DynamoDBAttribute
    private String currentDate;

    @DynamoDBAttribute
    private String asylumOffice;

    @Override
    public String toString() {
        return "AsylumCase{" +
                "id='" + id + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", caseOutcome='" + caseOutcome + '\'' +
                ", completionDate='" + completionDate + '\'' +
                ", currentDate='" + currentDate + '\'' +
                ", asylumOffice='" + asylumOffice + '\'' +
                '}';
    }
}