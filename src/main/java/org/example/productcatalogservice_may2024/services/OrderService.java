package org.example.productcatalogservice_may2024.services;

import org.example.productcatalogservice_may2024.exceptions.OptimisticLockingFailureException;
import org.example.productcatalogservice_may2024.exceptions.OutOfStockException;
import org.example.productcatalogservice_may2024.exceptions.ProductNotFoundException;
import org.example.productcatalogservice_may2024.models.Order;
import org.example.productcatalogservice_may2024.models.OrderItem;
import org.example.productcatalogservice_may2024.models.Product;
import org.example.productcatalogservice_may2024.repositories.OrderRepository;
import org.example.productcatalogservice_may2024.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void createOrder(List<OrderItem> orderItems) throws InterruptedException {
        int retryCount = 0;
        boolean successful = false;

        while (!successful && retryCount < 3) {
            try {
                Order order = new Order();
                order.setOrderItems(new HashSet<>(orderItems));

                for (OrderItem orderItem : orderItems) {
                    Optional<Product> productOpt = productRepository.findById(orderItem.getProduct().getId());
                    if (productOpt.isPresent()) {
                        Product product = productOpt.get();
                        if (product.getQuantity() >= orderItem.getQuantity()) {
                            product.setQuantity(product.getQuantity() - orderItem.getQuantity());
                            productRepository.save(product);

                            orderItem.setOrder(order);
                        } else {
                            throw new OutOfStockException("Not enough stock for product: " + product.getName());
                        }
                    } else {
                        throw new ProductNotFoundException("Product not found: " + orderItem.getProduct().getId());
                    }
                }

                orderRepository.save(order);
                successful = true;
            } catch (ObjectOptimisticLockingFailureException e) {
                retryCount++;
                if (retryCount == 3) {
                    throw new OptimisticLockingFailureException("Failed to create order after 3 attempts. Please try again later.", e);
                }
            }
        }
    }
}

