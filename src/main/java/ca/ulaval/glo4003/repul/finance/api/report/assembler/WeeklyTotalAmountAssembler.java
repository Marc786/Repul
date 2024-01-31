package ca.ulaval.glo4003.repul.finance.api.report.assembler;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.WeeklyTotalAmountResponse;

public class WeeklyTotalAmountAssembler {

    public WeeklyTotalAmountResponse toResponse(Amount weeklyTotalAmount) {
        return new WeeklyTotalAmountResponse(weeklyTotalAmount.value());
    }
}
