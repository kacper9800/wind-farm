package com.windFarm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "electricity_production")
public class ElectricityProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private WindFarm windFarm;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "electricity_produced_mw")
    private Double electricityProducedMW;

}



