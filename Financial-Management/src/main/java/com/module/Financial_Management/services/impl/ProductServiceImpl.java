package com.module.Financial_Management.services.impl;

import com.module.Financial_Management.model.dtos.ProductDto;
import com.module.Financial_Management.model.entities.Product;
import com.module.Financial_Management.repositories.IProductRepository;
import com.module.Financial_Management.services.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final IProductRepository iProductRepository;

    public ProductServiceImpl(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    public void createProduct(ProductDto productDto) {
        logger.debug("Start createProduct");

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        iProductRepository.saveAndFlush(product);

        logger.debug("End createProduct");
    }

    public ProductDto getProductById(Long idProduct) {
        logger.debug("Start getProductById");

        Product product = iProductRepository.findById(idProduct).orElseThrow(() -> new RuntimeException("Product not found"));
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());

        logger.debug("End getProductById");
        return productDto;
    }
}
