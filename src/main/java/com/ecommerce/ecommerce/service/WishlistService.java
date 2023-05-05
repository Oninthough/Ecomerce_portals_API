package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.payLoad.WishlistDTO;

import java.util.List;

public interface WishlistService {
    public WishlistDTO saveNewWishlist(WishlistDTO wishlistDto, Long userId);
    public List<WishlistDTO> fetchAllWishlist();
    public WishlistDTO fetchWishlistById(Long userId);
    public WishlistDTO updateWishlist(Long wishlistId, WishlistDTO wishlistdto);
    public void deleteWishlist(Long wishlistId);
}
