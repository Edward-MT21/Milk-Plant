package com.module.Financial_Management.model.entities;

import com.module.Common.entities.BaseEntity;
import com.module.Financial_Management.model.enums.ProductEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Enumerated(EnumType.STRING)
    private ProductEnum name;

    private String description;

    private BigDecimal price;
}
