package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.payLoad.OrderDTO;
import com.ecommerce.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createNewOrder(@RequestBody OrderDTO orderDTO)
    {
        OrderDTO order = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<List<OrderDTO>> fetchAllOrders()
    {
        List<OrderDTO> orderDto= orderService.fetchAllOrders();
        return ResponseEntity.ok(orderDto);

    }
}
