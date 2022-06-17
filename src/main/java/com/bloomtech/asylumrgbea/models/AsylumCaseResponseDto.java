package com.bloomtech.asylumrgbea.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsylumCaseResponseDto {
    private String asylumOffice;
    private String citizenship;
    private String raceOrEthnicity;
    private String caseOutcome;
    private String completion;
    private String currentDate;
}
