package org.example.productcatalogservice_may2024.services;

import org.example.productcatalogservice_may2024.clients.FakeStoreClient;
import org.example.productcatalogservice_may2024.dtos.FakeStoreProductDto;
import org.example.productcatalogservice_may2024.models.Category;
import org.example.productcatalogservice_may2024.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("fakestoreproductservice")
public class FakeStoreProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakeStoreClient fakeStoreClient;

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos =
                fakeStoreClient.getAllProducts();
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            products.add(getProduct(fakeStoreProductDto));
        }

        return products;
    }

    @Override
    public Product getProductById(Long productId) {
        return getProduct(fakeStoreClient.getProduct(productId));
    }

    @Override
    public Product createProduct(Product product) {
        return getProduct(fakeStoreClient.createProduct(getFakeStoreProductDto(product)));
    }
    @Override
    public Product replaceProduct(Long id, Product product) {
        return getProduct(fakeStoreClient.replaceProduct(id,getFakeStoreProductDto(product)));
    }

    @Override
    public Product deleteProduct(Long productId) {
        return getProduct(fakeStoreClient.deleteProduct(productId));
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        return getProduct(fakeStoreClient.updateProduct(productId,getFakeStoreProductDto(product)));
    }


    private Product getProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    private FakeStoreProductDto getFakeStoreProductDto(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setTitle(product.getName());
        if(product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }
}

