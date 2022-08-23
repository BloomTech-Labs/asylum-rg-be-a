package com.bloomtech.asylumrgbea.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AsylumSummaryModel {
    private final String office;

    private double granted;

    private double adminClosed;

    private double denied;

    private int totalCases;
}
