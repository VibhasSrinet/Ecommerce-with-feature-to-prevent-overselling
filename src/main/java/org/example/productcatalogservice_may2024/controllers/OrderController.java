package org.example.productcatalogservice_may2024.controllers;

import org.example.productcatalogservice_may2024.dtos.OrderRequestDto;
import org.example.productcatalogservice_may2024.models.OrderItem;
import org.example.productcatalogservice_may2024.models.Product;
import org.example.productcatalogservice_may2024.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDto orderRequestDto) throws InterruptedException {
        List<OrderItem> orderItems= orderRequestDto.getProducts().stream().map(
                productOrderDTO -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(productOrderDTO.getQuantity());
                    Product product = new Product();
                    product.setId(productOrderDTO.getProductId());
                    product.setQuantity(productOrderDTO.getQuantity());
                    orderItem.setProduct(product);
                    return orderItem;
                }).collect(Collectors.toList());

        orderService.createOrder(orderItems);
        return ResponseEntity.ok("Order created successfully");
    }
}
