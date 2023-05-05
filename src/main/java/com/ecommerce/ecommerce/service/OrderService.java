package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.entities.Order;
import com.ecommerce.ecommerce.entities.OrderDetail;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.payLoad.OrderDTO;
import com.ecommerce.ecommerce.payLoad.OrderDetailDTO;

import java.util.List;

public interface OrderService {
    public OrderDTO createOrder(OrderDTO orderDto);

    public List<OrderDTO> fetchAllOrders();
}
