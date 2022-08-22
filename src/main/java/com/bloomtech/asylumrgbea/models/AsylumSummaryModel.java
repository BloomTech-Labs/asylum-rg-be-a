package com.bloomtech.asylumrgbea.models;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class AsylumSummaryModel {
    private final String office;

    private final double granted;

    private final double adminClosed;

    private final double denied;

    private final int totalCases;
}
