package com.windFarm.controller;

import com.windFarm.service.TimezoneService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/timezones")
public class TimezoneController {

    private final TimezoneService service;

    public TimezoneController(TimezoneService service) {
        this.service = service;
    }

    @GetMapping
    public Set<String> getAllTimezones() {
        return this.service.getAllTimezones();
    }
}
