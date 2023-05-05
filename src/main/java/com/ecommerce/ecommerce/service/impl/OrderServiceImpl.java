package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.entities.Order;
import com.ecommerce.ecommerce.entities.OrderDetail;
import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.payLoad.OrderDTO;
import com.ecommerce.ecommerce.payLoad.OrderDetailDTO;
import com.ecommerce.ecommerce.payLoad.UserDTO;
import com.ecommerce.ecommerce.repository.OrderRepository;
import com.ecommerce.ecommerce.repository.ProductRepository;
import com.ecommerce.ecommerce.repository.UserRepository;
import com.ecommerce.ecommerce.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ModelMapper modelMapper;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

/*
    @Override
    public OrderDTO createOrder(OrderDTO orderDto) {
        Order order = new Order();
        order.setUser(userRepository.findById(orderDto.getUser().getId()).orElse(null));
        order.setOrderDate(orderDto.getOrderDate());
        order.setStatus(orderDto.getStatus());
        order.setTotalAmount(orderDto.getTotalAmount());

        List<OrderDetailDTO> orderDetailDtos = orderDto.getOrderDetails();
        List<OrderDetail> orderDetails = new ArrayList<>();
        double totalAmount = 0;
        for (OrderDetailDTO orderDetailDto : orderDetailDtos) {
            Product product = productRepository.findById(orderDetailDto.getProduct().getId()).orElse(null);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(orderDetailDto.getQuantity());
            orderDetail.setPrice(product.getPrice() * orderDetailDto.getQuantity());
            totalAmount += orderDetail.getPrice();
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        OrderDTO savedOrderDto = new OrderDTO();
        savedOrderDto.setOrderId(savedOrder.getOrderId());
        savedOrderDto.setUser(savedOrder.getUser());
        savedOrderDto.setOrderDate(savedOrder.getOrderDate());
        savedOrderDto.setStatus(savedOrder.getStatus());
        savedOrderDto.setTotalAmount(savedOrder.getTotalAmount());
        savedOrderDto.setOrderDetails(orderDto.getOrderDetails());

        return savedOrderDto;
    }

    */
@Override
public OrderDTO createOrder(OrderDTO orderDto) {
    // Map OrderDTO to Order entity
    Order order = modelMapper.map(orderDto, Order.class);

    // Set user from repository
    User user = userRepository.findById(orderDto.getUser().getId()).orElseThrow(() ->
            new ResourceNotFoundException("User", "id", orderDto.getUser().getId()));
    order.setUser(user);

    // Set order details
    List<OrderDetail> orderDetails = new ArrayList<>();
    for (OrderDetailDTO orderDetailDto : orderDto.getOrderDetails()) {
        OrderDetail orderDetail = modelMapper.map(orderDetailDto, OrderDetail.class);
        orderDetail.setOrder(order);
        orderDetail.setProduct(modelMapper.map(orderDetailDto.getProduct(), Product.class));
        orderDetails.add(orderDetail);
    }
    order.setOrderDetails(orderDetails);

    // Save and return the new order
    Order savedOrder = orderRepository.save(order);
    return modelMapper.map(savedOrder, OrderDTO.class);
}


    @Override
    public List<OrderDTO> fetchAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }


    /*
    @Override
    public List<OrderDTO> fetchAllOrders() {
        List<Order> all = orderRepository.findAll();
        all.stream().map(n->mapToDto(n).
        return null;
    }

    public OrderDTO mapToDto(Order order)
    {
        OrderDTO dto = modelMapper.map(order, OrderDTO.class);
        return dto;
    }

     */
}


