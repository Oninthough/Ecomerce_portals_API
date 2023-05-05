package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.entities.Wishlist;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.payLoad.WishlistDTO;
import com.ecommerce.ecommerce.repository.UserRepository;
import com.ecommerce.ecommerce.repository.WishlistRepository;
import com.ecommerce.ecommerce.service.WishlistService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, ModelMapper modelMapper) {
        this.wishlistRepository = wishlistRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public WishlistDTO saveNewWishlist(WishlistDTO wishlistDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Wishlist wishlist = modelMapper.map(wishlistDto, Wishlist.class);
        wishlist.setUser(user);
        wishlist = wishlistRepository.save(wishlist);

        return modelMapper.map(wishlist, WishlistDTO.class);
    }


    @Override
    public List<WishlistDTO> fetchAllWishlist() {
        List<Wishlist> wishlists = wishlistRepository.findAll();
        Type listType = new TypeToken<List<WishlistDTO>>(){}.getType();
        List<WishlistDTO> wishlistDTOs = modelMapper.map(wishlists, listType);
        return wishlistDTOs;
    }
    @Override
    public WishlistDTO fetchWishlistById(Long userId) {
        Optional<Wishlist> optionalWishlist = wishlistRepository.findByUserId(userId);
        if (optionalWishlist.isPresent()) {
            Wishlist wishlist = optionalWishlist.get();
            return modelMapper.map(wishlist, WishlistDTO.class);
        } else {
            throw new ResourceNotFoundException("Wishlist", "userId", userId);
        }
    }

    @Override
    public WishlistDTO updateWishlist(Long wishlistId, WishlistDTO wishlistdto) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist", "wishlistId", wishlistId));

        if (wishlistdto != null) {
            wishlist.setUser(modelMapper.map(wishlistdto.getUser(), User.class));
            wishlist.setProduct(modelMapper.map(wishlistdto.getProduct(), Product.class));
            // add other fields to update as needed
        }

        Wishlist updatedWishlist = wishlistRepository.save(wishlist);
        return modelMapper.map(updatedWishlist, WishlistDTO.class);
    }

    @Override
    public void deleteWishlist(Long wishlistId) {
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishlistId);
        if (optionalWishlist.isPresent()) {
            wishlistRepository.delete(optionalWishlist.get());
        } else {
            throw new ResourceNotFoundException("Wishlist", "wishlistId", wishlistId);
        }
    }
}
