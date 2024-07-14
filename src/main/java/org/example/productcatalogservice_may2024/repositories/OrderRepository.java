package org.example.productcatalogservice_may2024.repositories;

import org.example.productcatalogservice_may2024.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

