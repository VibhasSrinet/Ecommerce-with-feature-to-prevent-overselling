package org.example.productcatalogservice_may2024.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private List<ProductOrderDTO> products;
}
