package com.bloomtech.asylumrgbea.services;

import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.bloomtech.asylumrgbea.controllers.exceptions.AsylumCaseNotFoundException;
import com.bloomtech.asylumrgbea.controllers.exceptions.BadRequestException;
import com.bloomtech.asylumrgbea.controllers.exceptions.PageNotFoundException;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.mappers.AsylumCaseMapper;
import com.bloomtech.asylumrgbea.models.CasesQueryParameterDto;
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
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AsylumCaseServiceTest {
    @Mock
    private AsylumCaseMapper asylumCaseMapper;
    @Mock
    private AsylumCaseRepository asylumCaseRepository;
    private AsylumCaseService asylumCaseService;

    @BeforeEach
    void setup() {
        openMocks(this);
        asylumCaseService = new AsylumCaseService(asylumCaseRepository, asylumCaseMapper);
    }

    @AfterEach
    void teardown() {
        asylumCaseService = null;
    }

    @Test
    void getCasesBy_noQueryParametersProvided_buildsFilterAndRangeMap() {
        // GIVEN
        CasesQueryParameterDto requestDto = new CasesQueryParameterDto();

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<Map<String, List<String>>> filterCaptor  = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<String, String[]>> rangeCaptor       = ArgumentCaptor.forClass(Map.class);

        when(asylumCaseRepository.find(anyMap(), anyMap())).thenReturn(scanResultPage);

        // WHEN
        asylumCaseService.getCasesBy(requestDto);

        // THEN
        verify(asylumCaseRepository).find(filterCaptor.capture(), rangeCaptor.capture());
        assertTrue(filterCaptor.getValue().get("citizenship").isEmpty());
        assertTrue(filterCaptor.getValue().get("caseOutcome").isEmpty());
        assertTrue(filterCaptor.getValue().get("asylumOffice").isEmpty());

        assertNull(rangeCaptor.getValue().get("completionDate")[0]);
        assertNull(rangeCaptor.getValue().get("completionDate")[1]);
    }

    @Test
    void getCasesBy_oneCategorySingleValueQueryParameterProvided_buildsFilterAndRangeMap() {
        // GIVEN
        CasesQueryParameterDto requestDto = new CasesQueryParameterDto();
        requestDto.setCitizenship("citizenship");

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<Map<String, List<String>>> filterCaptor  = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<String, String[]>> rangeCaptor       = ArgumentCaptor.forClass(Map.class);

        when(asylumCaseRepository.find(anyMap(), anyMap())).thenReturn(scanResultPage);

        // WHEN
        asylumCaseService.getCasesBy(requestDto);

        // THEN
        verify(asylumCaseRepository).find(filterCaptor.capture(), rangeCaptor.capture());
        assertEquals("citizenship", filterCaptor.getValue().get("citizenship").get(0));
        assertTrue(filterCaptor.getValue().get("caseOutcome").isEmpty());
        assertTrue(filterCaptor.getValue().get("asylumOffice").isEmpty());

        assertNull(rangeCaptor.getValue().get("completionDate")[0]);
        assertNull(rangeCaptor.getValue().get("completionDate")[1]);
    }

    @Test
    void getCasesBy_oneCategoryTwoValuesQueryParameterProvided_buildsFilterAndRangeMap() {
        // GIVEN
        CasesQueryParameterDto requestDto = new CasesQueryParameterDto();
        requestDto.setCitizenship("citizenship10citizenship2");

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<Map<String, List<String>>> filterCaptor  = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<String, String[]>> rangeCaptor       = ArgumentCaptor.forClass(Map.class);

        when(asylumCaseRepository.find(anyMap(), anyMap())).thenReturn(scanResultPage);

        // WHEN
        asylumCaseService.getCasesBy(requestDto);

        // THEN
        verify(asylumCaseRepository).find(filterCaptor.capture(), rangeCaptor.capture());
        assertEquals("citizenship1", filterCaptor.getValue().get("citizenship").get(0));
        assertEquals("citizenship2", filterCaptor.getValue().get("citizenship").get(1));
        assertTrue(filterCaptor.getValue().get("caseOutcome").isEmpty());
        assertTrue(filterCaptor.getValue().get("asylumOffice").isEmpty());

        assertNull(rangeCaptor.getValue().get("completionDate")[0]);
        assertNull(rangeCaptor.getValue().get("completionDate")[1]);
    }

    @Test
    void getCasesBy_multipleCategorySingleValueQueryParametersProvided_buildsFilterAndRangeMap() {
        // GIVEN
        CasesQueryParameterDto requestDto = new CasesQueryParameterDto();
        requestDto.setCitizenship("citizenship");
        requestDto.setOutcome("outcome");
        requestDto.setFrom("from");

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<Map<String, List<String>>> filterCaptor  = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<String, String[]>> rangeCaptor       = ArgumentCaptor.forClass(Map.class);

        when(asylumCaseRepository.find(anyMap(), anyMap())).thenReturn(scanResultPage);

        // WHEN
        asylumCaseService.getCasesBy(requestDto);

        // THEN
        verify(asylumCaseRepository).find(filterCaptor.capture(), rangeCaptor.capture());
        assertEquals("citizenship",  filterCaptor.getValue().get("citizenship").get(0));
        assertEquals("outcome",   filterCaptor.getValue().get("caseOutcome").get(0));
        assertTrue(filterCaptor.getValue().get("asylumOffice").isEmpty());

        assertEquals("from", rangeCaptor.getValue().get("completionDate")[0]);
        assertNull(rangeCaptor.getValue().get("completionDate")[1]);
    }

    @Test
    void getCasesBy_multipleCategoryMultipleValuesQueryParametersProvided_buildsFilterAndRangeMap() {
        // GIVEN
        CasesQueryParameterDto requestDto = new CasesQueryParameterDto();
        requestDto.setCitizenship("citizenship10citizenship20citizenship30citizenship4");
        requestDto.setOutcome("outcome1,outcome2,outcome3");
        requestDto.setOffice("office1,office2");
        requestDto.setFrom("from");
        requestDto.setTo("to");

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase()));

        ArgumentCaptor<Map<String, List<String>>> filterCaptor  = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<String, String[]>> rangeCaptor       = ArgumentCaptor.forClass(Map.class);

        when(asylumCaseRepository.find(anyMap(), anyMap())).thenReturn(scanResultPage);

        // WHEN
        asylumCaseService.getCasesBy(requestDto);

        // THEN
        verify(asylumCaseRepository).find(filterCaptor.capture(), rangeCaptor.capture());
        assertEquals("citizenship1",  filterCaptor.getValue().get("citizenship").get(0));
        assertEquals("citizenship2",  filterCaptor.getValue().get("citizenship").get(1));
        assertEquals("citizenship3",  filterCaptor.getValue().get("citizenship").get(2));
        assertEquals("citizenship4",  filterCaptor.getValue().get("citizenship").get(3));
        assertEquals("outcome1",   filterCaptor.getValue().get("caseOutcome").get(0));
        assertEquals("outcome2",   filterCaptor.getValue().get("caseOutcome").get(1));
        assertEquals("outcome3",   filterCaptor.getValue().get("caseOutcome").get(2));
        assertEquals("office1",   filterCaptor.getValue().get("asylumOffice").get(0));
        assertEquals("office2",   filterCaptor.getValue().get("asylumOffice").get(1));

        assertEquals("from",    rangeCaptor.getValue().get("completionDate")[0]);
        assertEquals("to",      rangeCaptor.getValue().get("completionDate")[1]);
    }

    @Test
    void getCasesBy_queryParametersThatLeadsToEmptyResult_throwsAsylumCasesNotFoundException() {
        // GIVEN
        CasesQueryParameterDto requestDto = new CasesQueryParameterDto();

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(Collections.EMPTY_LIST);

        when(asylumCaseRepository.find(anyMap(), anyMap())).thenReturn(scanResultPage);

        // WHEN - THEN
        assertThrows(AsylumCaseNotFoundException.class, () -> asylumCaseService.getCasesBy(requestDto));
        verify(asylumCaseRepository).find(anyMap(), anyMap());
    }

    @Test
    void getCasesBy_queryParametersWithPageValueExceeds_throwsPageNotFoundException() {
        // GIVEN
        CasesQueryParameterDto requestDto = new CasesQueryParameterDto();
        requestDto.setLimit(5);
        requestDto.setPage(2);

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(List.of(new AsylumCase(), new AsylumCase(), new AsylumCase()));

        when(asylumCaseRepository.find(anyMap(), anyMap())).thenReturn(scanResultPage);

        // WHEN - THEN
        assertThrows(PageNotFoundException.class, () -> asylumCaseService.getCasesBy(requestDto));
        verify(asylumCaseRepository).find(anyMap(), anyMap());
    }

    @Test
    void getCasesBy_queryParametersLimitValueLessThan1_throwsBadRequestException() {
        // GIVEN
        CasesQueryParameterDto requestDto = new CasesQueryParameterDto();
        requestDto.setLimit(0);

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(Collections.EMPTY_LIST);

        when(asylumCaseRepository.find(anyMap(), anyMap())).thenReturn(scanResultPage);

        // WHEN - THEN
        assertThrows(BadRequestException.class, () -> asylumCaseService.getCasesBy(requestDto));
        verifyNoInteractions(asylumCaseRepository);
    }

    @Test
    void getCasesBy_queryParametersPageValueLessThan1_throwsBadRequestException() {
        // GIVEN
        CasesQueryParameterDto requestDto = new CasesQueryParameterDto();
        requestDto.setPage(0);

        ScanResultPage<AsylumCase> scanResultPage = new ScanResultPage<>();
        scanResultPage.setResults(Collections.EMPTY_LIST);

        when(asylumCaseRepository.find(anyMap(), anyMap())).thenReturn(scanResultPage);

        // WHEN - THEN
        assertThrows(BadRequestException.class, () -> asylumCaseService.getCasesBy(requestDto));
        verifyNoInteractions(asylumCaseRepository);
    }

    @Test
    void getAllAsylumCases_givenNoAsylumCase_throwsAsylumCaseNotFoundException() {
        // GIVEN
        // WHEN
        when(asylumCaseRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        // THEN
        assertThrows(AsylumCaseNotFoundException.class,
                () -> asylumCaseService.getAllAsylumCases(),
                "ERROR: Expected an AsylumCaseNotFoundException but was false..");
        verify(asylumCaseRepository).findAll();
    }
}
