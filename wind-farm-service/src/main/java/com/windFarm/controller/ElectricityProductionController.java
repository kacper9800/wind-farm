package com.windFarm.controller;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.dto.ElectricityProductionFilterDto;
import com.windFarm.service.ElectricityProductionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/electricityProductions")
@Tag(name = "ElectricityProductionController", description = "API for managing electricity production data")
public class ElectricityProductionController {

    private final ElectricityProductionService electricityProductionService;

    public ElectricityProductionController(ElectricityProductionService electricityProductionService) {
        this.electricityProductionService = electricityProductionService;
    }

    @GetMapping
    @Operation(
            summary = "Get filtered electricity production data",
            description = "Retrieves a paginated list of electricity production data based on the provided filters."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Electricity production data retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<ElectricityProductionDto>> getElectricityProductions(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter criteria for electricity production",
                    required = false,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ElectricityProductionFilterDto.class)))
            ElectricityProductionFilterDto filterDto) {
        Page<ElectricityProductionDto> electricityProductionDtoPage = electricityProductionService.getElectricityProduction(filterDto);
        return ResponseEntity.ok(electricityProductionDtoPage);
    }

    @GetMapping("/averageCapacityFactor")
    @Operation(
            summary = "Get average capacity factor",
            description = "Calculates the average capacity factor based on the provided filter criteria."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Average capacity factor calculated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "number", format = "double"))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Double> getAverageCapacityFactor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter criteria for capacity factor calculation",
                    required = false,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ElectricityProductionFilterDto.class)))
            ElectricityProductionFilterDto filterDto) {
        Double averageCapacityFactor = electricityProductionService.getAverageCapacityFactor(filterDto);
        return ResponseEntity.ok(averageCapacityFactor);
    }

    @PostMapping
    @Operation(
            summary = "Create a new electricity production entry",
            description = "Creates a new entry in the electricity production database."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Electricity production entry created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ElectricityProductionDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ElectricityProductionDto> createElectricityProduction(
            @Validated @RequestBody(
                    description = "Details of the electricity production entry to be created",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ElectricityProductionDto.class)))
            ElectricityProductionDto electricityProductionDto) {
        ElectricityProductionDto createdElectricityProduction = electricityProductionService.createElectricityProduction(electricityProductionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdElectricityProduction);
    }
}
