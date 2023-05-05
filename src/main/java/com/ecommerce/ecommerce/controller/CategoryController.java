package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.payLoad.CategoryDTO;
import com.ecommerce.ecommerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDTO> saveOneCategory(@RequestBody CategoryDTO categoryDTO)
    {
        CategoryDTO categoryDTO1 = categoryService.saveOneCategory(categoryDTO);
        return new ResponseEntity<>(categoryDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/find")
    public ResponseEntity<List<CategoryDTO>> getAllCatagories()
    {
        List<CategoryDTO> categoryDTO2= categoryService.findAllCatagories();
        return ResponseEntity.ok(categoryDTO2);
    }

}
