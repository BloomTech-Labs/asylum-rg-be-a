package com.bloomtech.asylumrgbea.models;

import lombok.Builder;
import lombok.Data;

@Data
public class CaseResponseDto {

    private final String asylumOffice;

    private final String citizenship;

    private final String caseOutcome;

    private final String completion;

    private final String currentDate;

}
