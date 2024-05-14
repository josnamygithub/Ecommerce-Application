package com.productservice.service;

import com.productservice.common.AddResponse;
import com.productservice.dto.ProductRequest;
import com.productservice.dto.ProductResponse;
import com.productservice.model.Product;
import com.productservice.repositroy.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger =LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;


    public AddResponse createProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                        .name(productRequest.getName())
                                .description(productRequest.getDescription())
                                        .price(productRequest.getPrice())
                .build();

        Product savedProduct = productRepository.insert(product);

        logger.info("product {} is saved ",product.getId());

        AddResponse res = new AddResponse();
        res.setMsg("Successfully Registered ");
        res.setId(savedProduct.getId());
        return res;
    }

    public List<ProductResponse> getAllproductRequest() {

        List<Product> products =productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {

    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .build();
    }
}

