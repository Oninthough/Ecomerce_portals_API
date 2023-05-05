package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.entities.Order;
import com.ecommerce.ecommerce.entities.Payment;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.exception.UserNotFoundException;
import com.ecommerce.ecommerce.payLoad.OrderDTO;
import com.ecommerce.ecommerce.payLoad.PaymentDTO;
import com.ecommerce.ecommerce.repository.OrderRepository;
import com.ecommerce.ecommerce.repository.PaymentRepository;
import com.ecommerce.ecommerce.repository.UserRepository;
import com.ecommerce.ecommerce.service.PaymentService;
import com.ecommerce.ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PaymentDTO savePaymentDetails(PaymentDTO paymentDto, Long userId) {
        // Find the user by ID
        User user = userRepository.findById(userId).get();

        // Convert PaymentDTO to Payment entity & setting the order
        Payment payment = modelMapper.map(paymentDto, Payment.class);
        Order order=orderRepository.findById(paymentDto.getOrder().getOrderId()).orElseThrow(
                ()-> new UserNotFoundException("Invalid order id is given")
        );
        payment.setOrder(order);

        // Set the user in the Payment entity
        payment.getOrder().setUser(user);

        // Save the Payment entity
        Payment savedPayment = paymentRepository.save(payment);

        // Convert the saved Payment entity to PaymentDTO and return it
        return modelMapper.map(savedPayment, PaymentDTO.class);
    }

    @Override
    public void deletePayment(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }
    // Other methods...
    @Override
    public List<PaymentDTO> fetchAllPayment(Long userId) {
        List<Payment> paymentList = paymentRepository.findPaymentsByUserId(userId);
        List<PaymentDTO> paymentDtoList = new ArrayList<>();

        for (Payment payment : paymentList) {
            PaymentDTO paymentDto = new PaymentDTO();
            paymentDto.setPaymentId(payment.getPaymentId());
            paymentDto.setPaymentMethod(payment.getPaymentMethod());
            paymentDto.setCardHolderName(payment.getCardHolderName());
            paymentDto.setCardNumber(payment.getCardNumber());
            paymentDto.setExpiryDate(payment.getExpiryDate());
            paymentDto.setCvv(payment.getCvv());

            OrderDTO orderDto = new OrderDTO();
            OrderDTO orderDTO = modelMapper.map(payment.getOrder(), OrderDTO.class);
           /* orderDto.setOrderId(payment.getOrder().getOrderId());
            orderDto.setOrderDate(payment.getOrder().getOrderDate());
            orderDto.setTotalAmount(payment.getOrder().getTotalAmount());
            orderDto.setUser(payment.getOrder().getUser());
            orderDto.setStatus(payment.getOrder().getStatus());

            */

            paymentDto.setOrder(orderDto);

            paymentDtoList.add(paymentDto);
        }

        return paymentDtoList;
    }



}
