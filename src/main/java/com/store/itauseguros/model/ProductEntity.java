package com.store.itauseguros.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_products")
public class ProductEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "ds_name")
    private String name;

    @Column(name = "ds_category")
    private String category;

    @Column(name = "vl_base_price")
    private Double basePrice;

    @Column(name = "vl_tariff_price")
    private Double tariffPrice;

}
