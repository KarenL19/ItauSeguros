package com.store.itauseguros.service.impl;


import com.itauseguros.model.PageableProducts;
import com.itauseguros.model.Product;
import com.itauseguros.model.ProductRequestDTO;
import com.store.itauseguros.mapper.ProductMapper;
import com.store.itauseguros.model.ProductEntity;
import com.store.itauseguros.repository.ProductRepository;
import com.store.itauseguros.service.ProductService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public PageableProducts productsGet(String id, String category, String name, Pageable pageable) {
        Page<ProductEntity> productsPageable = fetchProducts(name, category, pageable, id);
        if (productsPageable.getContent().isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        List<Product> productList = new ArrayList<>();
        for (ProductEntity productEntity : productsPageable) {
            productList.add(ProductMapper.toProduct(productEntity));
        }

        return ProductMapper.toPageableProducts(productList, productsPageable);
    }

    private Page<ProductEntity> fetchProducts(String name, String category, Pageable pageable,String id) {
       if (id != null) {
           List<ProductEntity> entity = fetchProduct(id);
           return new PageImpl<>(entity, pageable, entity.size());
       }
        if (name != null) return productRepository.findByName(name.toUpperCase(), pageable);
        if (category != null) return productRepository.findByCategory(category.toUpperCase(), pageable);
        return productRepository.findAll(pageable);
    }

    private List<ProductEntity> fetchProduct(String id) {
        List<ProductEntity> productList = new ArrayList<>();
        productRepository.findById(id).ifPresent(productList::add);
        return productList;
    }

    @Override
    public Product productsPost(ProductRequestDTO productRequestDTO) {
        if (productRequestDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product data");
        }
        ProductEntity productEntity = getProductEntityFromDTO(UUID.randomUUID().toString(),productRequestDTO);
        try {
            productRepository.save(productEntity);
        } catch (DataAccessException dae) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred during saving product", dae);
        }
       return ProductMapper.toProduct(productEntity);
    }

    @Override
    public Product productsProductIdPut(String productId, ProductRequestDTO productRequestDTO) {
        ProductEntity productEntity = getProductEntityFromDTO(productId, productRequestDTO);
        productRepository.findById(productId)
                .ifPresentOrElse(
                        entity -> productRepository.save(productEntity),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
                        }
                );
        return ProductMapper.toProduct(productEntity);
    }

    private ProductEntity getProductEntityFromDTO(String productId, ProductRequestDTO productRequestDTO) {
        return ProductMapper.toEntity(productRequestDTO, productId);
    }
}
