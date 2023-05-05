package com.ecommerce.ecommerce.payLoad;
import com.ecommerce.ecommerce.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;


    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is mandatory")
    private String email;

    private String profilePicture;
    private MultipartFile profileImage;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    private String address;

    @Pattern(regexp = "^\\+91[0-9]{10}$", message = "Please provide a valid Indian phone number with country code")
    private String phone;
    private String profileImageFileName;

    public void setProfileImageFileName(String profileImageFileName) {
        this.profileImageFileName = profileImageFileName;
    }
    @JsonIgnore
    private List<OrderDTO> orders;

    @JsonIgnore
    private List<CartDTO> carts;
    private List<WishlistDTO> wishlists;
    private Set<Role> roles;
}
