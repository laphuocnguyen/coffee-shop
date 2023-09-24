package com.example.demo.repository;

import com.example.demo.entities.Queue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository  extends CrudRepository<Queue, Integer> {
}
