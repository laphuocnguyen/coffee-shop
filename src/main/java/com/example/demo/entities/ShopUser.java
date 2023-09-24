package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "shop_user",
        uniqueConstraints = {
                @UniqueConstraint(name="user_username_unique", columnNames = "username")
        }
)

public class ShopUser extends BaseModel {

    @Column(name = "username", length = 100, nullable = false)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @OneToOne(mappedBy = "shopUser")
    private Customer customer;

}