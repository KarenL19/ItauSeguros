package com.store.itauseguros.service;

import com.itauseguros.model.Product;
import com.itauseguros.model.ProductRequestDTO;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Pageable productsGet(String name, String category, Pageable pageable);

    Product  productsPost(ProductRequestDTO productRequestDTO);

    Product  productsProductIdGet(String productId);

    Product  productsProductIdPut(String productId, Product product);
}
