package com.ecommerce.ecommerce.utils;

import com.ecommerce.ecommerce.payLoad.UserDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private List<UserDTO> content;
    private int pageSize;
    private int pageNo;
    private int totalPage;
    private boolean isLast;
    private long totalElement;
}
