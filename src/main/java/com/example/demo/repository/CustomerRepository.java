package com.example.demo.repository;

import com.example.demo.entities.Customer;
import com.example.demo.entities.CustomerQueue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query("select cs from Customer cs inner join ShopUser su on cs.shopUser.id = su.id where su.username=:username")
    Customer findCustomerByShopUser(String username);
}
