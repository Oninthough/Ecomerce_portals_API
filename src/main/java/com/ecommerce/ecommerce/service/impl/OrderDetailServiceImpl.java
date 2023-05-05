package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.entities.OrderDetail;
import com.ecommerce.ecommerce.payLoad.OrderDetailDTO;
import com.ecommerce.ecommerce.repository.OrderDetailRepository;
import com.ecommerce.ecommerce.service.OrderDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private final ModelMapper modelMapper;
    private final OrderDetailRepository orderDetailRepository;
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, ModelMapper modelMapper) {
        this.orderDetailRepository = orderDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDetailDTO saveOneOrderDetail(OrderDetailDTO orderDetaildto) {
        OrderDetail orderDetail = modelMapper.map(orderDetaildto, OrderDetail.class);
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        return modelMapper.map(savedOrderDetail, OrderDetailDTO.class);
    }

    @Override
    public List<OrderDetailDTO> getAllOrderDetail() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        return orderDetails.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class))
                .collect(Collectors.toList());
    }

}
