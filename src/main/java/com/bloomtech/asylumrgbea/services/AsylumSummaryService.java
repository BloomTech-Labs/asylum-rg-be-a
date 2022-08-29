package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.controllers.exceptions.BadRequestException;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.models.*;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AsylumSummaryService {

    private final AsylumCaseRepository asylumCaseRepository;

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");


    @Cacheable("summarydto")
    public AsylumSummaryDto getSummaryBy(SummaryQueryParameterDto queryParameters) {
        validateInput(queryParameters);
        AsylumSummaryDto dto = new AsylumSummaryDto(0,0,0,0,
                new ArrayList<>(), new ArrayList<>());
        int fromYear = Integer.parseInt(Objects.requireNonNull(queryParameters.getFrom()).substring(0, 4));
        int toYear = Integer.parseInt(Objects.requireNonNull(queryParameters.getTo()).substring(0, 4));
        int yearSpan = (toYear - fromYear);
        Map<String, String[]> rangeMap = Map.of(
                "completionDate", new String[] {
                        queryParameters.getFrom(),
                        queryParameters.getTo()
                }
        );

        Iterable<AsylumCase> casesIterable;
        if (queryParameters.getOffice() == null || queryParameters.getOffice().isEmpty()) {
            casesIterable = asylumCaseRepository.find(new HashMap<>(), rangeMap).getResults();
        } else {
            List<String> filter = new ArrayList<>();
            filter.add(queryParameters.getOffice());
            Map<String, List<String>> filterMap = Map.of("asylumOffice", filter);
            casesIterable = asylumCaseRepository.find(filterMap, rangeMap).getResults();
        }

        List<AsylumCase> casesList = new ArrayList<>();
        for(AsylumCase aCase : casesIterable) {
            casesList.add(aCase);
        }

        setYears(casesList, dto, yearSpan, fromYear);
        setNumByCitizenship(casesList, dto);
        dto.setGranted(toPercent(dto.getGranted(), dto.getTotalCases()));
        dto.setDenied(toPercent(dto.getDenied(), dto.getTotalCases()));
        dto.setAdminClosed(toPercent(dto.getAdminClosed(), dto.getTotalCases()));
        return dto;
    }

    private void setYears(Iterable<AsylumCase> casesIterable, AsylumSummaryDto dto,
                          int yearSpan, int fromYear) {
        Map<String, HashMap<String, AsylumSummaryModel>> countByOffice = new HashMap<>();
        Map<String, AsylumYearSummaryModel> countByYear = new HashMap<>();
        for (int i = 0; i <= yearSpan; i++) {
            countByOffice.put(fromYear + i + "", new HashMap<>());
            countByYear.put(fromYear + i + "", new AsylumYearSummaryModel(fromYear + i + ""));
        }
        casesIterable.forEach(asylumCase -> {
            String currentYear = asylumCase.getCompletionDate().substring(0, 4);
            if(countByOffice.containsKey(currentYear)) {
                String office = asylumCase.getAsylumOffice();

                HashMap<String, AsylumSummaryModel> inner = countByOffice.get(currentYear);
                if (!inner.containsKey(office)) {
                    AsylumSummaryModel results = new AsylumSummaryModel(office, 0.0, 0.0, 0.0, 0);
                    inner.putIfAbsent(office, results);
                }

                AsylumSummaryModel officeResults = inner.get(office);
                AsylumYearSummaryModel yearResults = countByYear.get(currentYear);
                switch (asylumCase.getCaseOutcome()) {
                    case "Grant" -> {
                        officeResults.setGranted(officeResults.getGranted() + 1);
                        yearResults.setGranted(yearResults.getGranted() + 1);
                        dto.setGranted(dto.getGranted() + 1);
                    }
                    case "Deny/Referral" -> {
                        officeResults.setDenied(officeResults.getDenied() + 1);
                        yearResults.setDenied(yearResults.getDenied() + 1);
                        dto.setDenied(dto.getDenied() + 1);
                    }
                    default -> {
                        officeResults.setAdminClosed(officeResults.getAdminClosed() + 1);
                        yearResults.setAdminClosed(yearResults.getAdminClosed() + 1);
                        dto.setAdminClosed(dto.getAdminClosed() + 1);
                    }
                }
                officeResults.setTotalCases(officeResults.getTotalCases() + 1);
                yearResults.setTotalCases(yearResults.getTotalCases() + 1);
                dto.setTotalCases(dto.getTotalCases() + 1);
                inner.put(office, officeResults);
                countByYear.put(currentYear, yearResults);
                countByOffice.put(currentYear, inner);
            }
        });

        List<AsylumYearSummaryModel> yearSummary = new ArrayList<>();
        // not a O(n^2) loop
        // outer loop iterates over years
        // inner loop iterates over year data aggregated from first loop
        countByOffice.forEach((key, value) -> {
            List<AsylumSummaryModel> temp = new ArrayList<>();
            value.forEach((key1, current) -> {
                if (current.getTotalCases() != 0) {
                    int total = current.getTotalCases();
                    current.setGranted(toPercent(current.getGranted(), total));
                    current.setDenied(toPercent(current.getDenied(), total));
                    current.setAdminClosed(toPercent(current.getAdminClosed(), total));
                    temp.add(current);
                }
            });
            AsylumYearSummaryModel current = countByYear.get(key);
            if (current.getTotalCases() != 0) {
                int total = current.getTotalCases();
                current.setGranted(toPercent(current.getGranted(), total));
                current.setDenied(toPercent(current.getDenied(), total));
                current.setAdminClosed(toPercent(current.getAdminClosed(), total));
                current.setYearData(temp);
                yearSummary.add(current);
            }
        });
        yearSummary.sort(Comparator.comparing(AsylumYearSummaryModel::getYear));
        dto.setYearResults(yearSummary);
    }

    private void setNumByCitizenship(Iterable<AsylumCase> casesIterable, AsylumSummaryDto dto) {
        Map<String, AsylumCitizenshipSummaryModel> countByCitizenship = new HashMap<>();

        casesIterable.forEach(asylumCase -> {
            String citizenship = asylumCase.getCitizenship();
            if (countByCitizenship.containsKey(citizenship)) {
                AsylumCitizenshipSummaryModel citizenshipResults = countByCitizenship.get(citizenship);
                switch (asylumCase.getCaseOutcome()) {
                    case "Grant" -> citizenshipResults.setGranted(citizenshipResults.getGranted() + 1);
                    case "Deny/Referral" -> citizenshipResults.setDenied(citizenshipResults.getDenied() + 1);
                    default -> citizenshipResults.setAdminClosed(citizenshipResults.getAdminClosed() + 1);
                }
                citizenshipResults.setTotalCases(citizenshipResults.getTotalCases() + 1);
            } else {
                switch (asylumCase.getCaseOutcome()) {
                    case "Grant" -> countByCitizenship.put(citizenship, new AsylumCitizenshipSummaryModel(citizenship,
                            1.0, 0.0, 0.0, 1));
                    case "Deny/Referral" -> countByCitizenship.put(citizenship, new AsylumCitizenshipSummaryModel(citizenship,
                            0.0, 0.0, 1.0, 1));
                    default -> countByCitizenship.put(citizenship, new AsylumCitizenshipSummaryModel(citizenship,
                            0.0, 1.0, 0.0, 1));
                }
            }
        });
        List<AsylumCitizenshipSummaryModel> citizenshipSummary = new ArrayList<>();
        countByCitizenship.forEach((key, value) -> {
            if (value.getTotalCases() != 0) {
                int total = value.getTotalCases();
                value.setGranted(toPercent(value.getGranted(), total));
                value.setDenied(toPercent(value.getDenied(), total));
                value.setAdminClosed(toPercent(value.getAdminClosed(), total));
                citizenshipSummary.add(value);
            }
        });
        dto.setCitizenshipResults(citizenshipSummary);
    }

    private void validateInput(SummaryQueryParameterDto queryParameters) {
        if (queryParameters.getFrom() == null || queryParameters.getFrom().isEmpty()) {
            throw new BadRequestException("ERROR: from date is required input...");
        }
        if (queryParameters.getTo() == null || queryParameters.getTo().isEmpty()) {
            throw new BadRequestException("ERROR: to date is required input...");
        }
        if (queryParameters.getFrom().compareTo(queryParameters.getTo()) > 0) {
            throw new BadRequestException("ERROR: to date must be greater than or equal to from date...");
        }
    }

    private double toPercent(double amount, int totalCases) {
        return Math.round((amount / (double) totalCases * 100) * 100) / 100.0;
    }
}
