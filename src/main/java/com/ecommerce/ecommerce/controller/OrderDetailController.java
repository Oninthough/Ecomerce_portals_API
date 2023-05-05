package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.payLoad.OrderDetailDTO;
import com.ecommerce.ecommerce.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orderDetails")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping()
    public ResponseEntity<OrderDetailDTO> saveOneOrderDetail(@RequestBody OrderDetailDTO orderDetaildto)
    {
        OrderDetailDTO savedOrderDetail = orderDetailService.saveOneOrderDetail(orderDetaildto);
        return ResponseEntity.ok(savedOrderDetail);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        List<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetail();
        return ResponseEntity.ok(orderDetails);
    }
}
