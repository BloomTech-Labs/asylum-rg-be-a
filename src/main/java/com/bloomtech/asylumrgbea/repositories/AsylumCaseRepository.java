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

    public Iterable<AsylumCase> findAll() {
        return dynamoDBMapper.scan(AsylumCase.class, new DynamoDBScanExpression());
    }

    /**
     * Filters for AsylumCases based on the filterMap and rangeMap. The keys of both maps
     * must reflect the attributes of an AsylumCase.
     * @param filterMap Map of String to List of String representing the equality filters
     * @param rangeMap Map of String to Array of String representing the range filters
     * @return ScanResultPage of AsylumCases
     */
    public ScanResultPage<AsylumCase> find(Map<String, List<String>> filterMap, Map<String, String[]> rangeMap) {
        Map<String, AttributeValue> valueMap    = this.generateValueMap(filterMap, rangeMap);
        String filterExpression                 = this.generateFilterExpression(filterMap, rangeMap);

        return dynamoDBMapper.scanPage(AsylumCase.class, this.generateScanExpression(valueMap, filterExpression));
    }

    /**
     * Saves an Iterable AsylumCases to DynamoDB.
     * @param cases Iterable AsylumCases to add to the persistent layer
     */
    public void saveAll(Iterable<AsylumCase> cases) {
        dynamoDBMapper.batchSave(cases);
    }

    /**
     * Private helper method that generates the DynamoDBScanExpression.
     * @param valueMap Map of String to AttributeValues
     * @param filterExpression String representation of logical operators
     * @return DynamoDBScanExpression
     */
    private DynamoDBScanExpression generateScanExpression(Map<String, AttributeValue> valueMap,
                                                          String filterExpression) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();

        if (filterExpression.length() != 0) {
            dynamoDBScanExpression.withExpressionAttributeValues(valueMap);
            dynamoDBScanExpression.withFilterExpression(filterExpression);
        }

        dynamoDBScanExpression.withConsistentRead(false);

        return dynamoDBScanExpression;
    }

    /**
     * Private helper method that generates the String of logical operators.
     * @param filterMap Map of String to List of String representing the equality filters
     * @param rangeMap Map of String to Array of String representing the range filters
     * @return String representation of logical evaluation operators
     */
    private String generateFilterExpression(Map<String, List<String>> filterMap, Map<String, String[]> rangeMap) {
        StringBuilder filterExpression = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : filterMap.entrySet()) {
            List<String> filterValues = entry.getValue();

            if (filterValues.size() != 0) {
                if (filterExpression.length() != 0) {
                    filterExpression.append(" AND ");
                }

                for (int i = 0; i < filterValues.size(); i++) {
                    if (i == 0) {
                        filterExpression.append("(")
                                .append(entry.getKey())
                                .append(" = :")
                                .append(entry.getKey())
                                .append(i);
                    }

                    if (i != 0) {
                        filterExpression.append(" OR ")
                                .append(entry.getKey())
                                .append(" = :")
                                .append(entry.getKey())
                                .append(i);
                    }

                    if (i == filterValues.size() - 1) {
                        filterExpression.append(")");
                    }
                }
            }
        }

        for(Map.Entry<String, String[]> entry : rangeMap.entrySet()) {
            String[] rangeFilters = entry.getValue();

            for (int i = 0; i < rangeFilters.length; i++) {
                if (rangeFilters[i] != null) {
                    if (filterExpression.length() != 0) {
                        filterExpression.append(" AND ");
                    }

                    filterExpression.append("(")
                            .append(entry.getKey())
                            .append(i == 0 ? " >= :" : " <= :")
                            .append(entry.getKey())
                            .append(i)
                            .append(")");
                }
            }
        }

        return filterExpression.toString();
    }

    /**
     * Private helper method that generates the Map of String to AttributeValue.
     * @param filterMap Map of String to List of String representing the equality filters
     * @param rangeMap Map of String to Array of String representing the range filters
     * @return Map of String to AttributeValue
     */
    private Map<String, AttributeValue> generateValueMap(Map<String, List<String>> filterMap,
                                                         Map<String, String[]> rangeMap) {
        Map<String, AttributeValue> valueMap = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : filterMap.entrySet()) {
            List<String> filterValues = entry.getValue();

            for (int i = 0; i < filterValues.size(); i++) {
                valueMap.put(String.format(":%s%s", entry.getKey(), i), new AttributeValue(filterValues.get(i)));
            }
        }

        for(Map.Entry<String, String[]> entry : rangeMap.entrySet()) {
            String[] rangeFilters = entry.getValue();

            for (int i = 0; i < rangeFilters.length; i++) {
                if (rangeFilters[i] != null) {
                    valueMap.put(String.format(":%s%s", entry.getKey(), i), new AttributeValue(rangeFilters[i]));
                }
            }
        }

        return valueMap;
    }
}
