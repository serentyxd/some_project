package com.dark.online.dto.product;

import com.dark.online.enums.OrderTypeEnum;
import lombok.Data;

@Data
public class CreateOrderDto {
    private OrderTypeEnum productTypeEnum;
    private Long price;
    private String description;
}