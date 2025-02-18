package com.store.itauseguros.mapper;

import com.itauseguros.model.PageableProducts;
import com.itauseguros.model.PageableProductsTemplatePageableProducts;
import com.itauseguros.model.Product;
import com.itauseguros.model.ProductRequestDTO;
import com.store.itauseguros.domain.TariffCalculator;
import com.store.itauseguros.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {
    private ProductMapper() {}
    public static Product toProduct(ProductEntity personEntity) {
        Product product = new Product();
        product.setId(personEntity.getId());
        product.setName(personEntity.getName());
        product.setCategory(personEntity.getCategory());
        product.setBasePrice(personEntity.getBasePrice());
        product.setTariffPrice(personEntity.getTariffPrice());
        return product;
    }

    public static PageableProducts toPageableProducts(List<Product> productList, Page<ProductEntity> products) {
        PageableProducts pageableProducts = new PageableProducts();
        pageableProducts.setContent(productList);

        PageableProductsTemplatePageableProducts pageableProductsTemplate = new PageableProductsTemplatePageableProducts();
        pageableProductsTemplate.setLimit(products.getSize());
        pageableProductsTemplate.setOffset(products.getPageable().getOffset());
        pageableProductsTemplate.setPageElements(products.getNumberOfElements());
        pageableProductsTemplate.setPageNumber(products.getNumber());
        pageableProductsTemplate.setTotalPages(products.getTotalPages());
        pageableProductsTemplate.setTotalElements(products.getNumberOfElements());

        pageableProducts.setPageableProducts(pageableProductsTemplate);

        return pageableProducts;
    }

    public static ProductEntity toEntity(ProductRequestDTO productRequestDTO, String id) {
        return ProductEntity.builder()
                .id(String.valueOf(id))
                .name(productRequestDTO.getName().toUpperCase())
                .basePrice(productRequestDTO.getBasePrice())
                .tariffPrice(TariffCalculator.calculateTariffPrice(
                        productRequestDTO.getBasePrice(), productRequestDTO.getCategory()))
                .category(productRequestDTO.getCategory().toUpperCase())
                .build();
    }
}
