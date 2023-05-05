package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.payLoad.PaymentDTO;
import com.ecommerce.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{userId}")
    public ResponseEntity<PaymentDTO> savePaymentDetails(@RequestBody PaymentDTO paymentDto, @PathVariable Long userId) {
        PaymentDTO savedPayment = paymentService.savePaymentDetails(paymentDto, userId);
        return ResponseEntity.ok(savedPayment);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.ok("Payment Details is deleted");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PaymentDTO>> fetchAllPayment(@PathVariable Long userId) {
        List<PaymentDTO> paymentDtoList = paymentService.fetchAllPayment(userId);
        return ResponseEntity.ok(paymentDtoList);
    }
    // Other methods...
}
