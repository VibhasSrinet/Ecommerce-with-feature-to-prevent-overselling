package org.example.productcatalogservice_may2024.services;

import org.example.productcatalogservice_may2024.dtos.UserDto;
import org.example.productcatalogservice_may2024.exceptions.InvalidRoleAccessException;
import org.example.productcatalogservice_may2024.exceptions.ProductNotFoundException;
import org.example.productcatalogservice_may2024.exceptions.UserNotFoundException;
import org.example.productcatalogservice_may2024.models.Category;
import org.example.productcatalogservice_may2024.models.Product;
import org.example.productcatalogservice_may2024.repositories.CategoryRepo;
import org.example.productcatalogservice_may2024.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Primary
public class StorageProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepo categoryRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Page<Product> getAllProducts(int paageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = Sort.by(sortBy).and(Sort.by("id").ascending());
        if(sortOrder.equals("desc")) {
            sort = sort.descending();
        }
        return productRepository.findAll(PageRequest.of(paageNumber, pageSize, sort));
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
            if (product.getCategory() != null) {
                Category category = categoryRepository.findById(product.getCategory().getId())
                        .orElseGet(() -> categoryRepository.save(product.getCategory()));
                product.setCategory(category);
            }
            return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        if (!productRepository.existsById(id)) {
            return null;
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            if (product.getName() != null) {
                existingProduct.setName(product.getName());
            }
            if (product.getDescription() != null) {
                existingProduct.setDescription(product.getDescription());
            }
            if (product.getImageUrl() != null) {
                existingProduct.setImageUrl(product.getImageUrl());
            }
            if (product.getPrice() != null) {
                existingProduct.setPrice(product.getPrice());
            }
            if (product.getCategory() != null) {
                existingProduct.setCategory(product.getCategory());
            }
            if (product.getQuantity() != null) {
                existingProduct.setQuantity(product.getQuantity());
            }
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public Product getProductBasedOnScope(Long productId, Long userId) {
        Product product = productRepository.findById(productId).orElse(null);
        if(product==null){
            throw new ProductNotFoundException("Product not found!");
        }
        UserDto userDto = restTemplate.getForEntity("http://userservice/users/{id}", UserDto.class, userId).getBody();
        if(userDto==null) {
            throw new UserNotFoundException("User not found!");
        }

        if(product.getIsPrivate()==true && !userDto.getRoles().contains("ADMIN")){
            throw new InvalidRoleAccessException("User does not has access to this product!");
        }
        return product;
    }

    @Override
    public Product deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return optionalProduct.get();
        }
        return null;
    }
}
