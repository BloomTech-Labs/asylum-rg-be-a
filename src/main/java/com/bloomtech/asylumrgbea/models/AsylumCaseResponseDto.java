package com.bloomtech.asylumrgbea.models;

import lombok.Data;

@Data
public class AsylumCaseResponseDto {

    private final String asylumOffice;

    private final String citizenship;

    private final String raceOrEthnicity;

    private final String caseOutcome;

    private final String completion;

    private final String currentDate;
}
