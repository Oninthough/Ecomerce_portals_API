package com.ecommerce.ecommerce.payLoad;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProductImageDTO {
    private Long id;

    @NotBlank(message = "File name is required")
    @Size(max = 255, message = "File name should not exceed 255 characters")
    private String fileName;

    @NotBlank(message = "File type is required")
    @Size(max = 50, message = "File type should not exceed 50 characters")
    private String fileType;

    @NotNull(message = "Data is required")
    private MultipartFile data;

    private ProductDTO product;
    // Add other fields as needed
}
