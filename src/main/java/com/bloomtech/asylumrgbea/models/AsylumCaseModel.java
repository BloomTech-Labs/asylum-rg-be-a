package com.bloomtech.asylumrgbea.models;

import lombok.Data;

/**
 * Defines a POJO for a case object that represents the client side case.
 */
@Data
public class AsylumCaseModel {

    private final String asylumOffice;

    private final String citizenship;

    private final String caseOutcome;

    private final String completionDate;

}

