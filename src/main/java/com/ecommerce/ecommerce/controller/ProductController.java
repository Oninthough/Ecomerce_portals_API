package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.payLoad.ProductDTO;
import com.ecommerce.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductDTO saveOneProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveOneProduct(productDTO);
    }
}
