package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.entities.Review;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.payLoad.ReviewDTO;
import com.ecommerce.ecommerce.repository.ProductRepository;
import com.ecommerce.ecommerce.repository.ReviewRepository;
import com.ecommerce.ecommerce.repository.UserRepository;
import com.ecommerce.ecommerce.service.ReviewService;
import com.ecommerce.ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReviewDTO createReview(Long productId, ReviewDTO reviewDTO) {
        // Retrieve the product from the database
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new EntityNotFoundException("Product not found with id: " + productId);
        }
        Product product = optionalProduct.get();

        // Retrieve the user from the database
        Long userId = reviewDTO.getUser().getId();
        User user = userRepository.findById(userId).get();

        // Create a new Review entity and set the properties
        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setProduct(product);
        review.setUser(user);

        // Save the review to the database
        Review savedReview = reviewRepository.save(review);

        // Map the saved review back to a DTO and return it
        ReviewDTO savedReviewDTO = modelMapper.map(savedReview, ReviewDTO.class);
        return savedReviewDTO;
    }

    @Override
    public ReviewDTO updateReview(Long productId, ReviewDTO reviewDTO) {
        Long userId= reviewDTO.getUser().getId();
        Optional<Review> reviewOptional = reviewRepository.findByProductProductIdAndUserId(productId, userId);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            review.setComment(reviewDTO.getComment());
            review.setRating(reviewDTO.getRating());
            Review re = reviewRepository.save(review);
            return modelMapper.map(re, ReviewDTO.class);
        } else {
            throw new UnsupportedOperationException("Review not found");
        }
    }

    @Override
    public List<ReviewDTO> getAllReview(Long productId) {
        List<Review> reviewList = reviewRepository.findAllByProductProductId(productId);
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        //List<ReviewDTO> dtos = reviewList.stream().map(n -> modelMapper.map(n, ReviewDTO.class)).collect(Collectors.toList());

        for (Review review : reviewList) {
            ReviewDTO reviewDTO = modelMapper.map(review,ReviewDTO.class);
            reviewDTOList.add(reviewDTO);
        }

        return reviewDTOList;
    }

    @Override
    public void deleteReviewById(Long userId, Long productId) {
        Optional<Review> optionalReview = reviewRepository.findByProductProductIdAndUserId(productId, userId);
        if (optionalReview.isPresent()) {
            reviewRepository.delete(optionalReview.get());
        } else {
            throw new UnsupportedOperationException("Review not found");
        }
    }

    // other methods...
}

