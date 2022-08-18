package com.bloomtech.asylumrgbea.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Defines a POJO for a page object that represents the client side page.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsylumSummaryDto {
    private List<AsylumYearSummaryModel> results;
}
