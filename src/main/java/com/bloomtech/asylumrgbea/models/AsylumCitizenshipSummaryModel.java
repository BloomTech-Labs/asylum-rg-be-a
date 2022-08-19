package com.bloomtech.asylumrgbea.models;

import lombok.Data;

@Data
public class AsylumCitizenshipSummaryModel {
    private final String citizenship;

    private final double granted;

    private final double adminClosed;

    private final double denied;

    private final int totalCases;
}
