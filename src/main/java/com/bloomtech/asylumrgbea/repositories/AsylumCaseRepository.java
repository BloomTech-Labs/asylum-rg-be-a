package com.bloomtech.asylumrgbea.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AsylumCaseRepository {

    @Autowired
    private final DynamoDBMapper dynamoDBMapper;

    //add methods as if this were the DAO
    public Iterable<AsylumCase> findAll() {
        return dynamoDBMapper.scan(AsylumCase.class, new DynamoDBScanExpression());
    }

    public void saveAll(Iterable<AsylumCase> cases) {
        dynamoDBMapper.batchSave(cases);
    }
}
