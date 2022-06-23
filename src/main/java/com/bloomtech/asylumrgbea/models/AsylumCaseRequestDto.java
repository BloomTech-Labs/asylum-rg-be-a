package com.bloomtech.asylumrgbea.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AsylumCaseRequestDto {
    private Integer numberOfItemsInPage;
    private int pageNumber;
}
