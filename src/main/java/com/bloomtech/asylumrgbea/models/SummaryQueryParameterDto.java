package com.bloomtech.asylumrgbea.models;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * Defines the DTO to store the query parameters provided by the client
 * for the cases' endpoint. Each attribute represents a query parameter
 * or category to filter cases by.
 */
@Data
public class SummaryQueryParameterDto {
    private boolean percentFlag;
    @Nullable
    private String from;
    @Nullable
    private String to;
    @Nullable
    private String office;

    @Override
    public String toString() {
        return "SummaryQueryParameterDto{" +
                ", percentFlag=" + percentFlag +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", office='" + office + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SummaryQueryParameterDto that = (SummaryQueryParameterDto) o;
        return percentFlag == that.percentFlag && Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(office, that.office);
    }

    @Override
    public int hashCode() {
        return Objects.hash(percentFlag, from, to, office);
    }
}
