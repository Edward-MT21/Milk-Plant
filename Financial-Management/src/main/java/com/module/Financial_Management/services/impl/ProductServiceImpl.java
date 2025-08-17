package com.module.Financial_Management.services.impl;

import com.module.Financial_Management.model.dtos.ProductDto;
import com.module.Financial_Management.model.entities.Product;
import com.module.Financial_Management.repositories.IProductRepository;
import com.module.Financial_Management.services.IProductService;

public class ProductServiceImpl implements IProductService {

    private final IProductRepository iProductRepository;

    public ProductServiceImpl(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    public void createProduct(ProductDto productDto) {

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());

        iProductRepository.saveAndFlush(product);

    }

    public ProductDto getProductById(Long idProduct) {

        Product product = iProductRepository.findById(idProduct).orElseThrow(() -> new RuntimeException("Product not found"));
        ProductDto productDto = new ProductDto();
        productDto.setIdProduct(product.getIdProduct());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());

        return productDto;

    }
}
