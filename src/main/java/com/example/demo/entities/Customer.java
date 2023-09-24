package com.example.demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends BaseModel {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false , referencedColumnName = "id")
    private ShopUser shopUser;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;
}
