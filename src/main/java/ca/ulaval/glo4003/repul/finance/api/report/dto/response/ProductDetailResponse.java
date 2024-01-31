package ca.ulaval.glo4003.repul.finance.api.report.dto.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import java.util.Map;

public record ProductDetailResponse(@JsonAnyGetter Map<String, String> properties) {}
