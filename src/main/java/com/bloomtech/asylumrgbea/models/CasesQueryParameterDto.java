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
public class CasesQueryParameterDto {
    private int limit = 10;
    private int page = 1;
    @Nullable
    private String citizenship;
    @Nullable
    private String outcome;
    @Nullable
    private String from;
    @Nullable
    private String to;
    @Nullable
    private String office;

    @Override
    public String toString() {
        return "CasesQueryParameterDto{" +
                "limit=" + limit +
                ", page=" + page +
                ", citizenship='" + citizenship + '\'' +
                ", outcome='" + outcome + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", office='" + office + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CasesQueryParameterDto)) return false;
        CasesQueryParameterDto that = (CasesQueryParameterDto) o;
        return page == that.page &&
                limit == that.limit &&
                Objects.equals(citizenship, that.citizenship) &&
                Objects.equals(outcome, that.outcome) &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(office, that.office);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                limit,
                page,
                citizenship,
                outcome,
                from,
                to,
                office);
    }
}
