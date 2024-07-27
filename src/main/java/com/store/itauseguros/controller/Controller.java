package com.store.itauseguros.controller;

import com.itauseguros.api.ProductsApi;
import com.itauseguros.model.Product;
import com.itauseguros.model.ProductRequestDTO;
import com.store.itauseguros.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;
import java.util.Set;


@RestController
public class Controller implements ProductsApi {

    public final ProductService productService;

    @Autowired
    public Controller(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ProductsApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Pageable> productsGet(String category, String name, Integer limit, Integer offset, Set<String> sort) {
        return ProductsApi.super.productsGet(category, name, limit, offset, sort);
    }

    @Override
    public ResponseEntity<Product> productsPost(ProductRequestDTO productRequestDTO) {
        return ProductsApi.super.productsPost(productRequestDTO);
    }

    @Override
    public ResponseEntity<Product> productsProductIdGet(String productId) {
        return ProductsApi.super.productsProductIdGet(productId);
    }

    @Override
    public ResponseEntity<Product> productsProductIdPut(String productId, ProductRequestDTO productRequestDTO) {
        return ProductsApi.super.productsProductIdPut(productId, productRequestDTO);
    }


}
