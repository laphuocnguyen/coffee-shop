package com.example.demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@NoArgsConstructor
public class MenuItem  extends BaseModel {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="shop_id", nullable=false)
    private Shop shop;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;
}
