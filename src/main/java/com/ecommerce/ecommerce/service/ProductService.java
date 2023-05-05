package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.payLoad.ProductDTO;

import java.util.List;

public interface ProductService {
    public ProductDTO saveOneProduct(ProductDTO productDTO);
    public List<ProductDTO> getAllProducts();
}
