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
                new AsylumCase("S00000001", "MEXICO", "OTHER",
                "Admin Close/Dismissal", "3/11/16", "2021-05-28T05:04:16.000Z", "ZLA"),

                new AsylumCase("S00000002", "ROMANIA", "OTHER",
                "Admin Close/Dismissal", "2/10/18", "2021-05-28T05:04:37.000Z", "ZLA"),

                new AsylumCase("S00000003", "VENEZUELA", "OTHER",
                "Admin Close/Dismissal", "12/10/20", "2021-05-28T05:04:44.000Z", "ZSF"),

                new AsylumCase("S00000004", "RUSSIA", "OTHER",
                "Deny/Referral", "10/18/18", "2021-05-28T05:04:44.000Z", "ZLA"),

                new AsylumCase("S00000005", "RUSSIA", "OTHER",
                "Deny/Referral", "10/18/18", "2021-05-28T05:04:44.000Z", "ZLA"),

                new AsylumCase("S00000006", "CHINA, PEOPLE'S REPUBLIC OF", "OTHER",
                        "Deny/Referral", "10/18/18", "2021-05-28T05:04:44.000Z", "ZLA"),

                new AsylumCase("S00000007", "ARMENIA", "ARMENIAN",
                        "Admin Close/Dismissal", "3/7/17", "2021-05-28T05:04:37.000Z", "ZSF"),

                new AsylumCase("S00000008", "EL SALVADOR", "OTHER",
                        "Grant", "1/5/17", "2021-05-28T05:04:21.000Z", "ZLA"),

                new AsylumCase("S00000009", "EL SALVADOR", "OTHER",
                        "Grant", "1/5/17", "2021-05-28T05:04:21.000Z", "ZLA"),

                new AsylumCase("S00000010", "GUATEMALA", "OTHER",
                        "Grant", "11/10/16", "2021-05-28T05:04:22.000Z", "ZSF"),

                new AsylumCase("S00000011", "UKRAINE", "OTHER",
                        "Deny/Referral", "7/13/18", "2021-05-28T05:04:05.000Z", "ZSF")
        );

        asylumCaseRepository.saveAll(cases);
    }
}
