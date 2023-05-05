package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.payLoad.CategoryDTO;

import java.util.List;

public interface CategoryService {
    public CategoryDTO saveOneCategory(CategoryDTO categoryDTO);

    public List<CategoryDTO> findAllCatagories();
}
