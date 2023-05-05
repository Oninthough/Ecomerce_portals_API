package com.ecommerce.ecommerce.controller;
import com.ecommerce.ecommerce.payLoad.ReviewDTO;
import com.ecommerce.ecommerce.service.ReviewService;
import com.ecommerce.ecommerce.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ReviewDTO> createReview(@PathVariable Long productId, @RequestBody ReviewDTO reviewDTO) {

        ReviewDTO review = reviewService.createReview(productId, reviewDTO);
        return new ResponseEntity<>(review,HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long productId, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO reviewDTO1 = reviewService.updateReview(productId, reviewDTO);
        return new ResponseEntity<>(reviewDTO1, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ReviewDTO>> getAllReview(@PathVariable Long productId) {
        List<ReviewDTO> allReview = reviewService.getAllReview(productId);
        return new ResponseEntity<>(allReview,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<String> deleteReviewById(@PathVariable Long userId, @PathVariable Long productId) {
        reviewService.deleteReviewById(userId, productId);
        return ResponseEntity.ok("Review deleted");
    }
}
