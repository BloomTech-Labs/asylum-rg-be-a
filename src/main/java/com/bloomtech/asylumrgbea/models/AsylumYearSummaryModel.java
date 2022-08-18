package com.bloomtech.asylumrgbea.models;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class AsylumYearSummaryModel {
    private final String year;

    private final List<AsylumSummaryModel> yearData;

    private final double granted;

    private final double adminClosed;

    private final double denied;

    private final int totalCases;
}
