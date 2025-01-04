package com.windFarm.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimezoneConverter {

    public static ZonedDateTime convertToTimezone(LocalDateTime utcTime, String targetTimezone) {
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId targetZone = ZoneId.of(targetTimezone);

        ZonedDateTime utcZonedDateTime = utcTime.atZone(utcZone);
        return utcZonedDateTime.withZoneSameInstant(targetZone);
    }
}