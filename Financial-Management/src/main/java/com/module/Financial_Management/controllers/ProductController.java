package com.module.Financial_Management.controllers;

import com.module.Common.dtos.ResponseDto;
import com.module.Financial_Management.model.dtos.ProductDto;
import com.module.Financial_Management.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ProductController")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final IProductService iProductService;

    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }


    @PostMapping("/createProduct")
    private ResponseEntity<ResponseDto> createProduct(ProductDto productDto) {
        iProductService.createProduct(productDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201", "Product created successfully"));

    }

    @GetMapping("/getProductById")
    private ResponseEntity<ProductDto> getProductById(Long idProduct) {

        ProductDto productDto = iProductService.getProductById(idProduct);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productDto);
    }

}
