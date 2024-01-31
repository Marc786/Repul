package ca.ulaval.glo4003.lib.date;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup;
import static ca.ulaval.glo4003.constant.Constants.Date.DATE_FORMAT;
import static ca.ulaval.glo4003.constant.Constants.Date.DAY_START_TIME;

import ca.ulaval.glo4003.lib.date.exception.InvalidDateFormatException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtils {

    private DateUtils() {}

    public static LocalDate formatDateToLocalDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }
    }

    public static boolean isDateWithinRange(LocalDate targetDate, int rangeInDays) {
        LocalDate currentDate = LocalDate.now(ClockSetup.getClock());
        long daysDifference = Duration
            .between(
                currentDate.atTime(LocalTime.now(ClockSetup.getClock())),
                targetDate.atTime(DAY_START_TIME)
            )
            .toDays();
        return daysDifference < rangeInDays;
    }
}
