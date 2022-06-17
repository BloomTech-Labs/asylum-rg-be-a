package com.bloomtech.asylumrgbea.controllers;

import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
Create the controller for the presentation layer.
The controller should be named AsylumCaseController and be annotated with the @RestController and @RequestMapping(“anEndPoint”).
The endpoints are defined in the API contract documentation.
The controller depends on the AsylumCaseService and has two methods.
The first method is called loadCases() and returns all cases as a response entity.
The second method will find one case by an id packaged in a AsylumCaseRequestDto.
Please use the @GetMapping annotation for both and review the class diagram in the branch below.
 */

@RestController
@RequestMapping ("cases")
@AllArgsConstructor
public class AsylumCaseController {
    private AsylumCaseService asylumCaseService;
    @GetMapping
    public ResponseEntity<Iterable<AsylumCaseResponseDto>> getAllCases() {
        return asylumCaseService.getAllAsylumCases();
    }
}
