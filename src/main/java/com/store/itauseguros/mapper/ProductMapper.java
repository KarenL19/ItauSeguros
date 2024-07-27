package com.store.itauseguros.mapper;

import com.itauseguros.model.Product;
import com.store.itauseguros.model.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public static Product toPerson(ProductEntity personEntity) {
        Product product = new Product();
        product.setId(personEntity.getId());
        product.setName(personEntity.getName());
        product.setCategory(personEntity.getCategory());
        product.setBasePrice(personEntity.getBasePrice());
        product.setTariffPrice(personEntity.getTariffPrice());
        return product;
    }
}
