package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.entities.Order;
import com.ecommerce.ecommerce.entities.Shipping;
import com.ecommerce.ecommerce.payLoad.OrderDTO;
import com.ecommerce.ecommerce.payLoad.ShippingDTO;
import com.ecommerce.ecommerce.repository.OrderRepository;
import com.ecommerce.ecommerce.repository.ShippingRepository;
import com.ecommerce.ecommerce.service.ShippingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ShippingDTO saveShippingStatus(ShippingDTO shippingDTO, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order not found with id: " + orderId));

        Shipping shipping = modelMapper.map(shippingDTO, Shipping.class);
        shipping.setOrder(order);
        shipping = shippingRepository.save(shipping);

        return modelMapper.map(shipping, ShippingDTO.class);
    }

    //deleteShippingStatus should be by shippingId otherwise all will be deleted at once
    @Override
    public void deleteShippingStatus(Long orderId) {
        List<Optional<Shipping>> optionalShipping = shippingRepository.findByOrderOrderId(orderId);
        List<Shipping> shippingList = optionalShipping.stream().map(n -> n.get()).collect(Collectors.toList());

        if (shippingList.isEmpty()) {
            // No shipping details found for the given order ID
            throw new EntityNotFoundException("No shipping details found for order ID: " + orderId);
        }
        for (Shipping shipping : shippingList) {
            shippingRepository.delete(shipping);
        }
    }
 /*
    public void deleteShippingStatus(Long orderId) {
        List<Shipping> shippings = shippingRepository.findByOrderId(orderId).get().orElseThrow(
                () -> new EntityNotFoundException("Shipping status not found for order id: " + orderId));
        shippingRepository.deleteAll(shippings);
    }


    @Override
    public List<ShippingDTO> updateShippingStatus(ShippingDTO shippingDTO, Long orderId) {
        // Find all shippings for the given order
        List<Optional<Shipping>> optionalShipping = shippingRepository.findByOrderId(orderId);
        List<Shipping> shippingList = optionalShipping.stream().map(n -> n.get()).collect(Collectors.toList());
        List<Shipping> savedShippings= new ArrayList<>();
        // Check if any shippings are found
        if (shippingList.isEmpty()) {
            throw new EntityNotFoundException("No shipping found for order with id " + orderId);
        }
        for (Shipping shipping : shippingList) {
            shipping.setShippingMethod(shippingDTO.getShippingMethod());
            shipping.setAddress(shippingDTO.getAddress());
            shipping.setShippingCost(shippingDTO.getShippingCost());
            Shipping savedShipping = shippingRepository.save(shipping);
            savedShippings.add(savedShipping);
        }

        // Convert the updated shipping to a ShippingDTO and return it
        //return modelMapper.map(shipping, ShippingDTO.class);
    }

  */

    @Override
    public ShippingDTO updateShippingStatus(ShippingDTO shippingDTO, Long orderId, Long shippingId) {
        Optional<Shipping> optionalShipping = shippingRepository.findByOrderOrderIdAndShippingId(orderId, shippingId);
        if (optionalShipping.isPresent()) {
            Shipping shipping = optionalShipping.get();
            shipping.setShippingMethod(shippingDTO.getShippingMethod());
            shipping.setAddress(shippingDTO.getAddress());
            shipping.setShippingCost(shippingDTO.getShippingCost());
            shippingRepository.save(shipping);
            return modelMapper.map(shipping, ShippingDTO.class);
        } else {
            throw new RuntimeException("Shipping not found for order id " + orderId + " and shipping id " + shippingId);
        }
    }

    @Override
    public List<ShippingDTO> getShippingStatus(Long orderId) {
        List<Optional<Shipping>> optionalShipping = shippingRepository.findByOrderOrderId(orderId);
        List<Shipping> shippingList = optionalShipping.stream().map(n -> n.get()).collect(Collectors.toList());
        List<ShippingDTO> shippingDTOList = new ArrayList<>();
        for (Shipping shipping : shippingList) {
            ShippingDTO shippingDTO = new ShippingDTO();
            shippingDTO.setShippingId(shipping.getShippingId());
            shippingDTO.setShippingMethod(shipping.getShippingMethod());
            shippingDTO.setAddress(shipping.getAddress());
            shippingDTO.setShippingCost(shipping.getShippingCost());
            shippingDTO.setOrder(modelMapper.map(shipping.getOrder(), OrderDTO.class));
            shippingDTOList.add(shippingDTO);
        }
        return shippingDTOList;
    }



}
