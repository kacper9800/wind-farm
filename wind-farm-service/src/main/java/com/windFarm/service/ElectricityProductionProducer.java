package com.windFarm.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.windFarm.dto.WindFarmDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ElectricityProductionProducer {

    private static final Logger logger = LoggerFactory.getLogger(ElectricityProductionProducer.class);

    private static final String TOPIC = "electricity_production";

    private final Producer producer;
    private final WindFarmService windFarmService;

    public ElectricityProductionProducer(Producer producer, WindFarmService windFarmService) {
        this.producer = producer;
        this.windFarmService = windFarmService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void produceElectricity() {
        List<WindFarmDto> windFarms = windFarmService.getAllWindFarms();
        for (WindFarmDto farm : windFarms) {
            LocalDateTime timestamp = LocalDateTime.now();
            String key = prepareKey(farm.getId(), timestamp);
            String value = prepareValue(farm.getId(), farm.getCapacityMW(), timestamp);
            this.producer.sendMessage(TOPIC, key, value);
        }
    }

    private String prepareKey(long windFarmId, LocalDateTime timestamp) {
        return String.valueOf(windFarmId).concat(" - ").concat(timestamp.toString());
    }

    private String prepareValue(Long windFarmId, double capacityMW, LocalDateTime timestamp) {
        double randomProducedElectricityMWValue = ThreadLocalRandom.current().nextDouble(0, capacityMW);
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode json = factory.objectNode();

        json.put("wind_farm_id", windFarmId);
        json.put("timestamp", timestamp.toString());
        json.put("electricity_produced_mw",  randomProducedElectricityMWValue);

        return json.toString();
    }
}
