package com.bloomtech.asylumrgbea.controllers;

import com.bloomtech.asylumrgbea.services.AsylumCaseService;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;

class AsylumCaseControllerTest {

    private AsylumCaseService asylumCaseService;

    private AsylumCaseController asylumCaseController;

    @BeforeEach
    void setup() {
        asylumCaseService = mock(AsylumCaseService.class);
        asylumCaseController = new AsylumCaseController(asylumCaseService);
    }

    void getCases_withNoQueryParameters_returnsAllCases() {
    }

    void getCases_withSingleQueryParameter_returnsCasesMatchingSingleQueryParameter() {
    }

    void getCases_withMultipleQueryParameter_returnsCasesMatchingMultipleQueryParameters() {
    }

    void getCases_withSingleQueryParameterNoMatches_returnsErrorDto() {
    }

    void getCases_withMultipleQueryParametersNoMatches_returnsErrorDto() {
    }

    void getCases_withMultipleQueryParametersMatchAndNoMatch_returnsErrorDto() {
    }
}
