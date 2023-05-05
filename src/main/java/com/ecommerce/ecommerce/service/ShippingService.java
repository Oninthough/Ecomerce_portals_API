package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.payLoad.ShippingDTO;

import java.util.List;

public interface ShippingService {
    public ShippingDTO saveShippingStatus(ShippingDTO shippingDTO, Long orderId);
    public void deleteShippingStatus(Long orderId);
    public ShippingDTO updateShippingStatus(ShippingDTO shippingDTO, Long orderId, Long shippingId);
    public List<ShippingDTO> getShippingStatus(Long orderId);
}
