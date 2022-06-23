package com.bloomtech.asylumrgbea.models;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageResponseDto {
    private int totalPages;
    private Iterable<AsylumCaseResponseDto> page;
}
