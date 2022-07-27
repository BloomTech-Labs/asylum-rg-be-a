package com.bloomtech.asylumrgbea.services.impl;

import com.bloomtech.asylumrgbea.controllers.exceptions.AsylumCaseNotFoundException;
import com.bloomtech.asylumrgbea.mappers.AsylumCaseMapper;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsylumCaseServiceImplTest {
    private AsylumCaseMapper asylumCaseMapper;
    private AsylumCaseRepository asylumCaseRepository;
    private AsylumCaseService asylumCaseService;

    @BeforeEach
    void setup() {
        asylumCaseMapper = mock(AsylumCaseMapper.class);
        asylumCaseRepository = mock(AsylumCaseRepository.class);
        asylumCaseService = new AsylumCaseService(asylumCaseRepository, asylumCaseMapper);
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

   @Test
    void getCasesBy_givenNoFilter_returnsAllCases() {
        // TODO: AsylumCaseServiceImpl has not been scoped-redesigned.
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void getCasesBy_givenOneCitizenship_returnsCasesByCitizenship() {
        // TODO: AsylumCaseServiceImpl has not been scoped-redesigned.
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void getCasesBy_givenMultipleCitizenship_returnsAllCases() {
        // TODO: AsylumCaseServiceImpl has not been scoped-redesigned.
        //GIVEN

        //WHEN

        //THEN
    }

}
