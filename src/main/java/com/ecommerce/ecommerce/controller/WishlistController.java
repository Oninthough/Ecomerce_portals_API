package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.payLoad.WishlistDTO;
import com.ecommerce.ecommerce.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/{userId}")
    public ResponseEntity<WishlistDTO> saveNewWishlist(@PathVariable Long userId,@RequestBody WishlistDTO wishlistDto) {
        WishlistDTO savedWishlistDto = wishlistService.saveNewWishlist(wishlistDto,userId);
        return ResponseEntity.ok(savedWishlistDto);
    }

    @GetMapping()
    public ResponseEntity<List<WishlistDTO>> fetchAllWishlist() {
        List<WishlistDTO> wishlistDtoList = wishlistService.fetchAllWishlist();
        return ResponseEntity.ok(wishlistDtoList);
    }
    @GetMapping("/{userId}")
    public WishlistDTO fetchWishlistById(@PathVariable Long userId) {
        return wishlistService.fetchWishlistById(userId);
    }

    @PutMapping("/{wishlistId}")
    public ResponseEntity<WishlistDTO> updateWishlist(@PathVariable Long wishlistId, @RequestBody WishlistDTO wishlistDto) {
        WishlistDTO updatedWishlistDto = wishlistService.updateWishlist(wishlistId, wishlistDto);
        return ResponseEntity.ok(updatedWishlistDto);
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<String> deleteWishlist(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlist(wishlistId);
        return new ResponseEntity<>("Wishlist Deleted", HttpStatus.OK);
    }

    //

}
