package com.store.itauseguros.service;

import com.itauseguros.model.PageableProducts;
import com.itauseguros.model.Product;
import com.itauseguros.model.ProductRequestDTO;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    PageableProducts productsGet(String id, String category, String name,  Pageable pageable);

    Product  productsPost(ProductRequestDTO productRequestDTO);

    Product  productsProductIdPut(String productId, ProductRequestDTO productRequestDTO);
}
