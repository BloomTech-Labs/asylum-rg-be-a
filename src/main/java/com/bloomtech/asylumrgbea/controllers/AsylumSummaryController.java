package com.bloomtech.asylumrgbea.controllers;

import com.bloomtech.asylumrgbea.models.AsylumSummaryDto;
import com.bloomtech.asylumrgbea.models.CasesQueryParameterDto;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import com.bloomtech.asylumrgbea.models.SummaryQueryParameterDto;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;
import com.bloomtech.asylumrgbea.services.AsylumSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * This class can be overridden by a ControllerAdvice class as a result of some exceptions being thrown.
 * @advice AsylumCaseControllerAdvice
 */
@RestController
@RequestMapping("summary")
@RequiredArgsConstructor
public class AsylumSummaryController {
    private final AsylumSummaryService asylumSummaryService;

    /**
     * The point of entry for a GET request for the endpoint cases. Accepts query parameters defined
     * by the CasesQueryParameterDto which characterizes the query criteria. All other query parameter not
     * explicitly defined in the CasesQueryParameterDto from the client are ignored.
     *
     * @param queryParameters characterizes the endpoint response.
     * @return An Iterable of AsylumCaseResponse or an ErrorDto.
     */
    @GetMapping
    public AsylumSummaryDto getCases(@Valid SummaryQueryParameterDto queryParameters) {
        return asylumSummaryService.getSummaryBy(queryParameters);
    }
}
