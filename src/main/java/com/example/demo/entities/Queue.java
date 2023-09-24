package com.example.demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "queue")
@Getter
@Setter
@NoArgsConstructor
public class Queue extends BaseModel {

    @ManyToOne(fetch = FetchType.EAGER)
    private Shop shop;

    @Column(name = "order_number")
    private int orderNumber;

    @Column(name = "current_customer")
    private int currentCustomer;
}
