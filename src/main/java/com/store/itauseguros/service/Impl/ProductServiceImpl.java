package com.store.itauseguros.service.Impl;


import com.itauseguros.model.PageableProducts;
import com.itauseguros.model.Product;
import com.itauseguros.model.ProductRequestDTO;
import com.store.itauseguros.domain.TariffCalculator;
import com.store.itauseguros.mapper.ProductMapper;
import com.store.itauseguros.model.ProductEntity;
import com.store.itauseguros.repository.ProductRepository;
import com.store.itauseguros.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    final
    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public PageableProducts productsGet(String category, String name, Pageable pageable) {
        Page<ProductEntity> productsPageable = fetchProducts(name, category, pageable);
        List<Product> productList = new ArrayList<>();
        for (ProductEntity productEntity : productsPageable) {
            productList.add(ProductMapper.toProduct(productEntity));
        }

        return ProductMapper.toPageableProducts(productList, productsPageable);
    }

    private Page<ProductEntity> fetchProducts(String name, String category, Pageable pageable) {
        if (name != null) return productRepository.findByName(name.toUpperCase(), pageable);
        if (category != null) return productRepository.findByCategory(category.toUpperCase(), pageable);
        return productRepository.findAll(pageable);
    }


    @Override
    public Product productsPost(ProductRequestDTO productRequestDTO) {
        ProductEntity productEntity = ProductEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .name(productRequestDTO.getName())
                .basePrice(productRequestDTO.getBasePrice())
                .tariffPrice(TariffCalculator.calculateTariffPrice(productRequestDTO.getBasePrice(), productRequestDTO.getCategory()))
                .category(productRequestDTO.getCategory())
                .build();
        productRepository.save(productEntity);
        return ProductMapper.toProduct(productEntity);
    }

    @Override
    public Product productsProductIdGet(String productId) {
        return null;
    }

    @Override
    public Product productsProductIdPut(String productId, Product product) {
        return null;
    }
}
