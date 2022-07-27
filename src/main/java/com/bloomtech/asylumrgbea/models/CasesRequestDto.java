package com.bloomtech.asylumrgbea.models;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.enterprise.inject.Default;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * Defines the DTO for the query parameters provided by the client for the cases' endpoint.
 * Each attribute represents a query category to filter AsylumCases by.
 */
@Data
public class CasesRequestDto {
    // TODO: Client needs to provide. Enforce validation using annotations.
    @Nullable
    private Integer numberOfItemsInPage;
    // TODO: Client needs to provide. Enforce validation using annotations.
    private int pageNumber;
    @Nullable
    private String citizenship;
    @Nullable
    private String caseOutcome;
    @Nullable
    private String completionTo;
    @Nullable
    private String completionFrom;
    @Nullable
    private String currentDate;
    @Nullable
    private Boolean isFiscalYear;
    @Nullable
    private String asylumOffice;

    @Override
    public String toString() {
        return "CasesRequestDto{" +
                "citizenship='" + citizenship + '\'' +
                ", caseOutcome='" + caseOutcome + '\'' +
                ", completionTo='" + completionTo + '\'' +
                ", completionFrom='" + completionFrom + '\'' +
                ", currentDate='" + currentDate + '\'' +
                ", isFiscalYear='" + isFiscalYear + '\'' +
                ", asylumOffice='" + asylumOffice + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CasesRequestDto)) {
            return false;
        }
        CasesRequestDto that = (CasesRequestDto) o;
        return Objects.equals(citizenship, that.citizenship) &&
                Objects.equals(caseOutcome, that.caseOutcome) &&
                Objects.equals(completionTo, that.completionTo) &&
                Objects.equals(completionFrom, that.completionFrom) &&
                Objects.equals(currentDate, that.currentDate) &&
                Objects.equals(isFiscalYear, that.isFiscalYear) &&
                Objects.equals(asylumOffice, that.asylumOffice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                citizenship,
                caseOutcome,
                completionTo,
                completionFrom,
                currentDate,
                isFiscalYear,
                asylumOffice);
    }
}
