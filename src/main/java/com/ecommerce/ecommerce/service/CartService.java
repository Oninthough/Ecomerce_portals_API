package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.payLoad.CartDTO;

import java.util.List;

public interface CartService {
    public CartDTO createCartForUser(Long userId, CartDTO cartDTO);
    public List<CartDTO> fetchCartById(Long userId);
    public void deleteCartById(Long userId);
    public void updateCartById(Long userId, Long cartId, CartDTO cartDTO);

}
