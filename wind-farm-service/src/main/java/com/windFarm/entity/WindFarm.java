package com.windFarm.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "wind_farm")
public class WindFarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacity_MW")
    private Double capacityMW;

    @Column(name = "description")
    private String description;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "location")
    private String location;
}
