package com.example.demo.service;


import com.example.demo.exception.BusinessException;
import com.example.demo.model.OrderDto;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.RequestOrderItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Testcontainers
@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class CustomerServiceIT {
    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withUsername("duke")
            .withPassword("password")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private CustomerService customerService;

    @Test
    void shouldCreateOrderSuccessful_whenValidInput() {
        List<RequestOrderItemDto> orderItems = new ArrayList<>();
        orderItems.add(RequestOrderItemDto.builder().menuItemId(1).quantity(4).build());
        orderItems.add(RequestOrderItemDto.builder().menuItemId(2).quantity(6).build());
        OrderDto orderDto = customerService.createOrder("admin", 2, orderItems);
        assertTrue(orderDto.getTotalAmount() == 310000l);
        assertTrue(orderDto.getWaitingTimes()== 45);
        assertTrue(orderDto.getShopId()== 2);
        assertTrue(orderDto.getStatus()==OrderStatus.IN_QUEUE);
    }

    @Test
    void shouldThrowException_whenCustomerNotFound() {
        try {
            OrderDto orderDto = customerService.createOrder("abc", 2,  new ArrayList<>());
        }catch (BusinessException businessException) {
            assertEquals(businessException.getMessage(), "Customer not found with username abc");
        }
    }

    @Test
    void shouldThrowException_whenShopNotFound() {
        try {
            OrderDto orderDto = customerService.createOrder("admin", 200,  new ArrayList<>());
        }catch (BusinessException businessException) {
            assertEquals(businessException.getMessage(), "Shop not found with shop id 200");
        }
    }

    @Test
    void shouldThrowException_whenOrderItemsEmpty() {
        try {
            OrderDto orderDto = customerService.createOrder("admin", 2,  new ArrayList<>());
        }catch (BusinessException businessException) {
            assertEquals(businessException.getMessage(), "Request order items are empty");
        }
    }

    @Test
    void shouldThrowException_whenOrderItemsWrong() {
        List<RequestOrderItemDto> orderItems = new ArrayList<>();
        orderItems.add(RequestOrderItemDto.builder().menuItemId(1).quantity(4).build());
        orderItems.add(RequestOrderItemDto.builder().menuItemId(2).quantity(6).build());
        orderItems.add(RequestOrderItemDto.builder().menuItemId(20).quantity(10).build());
        try {
            OrderDto orderDto = customerService.createOrder("admin", 2,  orderItems);
        }catch (BusinessException businessException) {
            assertEquals(businessException.getMessage(), "Shop id 2 can not found menu item ids [20]");
        }
    }
}
