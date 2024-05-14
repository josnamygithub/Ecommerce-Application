package com.productservice.controller;

import com.productservice.common.AddResponse;
import com.productservice.dto.ProductRequest;
import com.productservice.dto.ProductResponse;
import com.productservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    private static final Logger Logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AddResponse> createProduct(@RequestBody ProductRequest productRequest) {

        Logger.info("Received request: {}", productRequest);
        try {
            AddResponse response = productService.createProduct(productRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Logger.error("Exception occurred in createProduct", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getAllproducts() {

        Logger.info("Fetching All Products");
        return ResponseEntity.ok(productService.getAllproductRequest());

    }
}