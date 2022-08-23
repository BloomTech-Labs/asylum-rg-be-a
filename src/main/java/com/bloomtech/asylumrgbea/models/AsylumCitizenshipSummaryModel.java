package com.bloomtech.asylumrgbea.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AsylumCitizenshipSummaryModel {
    private String citizenship;

    private double granted;

    private double adminClosed;

    private double denied;

    private int totalCases;
}
