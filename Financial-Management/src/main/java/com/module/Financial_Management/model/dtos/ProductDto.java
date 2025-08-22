package com.module.Financial_Management.model.dtos;

import com.module.Financial_Management.model.enums.ProductEnum;
import lombok.*;

import java.math.BigDecimal;


/**
 * La anotación @Data de Lombok genera automáticamente:
 * - @Getter y @Setter para todos los campos
 * - @ToString
 * - @EqualsAndHashCode
 * - Un constructor @RequiredArgsConstructor
 * - @Value si la clase es final
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private ProductEnum name;
    private String description;
    private BigDecimal price;
}
