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
