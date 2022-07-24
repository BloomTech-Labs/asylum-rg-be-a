package com.bloomtech.asylumrgbea.controllers;

import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import com.bloomtech.asylumrgbea.models.CasesRequestDto;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * As the point of entry this method is mapped to the default end point and provides all ResponseDtos.
     * @return An Iterable of AsylumCaseResponseDto with totalPage or an ErrorDto with a message.
     *
     * This end-point has been deprecated at this point in development. Removed @GetMapping annotation
     * to obscure this endpoint from public access.
     */
    @Deprecated
    public PageResponseDto getPageOfCases(@RequestBody AsylumCaseRequestDto asylumCaseRequestDto) {

        return asylumCaseService.getPageOfAsylumCases(asylumCaseRequestDto);
    }

    /**
     * As the point of entry this method is mapped to the show-all end point and provides all ResponseDtos.
     * @return An Iterable of AsylumCaseResponseDto or an ErrorDto with a message.
     *
     * This end-point has been deprecated at this point in development. Removed @GetMapping annotation
     * to obscure this endpoint from public access.
     */
    @Deprecated
    public Iterable<AsylumCaseResponseDto> getAllOfCases() {

        return asylumCaseService.getAllAsylumCases();
    }

    /**
     * The point of entry for a GET request for the endpoint cases. Accepts query parameters defined
     * by the CasesRequestDto which characterizes the query criteria. All other query parameter not
     * explicitly defined in the CasesRequestDto from the client are ignored.
     *
     * @param casesRequestDto characterizes the endpoint response.
     * @return An Iterable of AsylumCaseResponse or an ErrorDto.
     */
    @GetMapping
    public Object getCases(@Valid CasesRequestDto casesRequestDto) {

        // TODO: Currently just returns the String of the CasesRequestDto for testing.
        //  Needs to reach the AsylumCaseServiceImpl to generate a proper response.
        //  Return type also needs to be more restrictive.
        return casesRequestDto.toString();
    }

}
