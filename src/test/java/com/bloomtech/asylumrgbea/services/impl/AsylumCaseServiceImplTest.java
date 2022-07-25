package com.bloomtech.asylumrgbea.services.impl;

import com.bloomtech.asylumrgbea.controllers.exceptions.AsylumCaseNotFoundException;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.mappers.AsylumCaseMapper;
import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsylumCaseServiceImplTest {
    private AsylumCaseMapper asylumCaseMapper;
    private AsylumCaseRepository asylumCaseRepository;
    private AsylumCaseServiceImpl asylumCaseService;

    @BeforeEach
    void setup() {
        asylumCaseMapper = mock(AsylumCaseMapper.class);
        asylumCaseRepository = mock(AsylumCaseRepository.class);
        asylumCaseService = new AsylumCaseServiceImpl(asylumCaseRepository, asylumCaseMapper);
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
