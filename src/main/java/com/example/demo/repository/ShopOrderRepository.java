package com.example.demo.repository;

import com.example.demo.entities.CustomerOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopOrderRepository extends CrudRepository<CustomerOrder, Integer> {
}
