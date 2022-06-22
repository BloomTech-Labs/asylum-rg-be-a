package com.bloomtech.asylumrgbea.controllers;

import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

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
     * As the point of entry this method is mapped to the cases end point and provides all ResponseDtos.
     * @return An Iterable of AsylumCaseResponseDto or an ErrorDto with a message.
     */
    @GetMapping
    public Iterable<AsylumCaseResponseDto> getAllCases() {
        return asylumCaseService.getAllAsylumCases();
    }

    @GetMapping("/{page}")
    public Iterable<AsylumCaseResponseDto> getAllCases(@RequestParam("page")Integer page, AsylumCaseRequestDto asylumCaseRequestDto) { return asylumCaseService.getAllAsylumCases(asylumCaseRequestDto); }
}
