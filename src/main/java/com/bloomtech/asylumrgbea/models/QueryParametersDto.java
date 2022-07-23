package com.bloomtech.asylumrgbea.models;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * Defines the DTO for the query parameters provided by the client.
 * Each attribute represents a query category to filter AsylumCases
 * by.
 */
@Data
public class QueryParametersDto {
    @Nullable
    private String citizenship;
    @Nullable
    private String caseOutcome;
    @Nullable
    private String completion;
    @Nullable
    private String currentDate;
    @Nullable
    private String asylumOffice;

    @Override
    public String toString() {
        return "QueryParametersDto{" +
                "citizenship='" + citizenship + '\'' +
                ", caseOutcome='" + caseOutcome + '\'' +
                ", completion='" + completion + '\'' +
                ", currentDate='" + currentDate + '\'' +
                ", asylumOffice='" + asylumOffice + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QueryParametersDto)) {
            return false;
        }
        QueryParametersDto that = (QueryParametersDto) o;
        return Objects.equals(citizenship, that.citizenship) &&
                Objects.equals(caseOutcome, that.caseOutcome) &&
                Objects.equals(completion, that.completion) &&
                Objects.equals(currentDate, that.currentDate) &&
                Objects.equals(asylumOffice, that.asylumOffice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                citizenship,
                caseOutcome,
                completion,
                currentDate,
                asylumOffice);
    }
}
