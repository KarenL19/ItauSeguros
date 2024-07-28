package com.store.itauseguros.controller;

import com.itauseguros.api.ProductsApi;
import com.itauseguros.model.PageableProducts;
import com.itauseguros.model.Product;
import com.itauseguros.model.ProductRequestDTO;
import com.store.itauseguros.metrics.MetricsService;
import com.store.itauseguros.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
public class Controller implements ProductsApi {

    private final ProductService productService;
    private final MetricsService metricsService;

    @Autowired
    public Controller(ProductService productService, MetricsService metricsService) {
        this.productService = productService;
        this.metricsService = metricsService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ProductsApi.super.getRequest();
    }

    @Override
    public ResponseEntity<PageableProducts> productsGet(
            @Parameter(name = "id", description = "Id product") @RequestParam(value = "id", required = false) String id,
            @Parameter(name = "category", description = "Filter by category name") @RequestParam(value = "category", required = false) String category,
            @Parameter(name = "name", description = "Filter by product name") @RequestParam(value = "name", required = false) String name,
            @Parameter(name = "_limit", description = "Number of records to be returned") @RequestParam(value = "_limit", required = false, defaultValue = "10") Integer limit,
            @Parameter(name = "_offset", description = "Number of records to be skipped") @RequestParam(value = "_offset", required = false, defaultValue = "0") Integer offset,
            @Parameter(name = "_sort", description = "Sort the result") @RequestParam(value = "_sort", required = false, defaultValue = "id") String sort
    ) {
        metricsService.incrementProductsGetCounter();
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(sort));
        PageableProducts result = metricsService.recordProductsGetTime(() ->
                productService.productsGet(id, category, name, pageable)
        );
        // Utiliza ResponseEntity diretamente para retornar o resultado
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Product> productsPost(ProductRequestDTO productRequestDTO) {
        metricsService.incrementProductsPostCounter();
        Product result = metricsService.recordProductsPostTime(() ->
                productService.productsPost(productRequestDTO)
        );
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Product> productsProductIdPut(String productId, ProductRequestDTO productRequestDTO) {
        metricsService.incrementProductsProductIdPutCounter();
        Product result = metricsService.recordProductsProductIdPutTime(() ->
                productService.productsProductIdPut(productId, productRequestDTO)
        );
        return ResponseEntity.ok(result);
    }
}
