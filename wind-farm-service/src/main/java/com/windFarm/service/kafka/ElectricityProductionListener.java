package com.windFarm.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.service.ElectricityProductionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class ElectricityProductionListener {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private final ElectricityProductionService service;

    ObjectMapper objectMapper = new ObjectMapper();

    public ElectricityProductionListener(ElectricityProductionService service) {
        this.service = service;
    }

    @KafkaListener(topics = "electricity_production", groupId = "local")
    public void listen(String value, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            ElectricityProductionDto electricityProductionDto = objectMapper.readValue(value, ElectricityProductionDto.class);
            service.createElectricityProduction(electricityProductionDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        logger.info(String.format("Consumed event from topic %s: value = %s", topic,value));
    }
}
