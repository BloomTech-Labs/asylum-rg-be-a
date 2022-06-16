package com.bloomtech.asylumrgbea.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamoDBTable(tableName = "AsylumCase")
@Data
@NoArgsConstructor
public class AsylumCase {
//    @Getter(onMethod_ = {@DynamoDBHashKey})
    private Integer id;
    private String asylumOffice;
    private String citizenship;
    private String raceOrEthnicity;
    private String caseOutcome;
    private String completion;
    private String currentDate;
}
