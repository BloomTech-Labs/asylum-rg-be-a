package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.controllers.exceptions.BadRequestException;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.models.*;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AsylumSummaryService {

    private final AsylumCaseRepository asylumCaseRepository;


    @Cacheable("summarydto")
    public AsylumSummaryDto getSummaryBy(SummaryQueryParameterDto queryParameters) {
        validateInput(queryParameters);
        boolean percentFlag;
        AsylumSummaryDto dto = new AsylumSummaryDto(0,0,0,0,
                new ArrayList<>(), new ArrayList<>());
        int fromYear = Integer.parseInt(queryParameters.getFrom().substring(0, 4));
        int toYear = Integer.parseInt(queryParameters.getTo().substring(0, 4));
        int yearSpan = (toYear - fromYear);
        percentFlag = queryParameters.isPercentFlag();
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

        dto.setYearResults(getYears(casesList, dto, yearSpan, fromYear, percentFlag));
        dto.setCitizenshipResults(getNumByCitizenship(casesList, percentFlag));
        if (percentFlag) {
            dto.setGranted(dto.getGranted() / (double) dto.getTotalCases() * 100);
            dto.setDenied(dto.getDenied() / (double) dto.getTotalCases() * 100);
            dto.setAdminClosed(dto.getAdminClosed() / (double) dto.getTotalCases() * 100);
        }
        return dto;
    }

    private List<AsylumYearSummaryModel> getYears(Iterable<AsylumCase> casesIterable,
                                                  AsylumSummaryDto dto, int yearSpan, int fromYear, boolean percentFlag) {
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

                AsylumSummaryModel citizenshipResults = inner.get(office);
                AsylumYearSummaryModel yearResults = countByYear.get(currentYear);
                if (asylumCase.getCaseOutcome().equals("Grant")) {
                    citizenshipResults.setGranted(citizenshipResults.getGranted() + 1);
                    yearResults.setGranted(yearResults.getGranted() + 1);
                    dto.setGranted(dto.getGranted() + 1);
                } else if (asylumCase.getCaseOutcome().equals("Deny/Referral")) {
                    citizenshipResults.setDenied(citizenshipResults.getDenied() + 1);
                    yearResults.setDenied(yearResults.getDenied() + 1);
                    dto.setDenied(dto.getDenied() + 1);
                } else {
                    citizenshipResults.setAdminClosed(citizenshipResults.getAdminClosed() + 1);
                    yearResults.setAdminClosed(yearResults.getAdminClosed() + 1);
                    dto.setAdminClosed(dto.getAdminClosed() + 1);
                }
                citizenshipResults.setTotalCases(citizenshipResults.getTotalCases() + 1);
                yearResults.setTotalCases(yearResults.getTotalCases() + 1);
                dto.setTotalCases(dto.getTotalCases() + 1);
                inner.put(office, citizenshipResults);
                countByYear.put(currentYear, yearResults);
                countByOffice.put(currentYear, inner);
            }
        });

        List<AsylumYearSummaryModel> yearSummary = new ArrayList<>();
        // not a O(n^2) loop
        // outer loop iterates over years
        // inner loop iterates over year data aggregated from first loop
        for (Map.Entry<String, HashMap<String,AsylumSummaryModel>> inner : countByOffice.entrySet()) {
            List<AsylumSummaryModel> temp = new ArrayList<>();
            for (Map.Entry<String, AsylumSummaryModel> entry : inner.getValue().entrySet()) {

                AsylumSummaryModel current = entry.getValue();
                if(current.getTotalCases() != 0) {
                    double total = current.getTotalCases();
                    if (percentFlag) {
                        current.setGranted(current.getGranted() / total * 100);
                        current.setDenied(current.getDenied() / total * 100);
                        current.setAdminClosed(current.getAdminClosed() / total * 100);
                    }

                    temp.add(current);
                }
            }
            AsylumYearSummaryModel current = countByYear.get(inner.getKey());
            if(current.getTotalCases() != 0) {
                double total = current.getTotalCases();
                if (percentFlag) {
                    current.setGranted(current.getGranted() / total * 100);
                    current.setDenied(current.getDenied() / total * 100);
                    current.setAdminClosed(current.getAdminClosed() / total * 100);
                }
                yearSummary.add(current);
            }
        }
        yearSummary.sort(Comparator.comparing(AsylumYearSummaryModel::getYear));
        dto.setYearResults(yearSummary);
        return yearSummary;
    }

    private List<AsylumCitizenshipSummaryModel> getNumByCitizenship(Iterable<AsylumCase> casesIterable,
                                                                    boolean percentFlag) {
        Map<String, AsylumCitizenshipSummaryModel> countByCitizenship = new HashMap<>();

        casesIterable.forEach(asylumCase -> {
            String citizenship = asylumCase.getCitizenship();
            if (countByCitizenship.containsKey(citizenship)) {
                AsylumCitizenshipSummaryModel citizenshipResults = countByCitizenship.get(citizenship);
                if (asylumCase.getCaseOutcome().equals("Grant")) {
                    citizenshipResults.setGranted(citizenshipResults.getGranted() + 1);
                } else if (asylumCase.getCaseOutcome().equals("Deny/Referral")) {
                    citizenshipResults.setDenied(citizenshipResults.getDenied() + 1);
                } else {
                    citizenshipResults.setAdminClosed(citizenshipResults.getAdminClosed() + 1);
                }
                citizenshipResults.setTotalCases(citizenshipResults.getTotalCases() + 1);
            } else {
                if (asylumCase.getCaseOutcome().equals("Grant")) {
                    countByCitizenship.put(citizenship, new AsylumCitizenshipSummaryModel(citizenship,
                            1.0, 0.0, 0.0, 1));
                } else if (asylumCase.getCaseOutcome().equals("Deny/Referral")) {
                    countByCitizenship.put(citizenship, new AsylumCitizenshipSummaryModel(citizenship,
                            0.0, 0.0, 1.0, 1));
                } else {
                    countByCitizenship.put(citizenship, new AsylumCitizenshipSummaryModel(citizenship,
                            0.0, 1.0, 0.0, 1));
                }
            }
        });
        List<AsylumCitizenshipSummaryModel> citizenshipSummary = new ArrayList<>();
        for (Map.Entry<String, AsylumCitizenshipSummaryModel> entry : countByCitizenship.entrySet()) {
            AsylumCitizenshipSummaryModel current = entry.getValue();
            if(current.getTotalCases() != 0) {
                double total = current.getTotalCases();
                if (percentFlag) {
                    current.setGranted(current.getGranted() / total * 100);
                    current.setDenied(current.getDenied() / total * 100);
                    current.setAdminClosed(current.getAdminClosed() / total * 100);
                }
                citizenshipSummary.add(current);
            }
        }
        return citizenshipSummary;
    }
    private void validateInput(SummaryQueryParameterDto queryParameters) {
        if (queryParameters.getFrom() == null || queryParameters.getFrom().isEmpty()) {
            throw new BadRequestException("ERROR: from date is required input...");
        }
        if (queryParameters.getTo() == null || queryParameters.getTo().isEmpty()) {
            throw new BadRequestException("ERROR: to date is required input...");
        }
    }
}
