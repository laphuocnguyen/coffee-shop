package com.example.demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "customer_queue")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CustomerQueue extends BaseModel {

    @Column(name="customer_id")
    private int customerId;

    @Column(name="order_id")
    private int orderId;

    @Column(name="queue_id")
    private int queueId;


    @Column(name="order_number")
    private int orderNumber;

    @Column(name="status")
    private String status;
}
