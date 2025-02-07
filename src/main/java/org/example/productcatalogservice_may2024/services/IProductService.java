package org.example.productcatalogservice_may2024.services;

import org.example.productcatalogservice_may2024.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IProductService {
    Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder);

    Product getProductById(Long id);

    Product createProduct(Product product);

    Product replaceProduct(Long id,Product product);

    Product deleteProduct(Long productId);

    Product updateProduct(Long productId, Product product);

    Product getProductBasedOnScope(Long productId, Long userId);
}
