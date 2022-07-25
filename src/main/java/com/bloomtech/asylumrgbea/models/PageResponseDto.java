package com.bloomtech.asylumrgbea.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto {

    private int totalPages;

    private Iterable<CaseResponseDto> page;
}
