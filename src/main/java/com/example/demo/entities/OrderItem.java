package com.example.demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class OrderItem extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id", nullable=false)
    private CustomerOrder customerOrder;

    @Column(name="menu_item_id")
    private int menuItemId;

    @Column(name="price")
    private long price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem )) return false;
        return getId() == ((OrderItem) o).getId();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
