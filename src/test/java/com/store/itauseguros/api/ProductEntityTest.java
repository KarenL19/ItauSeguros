package com.store.itauseguros.api;

import com.store.itauseguros.model.ProductEntity;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductEntityTest {

    @Test
    public void testGettersAndSetters() {
        // Criar uma instância de ProductEntity
        ProductEntity product = new ProductEntity();

        // Definir valores
        product.setId("123");
        product.setName("Test Product");
        product.setCategory("Category");
        product.setBasePrice(100.0);
        product.setTariffPrice(120.0);

        // Verificar se os valores foram definidos e recuperados corretamente
        assertEquals("123", product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Category", product.getCategory());
        assertEquals(100.0, product.getBasePrice());
        assertEquals(120.0, product.getTariffPrice());
    }
}