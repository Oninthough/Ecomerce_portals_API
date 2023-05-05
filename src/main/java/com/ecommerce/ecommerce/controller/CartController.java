package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.payLoad.CartDTO;
import com.ecommerce.ecommerce.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;
    @PostMapping("/{userId}")
    public ResponseEntity<CartDTO> createCartForUser(@PathVariable Long userId, @RequestBody CartDTO cartDTO) {
        CartDTO createdCart = cartService.createCartForUser(userId, cartDTO);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartDTO>> fetchCartById(@PathVariable Long userId) {
        List<CartDTO> cart = cartService.fetchCartById(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/{userId}/{cartId}")
    public ResponseEntity<Void> updateCartById(@PathVariable Long userId, @PathVariable Long cartId, @RequestBody CartDTO cartDTO) {
        cartService.updateCartById(userId, cartId, cartDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteCartById(@PathVariable Long userId) {
        cartService.deleteCartById(userId);
        return new ResponseEntity<>("Cart Deleted",HttpStatus.OK);
    }
}
