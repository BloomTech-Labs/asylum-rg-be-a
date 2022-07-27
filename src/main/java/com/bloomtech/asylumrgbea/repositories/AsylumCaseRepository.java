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

@Repository
@RequiredArgsConstructor
public class AsylumCaseRepository {

    @Autowired
    private final DynamoDBMapper dynamoDBMapper;

    //TODO Change tests to use new find method
    public Iterable<AsylumCase> findAll() {
        return dynamoDBMapper.scan(AsylumCase.class, new DynamoDBScanExpression());
    }

    public ScanResultPage<AsylumCase> find(Map<String, List<String>> filterMap ) {
        return dynamoDBMapper.scanPage(AsylumCase.class, buildScanExpression(filterMap));
    }

    public void saveAll(Iterable<AsylumCase> cases) {
        dynamoDBMapper.batchSave(cases);
    }



    // TODO: implement scanning between dates and fiscal year
    private DynamoDBScanExpression buildScanExpression(Map<String, List<String>> filterMap) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
        Map<String, AttributeValue> valueMap = new HashMap<>();
        StringBuilder filterExpression = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : filterMap.entrySet()) {
            List<String> filterValues = entry.getValue();

            if (filterValues.size() != 0) {
                if (filterExpression.length() != 0) {
                    filterExpression.append(" AND ");
                }

                for (int i = 0; i < filterValues.size(); i++) {
                    valueMap.put(":" + entry.getKey() + i, new AttributeValue(filterValues.get(i)));
                    if (i == 0) {
                        filterExpression.append("(" + entry.getKey() + " = :" + entry.getKey() + i);
                    }

                    if (i != 0) {
                        filterExpression.append(" OR " + entry.getKey() + " = :" + entry.getKey() + i);
                    }

                    if (i == filterValues.size() - 1) {
                        filterExpression.append(")");
                    }
                }
            }
        }

        if (filterExpression.length() != 0) {
            dynamoDBScanExpression.withExpressionAttributeValues(valueMap);
            dynamoDBScanExpression.withFilterExpression(filterExpression.toString());
        }

        return dynamoDBScanExpression.withConsistentRead(false);
    }

    private String buildDateFilterExpression(String filterExpression, String dateFrom, String dateTo) {
        // TODO: Need to implement
        return null;
    }
}
