package com.bloomtech.asylumrgbea.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AsylumCaseRepository {

    @Autowired
    private final DynamoDBMapper dynamoDBMapper;

    //add methods as if this were the DAO
    public Iterable<AsylumCase> findAll() {
        return dynamoDBMapper.scan(AsylumCase.class, new DynamoDBScanExpression());
    }

//    public ScanResultPage<AsylumCase> find(Object[] filters) {
//        return dynamoDBMapper.scanPage(AsylumCase.class, buildScanExpression(filters));
//    }

    // TODO: Alternative Solution
    public ScanResultPage<AsylumCase> find(Map<String, List<String>> filterMap ) {
        return dynamoDBMapper.scanPage(AsylumCase.class, buildScanExpression(filterMap));
    }

    public void saveAll(Iterable<AsylumCase> cases) {
        dynamoDBMapper.batchSave(cases);
    }

    private DynamoDBScanExpression buildScanExpression(Object[] filters) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
        Map<String, AttributeValue> valueMap = new HashMap<>();
        String filterExpression = "";

        if (filters[0] != null) {
            // TODO: Requires Multiple AttributeValues
            valueMap.put(":citizenship", new AttributeValue((String) filters[0]));
            filterExpression = buildFilterExpression(filterExpression, "citizenship");
        }
        if (filters[1] != null) {

            valueMap.put(":caseOutcome", new AttributeValue((String) filters[1]));
            filterExpression = buildFilterExpression(filterExpression, "caseOutcome");
        }
        if (filters[4] != null) {
            valueMap.put(":currentDate", new AttributeValue((String) filters[4]));
            filterExpression = buildFilterExpression(filterExpression, "currentDate");
        }
        if (filters[5] != null) {
            // TODO: Requires Multiple AttributeValues
            valueMap.put(":asylumOffice", new AttributeValue((String) filters[5]));
            filterExpression = buildFilterExpression(filterExpression, "asylumOffice");
        }

        if (!filterExpression.isEmpty()) {
            dynamoDBScanExpression.withExpressionAttributeValues(valueMap);
            dynamoDBScanExpression.withFilterExpression(filterExpression);
        }

        return dynamoDBScanExpression.withConsistentRead(false);
    }

    // TODO: Alternative Solution.
    private DynamoDBScanExpression buildScanExpression(Map<String, List<String>> filterMap) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
        Map<String, AttributeValue> valueMap = new HashMap<>();
        String filterExpression = "";

        for (Map.Entry<String, List<String>> entry : filterMap.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                valueMap.put(":" + entry.getKey(), new AttributeValue().withS(entry.getValue().get(0)));
            }
        }

        return dynamoDBScanExpression.withConsistentRead(false);
    }

    private String buildFilterExpression(String filterExpression, String addedFilter) {
        if (filterExpression.equals("")) {
            return filterExpression + addedFilter + " = :" + addedFilter;
        }
        return filterExpression + " AND " + addedFilter + " = :" + addedFilter;
    }

    private String buildDateFilterExpression(String filterExpression, String dateFrom, String dateTo) {
        // TODO: Need to implement
        return null;
    }
}
