package com.bloomtech.asylumrgbea.models;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class AsylumYearSummaryModel {
    private final String year;

    private double granted;

    private double adminClosed;

    private double denied;

    private int totalCases;

    private List<AsylumSummaryModel> yearData;
}
