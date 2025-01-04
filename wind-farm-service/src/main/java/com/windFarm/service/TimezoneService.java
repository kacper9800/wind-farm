package com.windFarm.service;

import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TimezoneService {

    public Set<String> getAllTimezones() {
        return ZoneId.getAvailableZoneIds().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
    }
}