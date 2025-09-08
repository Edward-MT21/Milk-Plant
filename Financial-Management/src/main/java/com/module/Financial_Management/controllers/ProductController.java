package com.module.Financial_Management.controllers;

import com.module.Common.dtos.ResponseDto;
import com.module.Financial_Management.model.dtos.ProductDto;
import com.module.Financial_Management.services.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ProductController")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final IProductService iProductService;

    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @PostMapping("/createProduct")
    private ResponseEntity<ResponseDto> createProduct(ProductDto productDto) {
        logger.debug("Start createProduct");

        iProductService.createProduct(productDto);

        logger.debug("End createProduct");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201", "Product created successfully"));

    }

    @GetMapping("/getProductById")
    private ResponseEntity<ProductDto> getProductById(Long idProduct) {
        logger.debug("Start getProductById");

        ProductDto productDto = iProductService.getProductById(idProduct);

        logger.debug("End getProductById");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productDto);
    }

}
