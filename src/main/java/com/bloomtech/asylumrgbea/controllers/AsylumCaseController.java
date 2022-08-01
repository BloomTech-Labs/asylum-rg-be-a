package com.bloomtech.asylumrgbea.controllers;

import com.bloomtech.asylumrgbea.models.CasesRequestDto;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;

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
@RequestMapping ("cases")
@RequiredArgsConstructor
public class AsylumCaseController {
    private final AsylumCaseService asylumCaseService;

    /**
     * The point of entry for a GET request for the endpoint cases. Accepts query parameters defined
     * by the CasesRequestDto which characterizes the query criteria. All other query parameter not
     * explicitly defined in the CasesRequestDto from the client are ignored.
     *
     * @param casesRequestDto characterizes the endpoint response.
     * @return An Iterable of AsylumCaseResponse or an ErrorDto.
     */
    @GetMapping
    public PageResponseDto getCases(@Valid CasesRequestDto casesRequestDto) {
        return asylumCaseService.getCasesBy(casesRequestDto);
    }
}
