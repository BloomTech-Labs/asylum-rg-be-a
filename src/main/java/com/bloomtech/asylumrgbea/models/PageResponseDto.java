package com.bloomtech.asylumrgbea.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Defines a POJO for a page object that represents the client side page.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto {

    private int totalPages;

    private Iterable<AsylumCaseModel> page;
}
