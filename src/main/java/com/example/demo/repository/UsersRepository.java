package com.example.demo.repository;

import com.example.demo.entities.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<ShopUser, Long> {

    Optional<ShopUser> findByUsername(String username);

}

