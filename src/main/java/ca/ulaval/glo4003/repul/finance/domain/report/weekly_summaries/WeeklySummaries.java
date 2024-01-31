package ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries;

import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeeklySummaries {

    private List<WeeklySummary> weeklySummaries;

    public WeeklySummaries(List<ReportBill> bills, Semester semester) {
        generateWeeklySummaries(bills, semester);
    }

    public List<WeeklySummary> getWeeklySummaries() {
        return weeklySummaries;
    }

    private void generateWeeklySummaries(List<ReportBill> bills, Semester semester) {
        weeklySummaries = new ArrayList<>();

        Map<Integer, List<ReportBill>> groupedBillsByWeekNumber = groupBillsByWeekNumber(
            bills
        );
        List<Integer> semesterWeekNumbers = semester.getWeekNumbers();

        compileBillsSummary(semesterWeekNumbers, groupedBillsByWeekNumber);
    }

    private void compileBillsSummary(
        List<Integer> semesterWeekNumbers,
        Map<Integer, List<ReportBill>> groupedBillsByWeekNumber
    ) {
        semesterWeekNumbers.forEach(weekNumber -> {
            List<ReportBill> weekBills = groupedBillsByWeekNumber.getOrDefault(
                weekNumber,
                new ArrayList<>()
            );

            WeeklySummary weeklySummary = new WeeklySummary(weekNumber, weekBills);
            weeklySummaries.add(weeklySummary);
        });
    }

    private Map<Integer, List<ReportBill>> groupBillsByWeekNumber(
        List<ReportBill> bills
    ) {
        return bills.stream().collect(Collectors.groupingBy(ReportBill::getWeekNumber));
    }
}
