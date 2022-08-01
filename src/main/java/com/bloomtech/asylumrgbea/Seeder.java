package com.bloomtech.asylumrgbea;

import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {

    private final AsylumCaseRepository asylumCaseRepository;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {

        List<AsylumCase> cases = List.of(
                new AsylumCase("S00000001", "MEXICO", "Admin Close/Dismissal",
                        "2016-3-11", "2021-05-28T05:04:16.000Z", "ZLA"),

                new AsylumCase("S00000002", "ROMANIA", "Admin Close/Dismissal",
                        "2018-2-10", "2021-05-28T05:04:37.000Z", "ZLA"),

                new AsylumCase("S00000003", "VENEZUELA", "Admin Close/Dismissal",
                        "2020-12-10", "2021-05-28T05:04:44.000Z", "ZSF"),

                new AsylumCase("S00000004", "RUSSIA", "Deny/Referral", "2018-10-18",
                        "2021-05-28T05:04:44.000Z", "ZLA"),

                new AsylumCase("S00000005", "RUSSIA", "Deny/Referral", "2018-10-18",
                        "2021-05-28T05:04:44.000Z", "ZLA"),

                new AsylumCase("S00000006", "CHINA, PEOPLE'S REPUBLIC OF", "Deny/Referral",
                        "2018-10-18", "2021-05-28T05:04:44.000Z", "ZLA"),

                new AsylumCase("S00000007", "ARMENIA", "Admin Close/Dismissal",
                        "2017-3-7", "2021-05-28T05:04:37.000Z", "ZSF"),

                new AsylumCase("S00000008", "EL SALVADOR", "Grant", "2017-1-05",
                        "2021-05-28T05:04:21.000Z", "ZLA"),

                new AsylumCase("S00000009", "EL SALVADOR", "Grant", "2017-1-05",
                        "2021-05-28T05:04:21.000Z", "ZLA"),

                new AsylumCase("S00000010", "GUATEMALA", "Grant", "2016-11-10",
                        "2021-05-28T05:04:22.000Z", "ZSF"),

                new AsylumCase("S00000011", "UKRAINE", "Deny/Referral", "2018-7-13",
                        "2021-05-28T05:04:05.000Z", "ZSF")
        );

        asylumCaseRepository.saveAll(cases);
    }
}
