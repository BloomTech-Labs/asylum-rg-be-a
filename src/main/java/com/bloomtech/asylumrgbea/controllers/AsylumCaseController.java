package com.bloomtech.asylumrgbea.controllers;

import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
     * to isolate this endpoint from the application.
     */
    @Deprecated
    public PageResponseDto getPageOfCases(@RequestBody AsylumCaseRequestDto asylumCaseRequestDto) {

        return asylumCaseService.getPageOfAsylumCases(asylumCaseRequestDto);
    }

    /**
     * As the point of entry this method is mapped to the show-all end point and provides all ResponseDtos.
     * @return An Iterable of AsylumCaseResponseDto or an ErrorDto with a message.
     */
    @GetMapping
    public Iterable<AsylumCaseResponseDto> getAllOfCases() {

        return asylumCaseService.getAllAsylumCases();
    }

}
