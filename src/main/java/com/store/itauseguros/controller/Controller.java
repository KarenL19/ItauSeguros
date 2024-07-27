package com.store.itauseguros.controller;

import com.itauseguros.api.ProductsApi;
import com.itauseguros.model.PageableProducts;
import com.itauseguros.model.Product;
import com.itauseguros.model.ProductRequestDTO;
import com.store.itauseguros.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;


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
    public ResponseEntity<PageableProducts> productsGet(String id, String category, String name, Integer limit, Integer offset, String sort) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(sort));
        return ResponseEntity.ok(productService.productsGet(id,category, name, pageable));
    }

    @Override
    public ResponseEntity<Product> productsPost(ProductRequestDTO productRequestDTO) {
        return ResponseEntity.ok(productService.productsPost(productRequestDTO));
    }


    @Override
    public ResponseEntity<Product> productsProductIdPut(String productId, ProductRequestDTO productRequestDTO) {
        return ResponseEntity.ok(productService.productsProductIdPut(productId,productRequestDTO));
    }


}
