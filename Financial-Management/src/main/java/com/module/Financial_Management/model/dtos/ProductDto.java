package com.module.Financial_Management.model.dtos;

import com.module.Financial_Management.model.enums.ProductEnum;
import lombok.Data;

import java.math.BigDecimal;


/**
 * La anotación @Data de Lombok genera automáticamente:
 * - @Getter y @Setter para todos los campos
 * - @ToString
 * - @EqualsAndHashCode
 * - Un constructor @RequiredArgsConstructor
 * - @Value si la clase es final
 */
@Data
public class ProductDto {
    private Long idProduct;
    private ProductEnum name;
    private String description;
    private BigDecimal price;
}
