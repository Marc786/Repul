package ca.ulaval.glo4003.repul.finance.infra.dto;

import java.util.Map;

public record BillDto(
    String id,
    String customerId,
    String price,
    String reportDate,
    Map<String, String> details
) {}
