package com.example.demo.entities;

import com.example.demo.model.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_order")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CustomerOrder extends BaseModel {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total")
    private long totalAmount;

    @Column(name="customer_id")
    private int customerId;

    @Column(name="shop_id")
    private int shopId;

    @OneToMany(
            mappedBy = "customerOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setCustomerOrder(this);
        this.totalAmount += orderItem.getPrice();
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setCustomerOrder(null);
    }
}
