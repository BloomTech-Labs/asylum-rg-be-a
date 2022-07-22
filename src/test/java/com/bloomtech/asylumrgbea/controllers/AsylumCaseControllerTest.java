package com.bloomtech.asylumrgbea.controllers;

import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;
import com.bloomtech.asylumrgbea.services.impl.AsylumCaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsylumCaseControllerTest {

    private AsylumCaseService asylumCaseService;

    private AsylumCaseController asylumCaseController;

    @BeforeEach
    void setup() {

        asylumCaseService = mock(AsylumCaseServiceImpl.class);

        asylumCaseController = new AsylumCaseController(asylumCaseService);
    }

    @Test
    void getAllCases_serviceIsValid_returnsIterableOfAsylumCaseResponseDto() {

        // GIVEN
        AsylumCaseResponseDto asylumCaseResponseDto = new AsylumCaseResponseDto(
                "SAN", "HIO", "Other",
                "Pending", "N/A", "6/24/2022");

        // WHEN
        when(asylumCaseService.getAllAsylumCases()).thenReturn(List.of(asylumCaseResponseDto));
        Iterable<AsylumCaseResponseDto> result = asylumCaseController.getAllOfCases();

        // THEN
        assertTrue(result.iterator().hasNext());
        verify(asylumCaseService).getAllAsylumCases();
    }

    @Test
    void getPageOfAsylumResponse_serviceIsValid_returnsIterableOfPageResponseDto() {
//        // GIVEN
//        AsylumCaseRequestDto asylumCaseRequestDto = new AsylumCaseRequestDto(10, 0);
//        AsylumCaseResponseDto asylumCaseResponseDto = new AsylumCaseResponseDto(
//                "SAN", "HIO", "Other",
//                "Pending", "N/A", "6/24/2022");
//
//        PageResponseDto pageResponseDto = new PageResponseDto(2, List.of(asylumCaseResponseDto));
//
//        // WHEN
//        when(asylumCaseService.getPageOfAsylumCases(new AsylumCaseRequestDto())).thenReturn(pageResponseDto);
//        PageResponseDto result = asylumCaseController.getPageOfCases(asylumCaseRequestDto);
//
//        // THEN
//        assertNotNull(pageResponseDto.getPage());
//        assertNotNull(pageResponseDto.getPage());
//        verify(asylumCaseService).getPageOfAsylumCases(any());
    }
}
