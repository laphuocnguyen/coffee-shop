package com.example.demo.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class RequestOrderItemDto {
    private int menuItemId;
    private int quantity;
}
