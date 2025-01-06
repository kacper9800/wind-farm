package com.windFarm.controller;

import com.windFarm.dto.WindFarmDto;
import com.windFarm.service.WindFarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/windFarms")
@Tag(name = "WindFarmController", description = "API for managing wind farms")
public class WindFarmController {

    private final WindFarmService windFarmService;

    public WindFarmController(WindFarmService windFarmService) {
        this.windFarmService = windFarmService;
    }

    @GetMapping
    @Operation(
            summary = "Get all wind farms",
            description = "Retrieves a list of all wind farms.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of wind farms retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = WindFarmDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public List<WindFarmDto> getAllWindFarms() {
        return windFarmService.getAllWindFarms();
    }

    @GetMapping("/info")
    @Operation(
            summary = "Get wind farm information",
            description = "Fetches detailed information about a specific wind farm based on its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Wind farm information retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = WindFarmDto.class))),
                    @ApiResponse(responseCode = "404", description = "Wind farm not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public WindFarmDto getWindFarmInfo(@RequestParam long id) {
        return windFarmService.getWindFarmInfo(id);
    }

    @PostMapping
    @Operation(
            summary = "Create a new wind farm",
            description = "Creates a new wind farm with the provided data.",
            requestBody = @RequestBody(
                    description = "Details of the wind farm to be created",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WindFarmDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Wind farm created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<WindFarmDto> createWindFarm(
            @RequestBody(
                    description = "Details of the wind farm entry to be created",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WindFarmDto.class)))
            @org.springframework.web.bind.annotation.RequestBody WindFarmDto windFarmDto) {
        WindFarmDto createdWindFarm = this.windFarmService.createWindFarm(windFarmDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWindFarm);
    }
}
