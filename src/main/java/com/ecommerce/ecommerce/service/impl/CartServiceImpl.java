package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.entities.Cart;
import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.exception.BadRequestException;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.payLoad.CartDTO;
import com.ecommerce.ecommerce.payLoad.CartItemDTO;
import com.ecommerce.ecommerce.repository.CartRepository;
import com.ecommerce.ecommerce.repository.ProductRepository;
import com.ecommerce.ecommerce.repository.UserRepository;
import com.ecommerce.ecommerce.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CartDTO createCartForUser(Long userId, CartDTO cartDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id "," not found",userId));

        Cart cart = modelMapper.map(cartDTO, Cart.class);
        cart.setUser(user);

        Cart savedCart = cartRepository.save(cart);
        return modelMapper.map(savedCart, CartDTO.class);
    }

    @Override
    @Transactional
    public void deleteCartById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id "," not found",userId));
        List<Cart> cart = cartRepository.findByUser(user);
        if (cart != null) {
            cartRepository.deleteAll(cart);
        }
    }

    @Override
    @Transactional
    public void updateCartById(Long userId, Long cartId, CartDTO cartDTO) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent() && optionalCart.get().getUser().getId().equals(userId)) {
            Cart cart = optionalCart.get();
            cart.setQuantity(cartDTO.getQuantity());
            // update other fields as needed

            cartRepository.save(cart);
        } else {
            throw new ResourceNotFoundException("User with id "," not found",userId);
        }
    }



    /*
    @Override
    public CartDTO fetchCartById(Long userId) {
        User user = new User();
        user.setId(userId);

        List<Cart> carts = cartRepository.findByUser(user);

        if (carts == null || carts.isEmpty()) {
            throw new NotFoundException("Cart not found for user id: " + userId);
        }

        // We assume there's only one cart per user for simplicity
        Cart cart = carts.get(0);

        return modelMapper.map(cart, CartDTO.class);
    }

     */
    @Override
    public List<CartDTO> fetchCartById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id "," not found",userId));

        List<Cart> carts = cartRepository.findByUser(user);

        if (carts.isEmpty()) {
            throw new ResourceNotFoundException("Cart not found for user with id ","",userId);
        }
        return carts.stream().map(n->modelMapper.map(n,CartDTO.class)).collect(Collectors.toList());

        // In case there are multiple carts, return the first one
       // Cart cart = carts.get(0);

        //return modelMapper.map(cart, CartDTO.class);
    }
/*
    private CartDTO convertToCartDTO(Cart cart) {
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.setUser(modelMapper.map(cart.getUser(), UserDTO.class));
        List<CartItemDTO> cartItems = cart.getCartItems().stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemDTO.class))
                .collect(Collectors.toList());
        cartDTO.setCartItems(cartItems);
        return cartDTO;
    }
*/

    // other methods...
}
