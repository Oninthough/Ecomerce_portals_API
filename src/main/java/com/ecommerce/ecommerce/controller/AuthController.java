package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entities.Role;
import com.ecommerce.ecommerce.payLoad.JWTAuthResponse;
import com.ecommerce.ecommerce.payLoad.LoginDto;
import com.ecommerce.ecommerce.payLoad.UserDTO;
import com.ecommerce.ecommerce.repository.RoleRepository;
import com.ecommerce.ecommerce.repository.UserRepository;
import com.ecommerce.ecommerce.security.JwtTokenProvider;
import com.ecommerce.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /*
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getFirstNameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.",
                HttpStatus.OK);
    }

     */
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getFirstNameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        return new ResponseEntity<>(new JWTAuthResponse(token), HttpStatus.OK);
    }




    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@RequestParam("firstName") String firstName,
                                              @RequestParam("lastName") String lastName,
                                              @RequestParam("email") String email,
                                              @RequestParam("phone") String phone,
                                              @RequestParam("address") String address,
                                              @RequestParam("password") String password,

                                              @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
        if(userRepository.existsByFirstName(firstName)){
            return new ResponseEntity<>("Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(email)){
            return new ResponseEntity<>("Email is already taken!",
                    HttpStatus.BAD_REQUEST);
        }


        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setPhone(phone);
        userDTO.setAddress(address);
        userDTO.setPassword(passwordEncoder.encode(password));
        userDTO.setProfileImage(profileImage);
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        userDTO.setRoles(Collections.singleton(roles));
        UserDTO createdUser = userService.createUser(userDTO, profileImage);

        return new ResponseEntity<>("User registered successfully" + createdUser,
                HttpStatus.OK);
    }

}
