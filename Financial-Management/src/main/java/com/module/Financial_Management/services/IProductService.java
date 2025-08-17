package com.module.Financial_Management.services;

import com.module.Financial_Management.model.dtos.ProductDto;
import com.module.Financial_Management.repositories.IProductRepository;

public interface IProductService {

    void createProduct(ProductDto productDto);

    ProductDto getProductById(Long idProduct);

}
