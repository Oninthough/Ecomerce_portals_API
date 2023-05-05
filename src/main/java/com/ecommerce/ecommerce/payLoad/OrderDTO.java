package com.ecommerce.ecommerce.payLoad;
import com.ecommerce.ecommerce.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private Long orderId;

    @NotNull
    private User user;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    @NotNull
    private String status;

    @NotNull
    private double totalAmount;

    @JsonIgnore
    private List<OrderDetailDTO> orderDetails = new ArrayList<>(); // initialize the list

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
