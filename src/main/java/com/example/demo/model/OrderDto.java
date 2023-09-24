package com.example.demo.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class OrderDto {

    private int id;
    private OrderStatus status;
    private int shopId;
    private int customerId;
    private long totalAmount;
    private int waitingTimes;
}
