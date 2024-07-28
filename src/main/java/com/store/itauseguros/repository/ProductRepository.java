package com.store.itauseguros.repository;

import com.store.itauseguros.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    Page<ProductEntity> findByCategory(String category,Pageable pageable);
    Page<ProductEntity> findByName(String name, Pageable pageable);

}
