package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.payLoad.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {

    public OrderDetailDTO saveOneOrderDetail(OrderDetailDTO orderDetaildto);
    public List<OrderDetailDTO> getAllOrderDetail();
}
