package com.bloomtech.asylumrgbea.controllers;

import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping ("cases")
@RequiredArgsConstructor
public class AsylumCaseController {
    private final AsylumCaseService asylumCaseService;

    @GetMapping
    public Iterable<AsylumCaseResponseDto> getAllCases() {
        return asylumCaseService.getAllAsylumCases();
    }
}
