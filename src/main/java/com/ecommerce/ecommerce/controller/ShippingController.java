package com.ecommerce.ecommerce.controller;
import com.ecommerce.ecommerce.entities.Shipping;
import com.ecommerce.ecommerce.payLoad.ShippingDTO;
import com.ecommerce.ecommerce.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    private final ShippingService shippingService;

    @Autowired
    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{orderId}")
    public ResponseEntity<ShippingDTO> saveShippingStatus(@RequestBody ShippingDTO shippingDTO,
                                                          @PathVariable Long orderId) {
        ShippingDTO shippingDTO1 = shippingService.saveShippingStatus(shippingDTO, orderId);
        return new ResponseEntity<>(shippingDTO1, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteShippingStatus(@PathVariable Long orderId) {
        shippingService.deleteShippingStatus(orderId);
        return new ResponseEntity<>("Shipping is deleted",HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}/{shippingId}")
    public ResponseEntity<ShippingDTO> updateShippingStatus(@RequestBody ShippingDTO shippingDTO,
                                                            @PathVariable Long orderId,
                                                            @PathVariable Long shippingId) {
        ShippingDTO dto = shippingService.updateShippingStatus(shippingDTO, orderId, shippingId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<ShippingDTO>> getShippingStatus(@PathVariable Long orderId) {
        List<ShippingDTO> shippingStatus = shippingService.getShippingStatus(orderId);
        return new ResponseEntity<>(shippingStatus, HttpStatus.OK);
    }

}

