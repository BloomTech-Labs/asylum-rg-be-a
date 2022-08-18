package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.models.AsylumSummaryDto;
import com.bloomtech.asylumrgbea.models.AsylumSummaryModel;
import com.bloomtech.asylumrgbea.models.AsylumYearSummaryModel;
import com.bloomtech.asylumrgbea.models.SummaryQueryParameterDto;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.AbstractAsyncConfiguration;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AsylumSummaryService {

    private final AsylumCaseRepository asylumCaseRepository;

    private boolean citizenshipOrOfficeFlag = false;

    public AsylumSummaryDto getSummaryBy(SummaryQueryParameterDto queryParameters) {
        int fromYear = Integer.parseInt(queryParameters.getFrom().substring(0, 4));
        int toYear = Integer.parseInt(queryParameters.getTo().substring(0, 4));
        int yearSpan = (toYear - fromYear);

        if (queryParameters.getView() == "office") {
            citizenshipOrOfficeFlag = true;
        }

        Map<String, String[]> rangeMap = Map.of(
                "completionDate", new String[] {
                        queryParameters.getFrom(),
                        queryParameters.getTo()
                }
        );
        Iterable<AsylumCase> casesIterable;
        if (queryParameters.getOffice() == null) {
            casesIterable = asylumCaseRepository.find(new HashMap<>(), rangeMap).getResults();
        } else {
            List<String> filter = new ArrayList<>();
            filter.add(queryParameters.getOffice());
            Map<String, List<String>> filterMap = Map.of("asylumOffice", filter);
            casesIterable = asylumCaseRepository.find(filterMap, rangeMap).getResults();
        }
        List<AsylumYearSummaryModel> summaries = getYearCitizenship(casesIterable, yearSpan, fromYear);
        return new AsylumSummaryDto(summaries);
    }

    private List<AsylumYearSummaryModel> getYearCitizenship(Iterable<AsylumCase> casesIterable, int yearSpan, int fromYear) {
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
                } else if (asylumCase.getCaseOutcome().equals("Deny/Referral")) {
                    citizenshipResults[1] += 1;
                    yearResults[1] += 1;
                } else {
                    citizenshipResults[2] += 1;
                    yearResults[2] += 1;
                }
                citizenshipResults[3] += 1;
                yearResults[3] += 1;
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
                double total = current[0] + current[1] + current[2];
                current[0] = current[0] / total * 100;
                current[1] = current[1] / total * 100;
                current[2] = current[2] / total * 100;
                if (citizenshipOrOfficeFlag) {
                    temp.add(new AsylumSummaryModel(
                            entry.getKey(), null, current[0], current[1], current[2], (int) current[3]));
                } else {
                    temp.add(new AsylumSummaryModel(
                            null, entry.getKey(), current[0], current[1], current[2], (int) current[3]));
                }
            }
            double[] current = countByYear.get(inner.getKey());
            double total = current[0] + current[1] + current[2];
            current[0] = current[0] / total * 100;
            current[1] = current[1] / total * 100;
            current[2] = current[2] / total * 100;
            yearSummary.add(new AsylumYearSummaryModel(inner.getKey(), temp,
                    current[0], current[1], current[2], (int) current[3]));
        }
        return yearSummary;
    }

//    private Map<String, List<AsylumSummaryModel>> getPercentAccepted(Iterable<AsylumCase> casesIterable, int yearSpan, int fromYear) {
//        Map<String, HashMap<String, double[]>> countByYear = new HashMap<>();
//        // countByYear.getKey(value)[0] = amountGranted
//        // countByYear.getKey(value)[1] = amountDenied
//        // countByYear.getKey(value)[2] = amountAdminClosed
//        // countByYear.getKey(value)[2] = totalCases
//        for (int i = 0; i <= yearSpan; i++) {
//            String tempString = fromYear + i + "-";
//            for (int j = 1; j <= 12; j++) {
//                countByYear.put(tempString + j, new HashMap<>());
//            }
//        }
//        casesIterable.forEach(asylumCase -> {
//            String currentYear = asylumCase.getCompletionDate().substring(0, 6);
//            if(currentYear.substring(5,6) == "-") {
//                currentYear = currentYear.substring(0,5);
//            }
//            if(countByYear.containsKey(currentYear)) {
//                HashMap<String, double[]> inner = countByYear.get(currentYear);
//                if (!inner.containsKey(asylumCase.getCitizenship())) {
//                    double[] results = {0, 0, 0, 0};
//                    inner.putIfAbsent(asylumCase.getCitizenship(), results);
//                }
//
//                double[] results = inner.get(asylumCase.getCitizenship());
//                if (asylumCase.getCaseOutcome().equals("Grant")) {
//                    results[0] += 1;
//                } else if (asylumCase.getCaseOutcome().equals("Deny/Referral")) {
//                    results[1] += 1;
//                } else {
//                    results[2] += 1;
//                }
//                results[3] += 1;
//                inner.put(asylumCase.getCitizenship(), results);
//
//                countByYear.put(currentYear, inner);
//            }
//        });
//
//        Map<String, List<AsylumSummaryModel>> result = new HashMap<>();
//        for (Map.Entry<String, HashMap<String, double[]>> inner : countByYear.entrySet()) {
//            List<AsylumSummaryModel> temp = new ArrayList<>();
//            for (Map.Entry<String, double[]> entry : inner.getValue().entrySet()) {
//
//                double[] current = entry.getValue();
//                double total = current[0] + current[1] + current[2];
//                current[0] = current[0] / total * 100;
//                current[1] = current[1] / total * 100;
//                current[2] = current[2] / total * 100;
//                temp.add(new AsylumSummaryModel(
//                        entry.getKey(), null, current[0], current[1], current[2], (int) current[3]));
//            }
//            result.put(inner.getKey(), temp);
//        }
//        return result;
//    }
}
