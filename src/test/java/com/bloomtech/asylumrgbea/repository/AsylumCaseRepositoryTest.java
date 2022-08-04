package com.bloomtech.asylumrgbea.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AsylumCaseRepositoryTest {
    @Mock
    DynamoDBMapper dynamoDBMapper;
    AsylumCaseRepository asylumCaseRepository;
    Map<String, List<String>> filterMap;
    Map<String, String[]> rangeMap;

    @BeforeEach
    void setup() {
        openMocks(this);
        asylumCaseRepository = new AsylumCaseRepository(dynamoDBMapper);
    }

    @AfterEach
    void teardown() {
        asylumCaseRepository = null;
        filterMap   = null;
        rangeMap    = null;
    }

    @Test
    void find_emptyFilterMapAndRangeMap_expressionAttributeAndFilterExpressionNull() {
        // GIVEN
        filterMap = Map.of(
                "citizenship",  Collections.EMPTY_LIST,
                "caseOutcome",  Collections.EMPTY_LIST,
                "asylumOffice", Collections.EMPTY_LIST
        );

        rangeMap = Map.of(
                "completion", new String[] {
                        null, null
                }
        );

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<DynamoDBScanExpression> expressionCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        when(dynamoDBMapper.scanPage(eq(AsylumCase.class), any(DynamoDBScanExpression.class)))
                .thenReturn(scanResultPage);

        // WHEN
        asylumCaseRepository.find(filterMap, rangeMap);

        // THEN
        verify(dynamoDBMapper).scanPage(eq(AsylumCase.class), expressionCaptor.capture());
        DynamoDBScanExpression dynamoDBScanExpression = expressionCaptor.getValue();

        assertFalse(dynamoDBScanExpression.isConsistentRead());
        assertNull(dynamoDBScanExpression.getExpressionAttributeValues());
        assertNull(dynamoDBScanExpression.getFilterExpression());
    }

    @Test
    void find_withFilterMapOfOneCategoryOneValue_buildsExpressionAttributeAndFilterExpressionValue() {
        // GIVEN
        String expectedExpressionClause =
                String.format("(%1$s = :%1$s0)", "citizenship");

        String substring = " AND ";
        // The total number of expectedExpressionClauses - 1
        int expectedCountOfSubstring = 0;

        Map<String, AttributeValue> expectedValueMap = Map.of(
                ":citizenship0", new AttributeValue("citizenshipA")
        );

        filterMap = Map.of(
                "citizenship",  List.of("citizenshipA"),
                "caseOutcome",  Collections.EMPTY_LIST,
                "asylumOffice", Collections.EMPTY_LIST
        );

        rangeMap = Map.of(
                "completion", new String[] {
                        null, null
                }
        );

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<DynamoDBScanExpression> expressionCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        when(dynamoDBMapper.scanPage(eq(AsylumCase.class), any(DynamoDBScanExpression.class)))
                .thenReturn(scanResultPage);

        // WHEN
        asylumCaseRepository.find(filterMap, rangeMap);

        // THEN
        verify(dynamoDBMapper).scanPage(eq(AsylumCase.class), expressionCaptor.capture());
        DynamoDBScanExpression dynamoDBScanExpression = expressionCaptor.getValue();

        assertFalse(dynamoDBScanExpression.isConsistentRead());
        assertEquals(expectedExpressionClause, dynamoDBScanExpression.getFilterExpression());
        assertEquals(expectedValueMap, dynamoDBScanExpression.getExpressionAttributeValues());
        assertEquals(expectedCountOfSubstring,
                getCountOfSubstring(dynamoDBScanExpression.getFilterExpression(), substring));
    }

    @Test
    void find_withFilterMapOfOneCategoryTwoValues_buildsExpressionAttributeAndFilterExpressionValue() {
        // GIVEN
        String expectedExpressionClause =
                String.format("(%1$s = :%1$s0 OR %1$s = :%1$s1)", "citizenship");

        String substring = " AND ";
        // The total number of expectedExpressionClauses - 1
        int expectedCountOfSubstring = 0;

        Map<String, AttributeValue> expectedValueMap = Map.of(
                ":citizenship0", new AttributeValue("citizenshipA"),
                ":citizenship1", new AttributeValue("citizenshipB")
        );

        filterMap = Map.of(
                "citizenship",  List.of("citizenshipA", "citizenshipB"),
                "caseOutcome",  Collections.EMPTY_LIST,
                "asylumOffice", Collections.EMPTY_LIST
        );

        rangeMap = Map.of(
                "completion", new String[] {
                        null, null
                }
        );

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<DynamoDBScanExpression> expressionCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        when(dynamoDBMapper.scanPage(eq(AsylumCase.class), any(DynamoDBScanExpression.class)))
                .thenReturn(scanResultPage);

        // WHEN
        asylumCaseRepository.find(filterMap, rangeMap);

        // THEN
        verify(dynamoDBMapper).scanPage(eq(AsylumCase.class), expressionCaptor.capture());
        DynamoDBScanExpression dynamoDBScanExpression = expressionCaptor.getValue();

        assertFalse(dynamoDBScanExpression.isConsistentRead());
        assertEquals(expectedExpressionClause, dynamoDBScanExpression.getFilterExpression());
        assertEquals(expectedValueMap, dynamoDBScanExpression.getExpressionAttributeValues());
        assertEquals(expectedCountOfSubstring,
                getCountOfSubstring(dynamoDBScanExpression.getFilterExpression(), substring));
    }

    @Test
    void find_withFilterMapOfMultipleCategoryMultipleValues_buildsExpressionAttributeAndFilterExpressionValue() {
        // GIVEN
        String expectedExpressionClause1 =
                String.format("(%1$s = :%1$s0 OR %1$s = :%1$s1 OR %1$s = :%1$s2", "asylumOffice");
        String expectedExpressionClause2 =
                String.format("(%1$s = :%1$s0 OR %1$s = :%1$s1)", "citizenship");
        String expectedExpressionClause3 =
                String.format("(%1$s = :%1$s0)", "caseOutcome");

        String substring = " AND ";
        // The total number of expectedExpressionClauses - 1
        int expectedCountOfSubstring = 2;

        Map<String, AttributeValue> expectedValueMap = Map.of(
                ":citizenship0", new AttributeValue("citizenshipA"),
                ":citizenship1", new AttributeValue("citizenshipB"),
                ":caseOutcome0", new AttributeValue("outcomeA"),
                ":asylumOffice0", new AttributeValue("officeA"),
                ":asylumOffice1", new AttributeValue("officeB"),
                ":asylumOffice2", new AttributeValue("officeC")
        );

        filterMap = Map.of(
                "citizenship",  List.of("citizenshipA", "citizenshipB"),
                "caseOutcome",  List.of("outcomeA"),
                "asylumOffice", List.of("officeA", "officeB", "officeC")
        );

        rangeMap = Map.of(
                "completion", new String[] {
                        null, null
                }
        );

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<DynamoDBScanExpression> expressionCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        when(dynamoDBMapper.scanPage(eq(AsylumCase.class), any(DynamoDBScanExpression.class)))
                .thenReturn(scanResultPage);

        // WHEN
        asylumCaseRepository.find(filterMap, rangeMap);

        // THEN
        verify(dynamoDBMapper).scanPage(eq(AsylumCase.class), expressionCaptor.capture());
        DynamoDBScanExpression dynamoDBScanExpression = expressionCaptor.getValue();

        assertFalse(dynamoDBScanExpression.isConsistentRead());
        assertTrue(dynamoDBScanExpression.getFilterExpression().contains(expectedExpressionClause1));
        assertTrue(dynamoDBScanExpression.getFilterExpression().contains(expectedExpressionClause2));
        assertTrue(dynamoDBScanExpression.getFilterExpression().contains(expectedExpressionClause3));
        assertEquals(expectedValueMap, dynamoDBScanExpression.getExpressionAttributeValues());
        assertEquals(expectedCountOfSubstring,
                getCountOfSubstring(dynamoDBScanExpression.getFilterExpression(), substring));
    }

    @Test
    void find_withRangeMapFloorValue_buildsExpressionAttributeAndFilterExpressionValue() {
        // GIVEN
        String expectedExpressionClause =
                String.format("(%1$s >= :%1$s0)", "completion");

        String substring = " AND ";
        // The total number of expectedExpressionClauses - 1
        int expectedCountOfSubstring = 0;

        Map<String, AttributeValue> expectedValueMap = Map.of(
                ":completion0", new AttributeValue("fromA")
        );

        filterMap = Map.of(
                "citizenship",  Collections.EMPTY_LIST,
                "caseOutcome",  Collections.EMPTY_LIST,
                "asylumOffice", Collections.EMPTY_LIST
        );

        rangeMap = Map.of(
                "completion", new String[] {
                        "fromA", null
                }
        );

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<DynamoDBScanExpression> expressionCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        when(dynamoDBMapper.scanPage(eq(AsylumCase.class), any(DynamoDBScanExpression.class)))
                .thenReturn(scanResultPage);

        // WHEN
        asylumCaseRepository.find(filterMap, rangeMap);

        // THEN
        verify(dynamoDBMapper).scanPage(eq(AsylumCase.class), expressionCaptor.capture());
        DynamoDBScanExpression dynamoDBScanExpression = expressionCaptor.getValue();

        assertFalse(dynamoDBScanExpression.isConsistentRead());
        assertEquals(expectedExpressionClause, dynamoDBScanExpression.getFilterExpression());
        assertEquals(expectedValueMap, dynamoDBScanExpression.getExpressionAttributeValues());
        assertEquals(expectedCountOfSubstring,
                getCountOfSubstring(dynamoDBScanExpression.getFilterExpression(), substring));
    }

    @Test
    void find_withRangeMapCeilingValue_buildsExpressionAttributeAndFilterExpressionValue() {
        // GIVEN
        String expectedExpressionClause =
                String.format("(%1$s <= :%1$s1)", "completion");

        String substring = " AND ";
        // The total number of expectedExpressionClauses - 1
        int expectedCountOfSubstring = 0;

        Map<String, AttributeValue> expectedValueMap = Map.of(
                ":completion1", new AttributeValue("toA")
        );

        filterMap = Map.of(
                "citizenship",  Collections.EMPTY_LIST,
                "caseOutcome",  Collections.EMPTY_LIST,
                "asylumOffice", Collections.EMPTY_LIST
        );

        rangeMap = Map.of(
                "completion", new String[] {
                        null, "toA"
                }
        );

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<DynamoDBScanExpression> expressionCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        when(dynamoDBMapper.scanPage(eq(AsylumCase.class), any(DynamoDBScanExpression.class)))
                .thenReturn(scanResultPage);

        // WHEN
        asylumCaseRepository.find(filterMap, rangeMap);

        // THEN
        verify(dynamoDBMapper).scanPage(eq(AsylumCase.class), expressionCaptor.capture());
        DynamoDBScanExpression dynamoDBScanExpression = expressionCaptor.getValue();

        assertFalse(dynamoDBScanExpression.isConsistentRead());
        assertEquals(expectedExpressionClause, dynamoDBScanExpression.getFilterExpression());
        assertEquals(expectedValueMap, dynamoDBScanExpression.getExpressionAttributeValues());
        assertEquals(expectedCountOfSubstring,
                getCountOfSubstring(dynamoDBScanExpression.getFilterExpression(), substring));
    }

    @Test
    void find_withRangeMapFloorAndCeilingValue_buildsExpressionAttributeAndFilterExpressionValue() {
        // GIVEN
        String expectedExpressionClause1 =
                String.format("(%1$s >= :%1$s0)", "completion");
        String expectedExpressionClause2 =
                String.format("(%1$s <= :%1$s1)", "completion");

        String substring = " AND ";
        // The total number of expectedExpressionClauses - 1
        int expectedCountOfSubstring = 1;

        Map<String, AttributeValue> expectedValueMap = Map.of(
                ":completion0", new AttributeValue("fromA"),
                ":completion1", new AttributeValue("toA")
        );

        filterMap = Map.of(
                "citizenship",  Collections.EMPTY_LIST,
                "caseOutcome",  Collections.EMPTY_LIST,
                "asylumOffice", Collections.EMPTY_LIST
        );

        rangeMap = Map.of(
                "completion", new String[] {
                        "fromA", "toA"
                }
        );

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<DynamoDBScanExpression> expressionCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        when(dynamoDBMapper.scanPage(eq(AsylumCase.class), any(DynamoDBScanExpression.class)))
                .thenReturn(scanResultPage);

        // WHEN
        asylumCaseRepository.find(filterMap, rangeMap);

        // THEN
        verify(dynamoDBMapper).scanPage(eq(AsylumCase.class), expressionCaptor.capture());
        DynamoDBScanExpression dynamoDBScanExpression = expressionCaptor.getValue();

        assertFalse(dynamoDBScanExpression.isConsistentRead());
        assertTrue(dynamoDBScanExpression.getFilterExpression().contains(expectedExpressionClause1));
        assertTrue(dynamoDBScanExpression.getFilterExpression().contains(expectedExpressionClause2));
        assertEquals(expectedValueMap, dynamoDBScanExpression.getExpressionAttributeValues());
        assertEquals(expectedCountOfSubstring,
                getCountOfSubstring(dynamoDBScanExpression.getFilterExpression(), substring));
    }

    @Test
    void find_withRangeMapAndFilterMapMultipleCategoriesAndValues_buildsExpressionAttributeAndFilterExpressionValue() {
        // GIVEN
        String expectedExpressionClause1 =
                String.format("(%1$s = :%1$s0 OR %1$s = :%1$s1 OR %1$s = :%1$s2", "asylumOffice");
        String expectedExpressionClause2 =
                String.format("(%1$s = :%1$s0 OR %1$s = :%1$s1)", "citizenship");
        String expectedExpressionClause3 =
                String.format("(%1$s = :%1$s0)", "caseOutcome");
        String expectedExpressionClause4 =
                String.format("(%1$s >= :%1$s0)", "completion");
        String expectedExpressionClause5 =
                String.format("(%1$s <= :%1$s1)", "completion");

        String substring = " AND ";
        // The total number of expectedExpressionClauses - 1
        int expectedCountOfSubstring = 4;

        Map<String, AttributeValue> expectedValueMap = Map.of(
                ":citizenship0", new AttributeValue("citizenshipA"),
                ":citizenship1", new AttributeValue("citizenshipB"),
                ":caseOutcome0", new AttributeValue("outcomeA"),
                ":asylumOffice0", new AttributeValue("officeA"),
                ":asylumOffice1", new AttributeValue("officeB"),
                ":asylumOffice2", new AttributeValue("officeC"),
                ":completion0", new AttributeValue("fromA"),
                ":completion1", new AttributeValue("toA")
        );

        filterMap = Map.of(
                "citizenship",  List.of("citizenshipA", "citizenshipB"),
                "caseOutcome",  List.of("outcomeA"),
                "asylumOffice", List.of("officeA", "officeB", "officeC")
        );

        rangeMap = Map.of(
                "completion", new String[] {
                        "fromA", "toA"
                }
        );

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<DynamoDBScanExpression> expressionCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        when(dynamoDBMapper.scanPage(eq(AsylumCase.class), any(DynamoDBScanExpression.class)))
                .thenReturn(scanResultPage);

        // WHEN
        asylumCaseRepository.find(filterMap, rangeMap);

        // THEN
        verify(dynamoDBMapper).scanPage(eq(AsylumCase.class), expressionCaptor.capture());
        DynamoDBScanExpression dynamoDBScanExpression = expressionCaptor.getValue();

        assertFalse(dynamoDBScanExpression.isConsistentRead());
        assertTrue(dynamoDBScanExpression.getFilterExpression().contains(expectedExpressionClause1));
        assertTrue(dynamoDBScanExpression.getFilterExpression().contains(expectedExpressionClause2));
        assertTrue(dynamoDBScanExpression.getFilterExpression().contains(expectedExpressionClause3));
        assertTrue(dynamoDBScanExpression.getFilterExpression().contains(expectedExpressionClause4));
        assertTrue(dynamoDBScanExpression.getFilterExpression().contains(expectedExpressionClause5));
        assertEquals(expectedValueMap, dynamoDBScanExpression.getExpressionAttributeValues());
        assertEquals(expectedCountOfSubstring,
                getCountOfSubstring(dynamoDBScanExpression.getFilterExpression(), substring));
    }

    /**
     * Private helper method that determines the number of instances of a
     * substring within a reference string.
     * @param ref the string to search in
     * @param sub the substring to count
     * @return int the number of instance of the sub
     */
    private int getCountOfSubstring(String ref, String sub) {
        int count = 0;
        while (ref.contains(sub)) {
            count++;
            ref = ref.substring(ref.indexOf(sub) + sub.length());
        }
        return count;
    }
}
