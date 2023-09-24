package com.example.demo.controller;

import com.example.demo.exception.BusinessException;
import com.example.demo.filter.JwtRequestFilter;
import com.example.demo.model.OrderDto;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.RequestOrderItemDto;
import com.example.demo.security.MyUserDetailsService;
import com.example.demo.service.impl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceImpl customerService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    void testCreateNewOrder_shouldCreateOrder_whenValidInput() throws Exception {
        OrderDto orderDto = OrderDto.builder().shopId(1).status(OrderStatus.IN_QUEUE)
                        .totalAmount(260000).build();
        when(customerService.createOrder(any(), any(), any())).thenReturn(orderDto);
        List<RequestOrderItemDto> orderItems = new ArrayList<>();
        orderItems.add(RequestOrderItemDto.builder().menuItemId(1).quantity(10).build());
        String jsonContent = objectMapper.writeValueAsString(orderItems);
        MvcResult mvcResult = this.mockMvc.perform(post("/api/customer/shops/1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated()).andReturn() ;
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(orderDto));
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    void testCreateNewOrder_shouldReturnBadRequest_whenInValidInput() throws Exception {
        when(customerService.createOrder(any(), any(), any())).thenThrow(new BusinessException("Invalid Input Data"));
        List<RequestOrderItemDto> orderItems = new ArrayList<>();
        orderItems.add(RequestOrderItemDto.builder().menuItemId(1).quantity(10).build());
        String jsonContent = objectMapper.writeValueAsString(orderItems);
        MvcResult mvcResult = this.mockMvc.perform(post("/api/customer/shops/1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is4xxClientError()).andReturn() ;
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertTrue(actualResponseBody.contains("Invalid Input Data"));
    }
}
