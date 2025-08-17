package com.module.Financial_Management.model.enums;

public enum ProductEnum {

    RAW_MILK(1L),
    CURD(2L),
    GROUND_CHEESE(3L),
    DOUBLE_CREAM_CHEESE(4L);

    private final Long idProduct;

    ProductEnum(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getIdProduct() {
        return idProduct;
    }
}
