package ca.ulaval.glo4003.lib.semester.registry;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SemesterJson(
    @JsonProperty("semester_code") String semesterCode,
    @JsonProperty("start_date") String startDate,
    @JsonProperty("end_date") String endDate
) {}
