package com.example.demo.repository;

import com.example.demo.entities.CustomerQueue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerQueueRepository extends CrudRepository<CustomerQueue, Integer> {
}
