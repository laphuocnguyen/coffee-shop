package com.example.demo.service;

import com.example.demo.model.OrderDto;
import com.example.demo.model.RequestOrderItemDto;

import java.util.List;

public interface CustomerService {

    public OrderDto createOrder(String username, Integer shopId, List<RequestOrderItemDto> orderItems);

}
