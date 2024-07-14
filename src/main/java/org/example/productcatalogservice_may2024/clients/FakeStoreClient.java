package org.example.productcatalogservice_may2024.clients;

import jakarta.annotation.Nullable;
import org.example.productcatalogservice_may2024.dtos.FakeStoreProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductDto getProduct(Long productId) {
        FakeStoreProductDto fakeStoreProductDto = requestForEntity(HttpMethod.GET,"http://fakestoreapi.com/products/{id}",null,FakeStoreProductDto.class,productId).getBody();
        return fakeStoreProductDto;
    }

    public FakeStoreProductDto[] getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos = requestForEntity(HttpMethod.GET,"http://fakestoreapi.com/products/",null,FakeStoreProductDto[].class).getBody();
        return fakeStoreProductDtos;
    }

    public FakeStoreProductDto replaceProduct(Long productId, FakeStoreProductDto fakeStoreProductDtoRequest) {
        FakeStoreProductDto fakeStoreProductDtoResponse = requestForEntity(HttpMethod.PUT,"http://fakestoreapi.com/products/{id}",fakeStoreProductDtoRequest,FakeStoreProductDto.class,productId).getBody();
        return fakeStoreProductDtoResponse;
    }

    public FakeStoreProductDto updateProduct(Long productId, FakeStoreProductDto fakeStoreProductDtoRequest) {
        FakeStoreProductDto fakeStoreProductDtoResponse = requestForEntity(HttpMethod.PATCH,"http://fakestoreapi.com/products/{id}",fakeStoreProductDtoRequest,FakeStoreProductDto.class,productId).getBody();
        return fakeStoreProductDtoResponse;
    }

    public FakeStoreProductDto deleteProduct(Long productId) {
        FakeStoreProductDto fakeStoreProductDto = requestForEntity(HttpMethod.DELETE,"http://fakestoreapi.com/products/{id}",null,FakeStoreProductDto.class,productId).getBody();
        return fakeStoreProductDto;
    }

    public FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductDtoRequest) {
        FakeStoreProductDto fakeStoreProductDtoResponse = requestForEntity(HttpMethod.POST,"http://fakestoreapi.com/products/",fakeStoreProductDtoRequest,FakeStoreProductDto.class).getBody();
        return fakeStoreProductDtoResponse;
    }

    public <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}
