package com.example.demo.controller;

import com.example.demo.exception.ApiExceptionResponse;
import com.example.demo.model.OrderDto;
import com.example.demo.model.RequestOrderItemDto;
import com.example.demo.service.CustomerService;
import com.example.demo.service.impl.CustomerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@SecurityRequirement(name = "Bearer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping("shops/{shopId}/orders")
    @Operation(summary = "Create new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDto.class)) }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiExceptionResponse.class)) }) })
    public ResponseEntity<OrderDto> createOrder(@Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer shopId, @RequestBody List<RequestOrderItemDto> orderItems) {
        return new ResponseEntity<>(customerService.createOrder(userDetails.getUsername(), shopId, orderItems), HttpStatus.CREATED);
    }
}
