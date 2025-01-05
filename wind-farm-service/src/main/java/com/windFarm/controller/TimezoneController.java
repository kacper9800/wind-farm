package com.windFarm.controller;

import com.windFarm.service.TimezoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/timezones")
@Tag(name = "TimezoneController", description = "API for retrieving available timezones")
public class TimezoneController {

    private final TimezoneService service;

    public TimezoneController(TimezoneService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Get all available timezones",
            description = "Retrieves a set of all available timezones."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved timezones"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Set<String> getAllTimezones() {
        return this.service.getAllTimezones();
    }
}
