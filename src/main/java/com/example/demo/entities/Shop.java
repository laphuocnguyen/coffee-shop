package com.example.demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shop")
@Getter
@Setter
@NoArgsConstructor
public class Shop extends BaseModel {

    @Column(name = "address")
    private String address;

    @Column(name = "queue_size")
    private int queueSize;

    @Column(name = "average_wait_time")
    private int averageWaitTime;    // in seconds

    @OneToMany(
            mappedBy = "shop",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MenuItem> menuItems = new ArrayList<>();

    @OneToMany(
            mappedBy = "shop",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Queue> shopQueues = new ArrayList<>();
}
