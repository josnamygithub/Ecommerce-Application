package com.productservice.service;

import com.productservice.common.AddResponse;
import com.productservice.dto.ProductRequest;
import com.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    AddResponse createProduct(ProductRequest productRequest);

    public List<ProductResponse> getAllproductRequest() ;
}
