package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.payLoad.ReviewDTO;

import java.util.List;

public interface ReviewService {
    public ReviewDTO createReview(Long productId, ReviewDTO reviewDTO);
    public ReviewDTO updateReview(Long productId, ReviewDTO reviewDTO);
    public List<ReviewDTO> getAllReview(Long productId);
    public void deleteReviewById(Long userId, Long productId);
}
