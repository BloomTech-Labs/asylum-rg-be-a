package com.bloomtech.asylumrgbea.helpers;

import com.bloomtech.asylumrgbea.entities.AsylumCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AsylumCaseHelper {

    static String[] offices = {"ZLA","ZSF","ZMI","ZAR","ZOL","ZCH","ZNY","ZHN","ZNK"};
    static String[] citizenShips = {"RUSSIA","EL SALVADOR","CHINA, PEOPLE'S REPUBLIC OF","INDONESIA","MEXICO","UKRAINE","GUATEMALA","VENEZUELA","PHILIPPINES","HONDURAS","UGANDA","JORDAN","INDIA","IRAQ","ERITREA","KYRGYZSTAN","ARMENIA","NEPAL","SYRIA","TURKEY","LEBANON","GEORGIA","LEBANON"};
    static String[] caseOutcomes = {"Grant","Admin Close/Dismissal","Deny/Referral"};

    public static List<AsylumCase> createCaseSampleForOffice(int totalItems, int totalItemsWithSameOffice) {
        List<AsylumCase> returnedList = new ArrayList<>();
        String sameAsylumOffice = offices[new Random().nextInt(offices.length)];

        int sameOfficesMade = 0;
        for (int i = 0; i < totalItems;i ++) {
            AsylumCase asylumCase = new AsylumCase();
            String randomOffice = sameAsylumOffice;

            while (randomOffice.equals(sameAsylumOffice) && sameOfficesMade >= totalItemsWithSameOffice) {
                randomOffice = offices[new Random().nextInt(offices.length)];
            }

            asylumCase.setAsylumOffice(randomOffice);
            asylumCase.setId(String.valueOf(i));

            sameOfficesMade++;
            returnedList.add(asylumCase);
        }

        return returnedList;
    }

    public static List<AsylumCase> createCaseSampleForCitizenship(int totalItems, int totalItemsWithSameCitizenship) {
        List<AsylumCase> returnedList = new ArrayList<>();
        String sameCitizenship = citizenShips[new Random().nextInt(citizenShips.length)];

        int sameCitizenshipMade = 0;
        for (int i = 0; i < totalItems;i ++) {
            AsylumCase asylumCase = new AsylumCase();
            String randomCitizenShip = sameCitizenship;

            while (randomCitizenShip.equals(sameCitizenship) && sameCitizenshipMade >= totalItemsWithSameCitizenship) {
                randomCitizenShip = citizenShips[new Random().nextInt(citizenShips.length)];
            }

            asylumCase.setCitizenship(randomCitizenShip);
            asylumCase.setId(String.valueOf(i));

            sameCitizenshipMade++;
            returnedList.add(asylumCase);
        }

        return returnedList;
    }

    public static List<AsylumCase> createCaseSampleForCaseOutcome(int totalItems, int totalItemsWithSameCaseOutCome) {
        List<AsylumCase> returnedList = new ArrayList<>();
        String sameCaseOutcome = caseOutcomes[new Random().nextInt(caseOutcomes.length)];

        int sameCaseOutcomeMade = 0;
        for (int i = 0; i < totalItems;i ++) {
            AsylumCase asylumCase = new AsylumCase();
            String randomCaseOutcome = sameCaseOutcome;

            while (randomCaseOutcome.equals(sameCaseOutcome) && sameCaseOutcomeMade >= totalItemsWithSameCaseOutCome) {
                randomCaseOutcome = caseOutcomes[new Random().nextInt(caseOutcomes.length)];
            }

            asylumCase.setCaseOutcome(randomCaseOutcome);
            asylumCase.setId(String.valueOf(i));

            sameCaseOutcomeMade++;
            returnedList.add(asylumCase);
        }

        return returnedList;
    }

    //TODO make case for dates
}
