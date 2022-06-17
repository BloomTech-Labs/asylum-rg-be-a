package com.bloomtech.asylumrgbea.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@DynamoDBTable(tableName = "AsylumCase")
public class AsylumCase {

    @Id
    @DynamoDBHashKey(attributeName = "id")
    private Integer id;

    private String asylumOffice;

    private String citizenship;

    private String raceOrEthnicity;

    private String caseOutcome;

    private String completion;

    private String currentDate;
}
