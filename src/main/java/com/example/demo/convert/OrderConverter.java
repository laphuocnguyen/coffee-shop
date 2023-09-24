package com.example.demo.convert;

import com.example.demo.entities.CustomerOrder;
import com.example.demo.model.OrderDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderConverter {

    private final ModelMapper modelMapper;
    public OrderDto toOrderDto(CustomerOrder customerOrder) {
        return modelMapper.map(customerOrder, OrderDto.class);
    }
}
