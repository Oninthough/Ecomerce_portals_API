package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.payLoad.PaymentDTO;

import java.util.List;

public interface PaymentService {
    public PaymentDTO savePaymentDetails(PaymentDTO paymentDto, Long userId);
    public void deletePayment(Long paymentId);

    public List<PaymentDTO> fetchAllPayment(Long userId);
}
