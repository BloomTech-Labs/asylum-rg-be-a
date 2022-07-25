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
        // TODO: AsylumCaseServiceImpl has not been scoped-redesigned.
    }

    void getCases_withSingleQueryParameter_returnsCasesMatchingSingleQueryParameter() {
        // TODO: AsylumCaseServiceImpl has not been scoped-redesigned.
    }

    void getCases_withMultipleQueryParameter_returnsCasesMatchingMultipleQueryParameters() {
        // TODO: AsylumCaseServiceImpl has not been scoped-redesigned.
    }

    void getCases_withSingleQueryParameterNoMatches_returnsErrorDto() {
        // TODO: AsylumCaseServiceImpl has not been scoped-redesigned.
    }

    void getCases_withMultipleQueryParametersNoMatches_returnsErrorDto() {
        // TODO: AsylumCaseServiceImpl has not been scoped-redesigned.
    }

    void getCases_withMultipleQueryParametersMatchAndNoMatch_returnsErrorDto() {
        // TODO: AsylumCaseServiceImpl has not been scoped-redesigned.
    }
}
