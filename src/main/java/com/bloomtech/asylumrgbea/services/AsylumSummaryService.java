package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.models.*;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AsylumSummaryService {

    private final AsylumCaseRepository asylumCaseRepository;

    private boolean citizenshipOrOfficeFlag;
    private boolean percentFlag;

    private AsylumSummaryDto dto = new AsylumSummaryDto(0,0,0,0,
            new ArrayList<>(), new ArrayList<>());

    public AsylumSummaryDto getSummaryBy(SummaryQueryParameterDto queryParameters) {
        int fromYear = Integer.parseInt(queryParameters.getFrom().substring(0, 4));
        int toYear = Integer.parseInt(queryParameters.getTo().substring(0, 4));
        int yearSpan = (toYear - fromYear);
        citizenshipOrOfficeFlag = queryParameters.isCitizenshipFlag();
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
        dto.setResults(getYears(casesList, yearSpan, fromYear));
        dto.setCitizenshipResults(getNumByCitizenship(casesList));
        if (percentFlag) {
            dto.setGranted(dto.getGranted() / (double) dto.getTotalCases() * 100);
            dto.setDenied(dto.getDenied() / (double) dto.getTotalCases() * 100);
            dto.setAdminClosed(dto.getAdminClosed() / (double) dto.getTotalCases() * 100);
        }
        return dto;
    }

    private List<AsylumYearSummaryModel> getYears(Iterable<AsylumCase> casesIterable, int yearSpan, int fromYear) {
        Map<String, HashMap<String, double[]>> countByCitizenship = new HashMap<>();
        Map<String, double[]> countByYear = new HashMap<>();

        // countByCitizenship.getKey(value)[0] = amountGranted
        // countByCitizenship.getKey(value)[1] = amountDenied
        // countByCitizenship.getKey(value)[2] = amountAdminClosed
        // countByCitizenship.getKey(value)[2] = totalCases
        for (int i = 0; i <= yearSpan; i++) {
            countByCitizenship.put(fromYear + i + "", new HashMap<>());
            countByYear.put(fromYear + i + "", new double[]{0,0,0,0});
        }
        casesIterable.forEach(asylumCase -> {
            String currentYear = asylumCase.getCompletionDate().substring(0, 4);
            if(countByCitizenship.containsKey(currentYear)) {
                String citizenshipOrOffice;
                if (citizenshipOrOfficeFlag){
                    citizenshipOrOffice = asylumCase.getCitizenship();
                } else {
                    citizenshipOrOffice = asylumCase.getAsylumOffice();
                }
                HashMap<String, double[]> inner = countByCitizenship.get(currentYear);
                if (!inner.containsKey(citizenshipOrOffice)) {
                    double[] results = {0, 0, 0, 0};
                    inner.putIfAbsent(citizenshipOrOffice, results);
                }

                double[] citizenshipResults = inner.get(citizenshipOrOffice);
                double[] yearResults = countByYear.get(currentYear);
                if (asylumCase.getCaseOutcome().equals("Grant")) {
                    citizenshipResults[0] += 1;
                    yearResults[0] += 1;
                    dto.setGranted(dto.getGranted() + 1);
                } else if (asylumCase.getCaseOutcome().equals("Deny/Referral")) {
                    citizenshipResults[1] += 1;
                    yearResults[1] += 1;
                    dto.setDenied(dto.getDenied() + 1);
                } else {
                    citizenshipResults[2] += 1;
                    yearResults[2] += 1;
                    dto.setAdminClosed(dto.getAdminClosed() + 1);
                }
                citizenshipResults[3] += 1;
                yearResults[3] += 1;
                dto.setTotalCases(dto.getTotalCases() + 1);
                inner.put(citizenshipOrOffice, citizenshipResults);
                countByYear.put(currentYear, yearResults);
                countByCitizenship.put(currentYear, inner);
            }
        });

        List<AsylumYearSummaryModel> yearSummary = new ArrayList<>();
        for (Map.Entry<String, HashMap<String, double[]>> inner : countByCitizenship.entrySet()) {
            List<AsylumSummaryModel> temp = new ArrayList<>();
            for (Map.Entry<String, double[]> entry : inner.getValue().entrySet()) {

                double[] current = entry.getValue();
                if(current[3] != 0) {
                    double total = current[0] + current[1] + current[2];
                    if (percentFlag) {
                        current[0] = current[0] / total * 100;
                        current[1] = current[1] / total * 100;
                        current[2] = current[2] / total * 100;
                    }
                    if (citizenshipOrOfficeFlag) {
                        temp.add(new AsylumSummaryModel(
                                entry.getKey(), null, current[0], current[1], current[2], (int) current[3]));
                    } else {
                        temp.add(new AsylumSummaryModel(
                                null, entry.getKey(), current[0], current[1], current[2], (int) current[3]));
                    }
                }
            }
            double[] current = countByYear.get(inner.getKey());
            if(current[3] != 0) {
                double total = current[0] + current[1] + current[2];
                if (percentFlag) {
                    current[0] = current[0] / total * 100;
                    current[1] = current[1] / total * 100;
                    current[2] = current[2] / total * 100;
                }
                yearSummary.add(new AsylumYearSummaryModel(
                        inner.getKey(), current[0], current[1], current[2], (int) current[3], temp));
            }
        }
        return yearSummary;
    }

    private List<AsylumCitizenshipSummaryModel> getNumByCitizenship(Iterable<AsylumCase> casesIterable) {
        Map<String, double[]> countByCitizenship = new HashMap<>();

        casesIterable.forEach(asylumCase -> {
            String citizenship = asylumCase.getCitizenship();
            if (countByCitizenship.containsKey(citizenship)) {
                double[] citizenshipResults = countByCitizenship.get(citizenship);
                if (asylumCase.getCaseOutcome().equals("Grant")) {
                    citizenshipResults[0] += 1;
                } else if (asylumCase.getCaseOutcome().equals("Deny/Referral")) {
                    citizenshipResults[1] += 1;
                } else {
                    citizenshipResults[2] += 1;
                }
                citizenshipResults[3] += 1;
            } else {
                if (asylumCase.getCaseOutcome().equals("Grant")) {
                    countByCitizenship.put(citizenship, new double[]{1,0,0,1});
                } else if (asylumCase.getCaseOutcome().equals("Deny/Referral")) {
                    countByCitizenship.put(citizenship, new double[]{0,1,0,1});
                } else {
                    countByCitizenship.put(citizenship, new double[]{0,0,1,1});
                }
            }
        });
        List<AsylumCitizenshipSummaryModel > yearSummary = new ArrayList<>();
        for (Map.Entry<String, double[]> entry : countByCitizenship.entrySet()) {
            double[] current = entry.getValue();
            if(current[3] != 0) {
                double total = current[0] + current[1] + current[2];
                if (percentFlag) {
                    current[0] = current[0] / total * 100;
                    current[1] = current[1] / total * 100;
                    current[2] = current[2] / total * 100;
                }
                yearSummary.add(new AsylumCitizenshipSummaryModel(entry.getKey(),
                        current[0], current[1], current[2], (int) current[3]));
            }
        }
        return yearSummary;
    }

}
