package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.exception.InvalidInputException;
import com.ecommerce.ecommerce.payLoad.UserDTO;
import com.ecommerce.ecommerce.service.UserService;
import com.ecommerce.ecommerce.service.impl.UserServiceImpl;
import com.ecommerce.ecommerce.utils.UserExcelExporter;
import com.ecommerce.ecommerce.utils.UserPDFExporter;
import com.ecommerce.ecommerce.utils.UserResponse;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //http://localhost:8080/users/find?PageNO=0&PageSize=5&SortBy=lastName&SortDir=desc
    @GetMapping("/find")
    public ResponseEntity<UserResponse> getUsers(
            @RequestParam(value = "PageNO", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "PageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "SortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "SortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        UserResponse response = userService.getAllUsers(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestParam("firstName") String firstName,
                                              @RequestParam("lastName") String lastName,
                                              @RequestParam("email") String email,
                                              @RequestParam("phone") String phone,
                                              @RequestParam("address") String address,
                                              @RequestParam("password") String password,

                                              @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setPhone(phone);
        userDTO.setAddress(address);
        userDTO.setPassword(password);
        userDTO.setProfileImage(profileImage);


        UserDTO createdUser = userService.createUser(userDTO, profileImage);

        return ResponseEntity.ok(createdUser);
    }

     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("Record deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id,
                                              @RequestParam(value = "firstName", required = false, defaultValue = "John") String firstName,
                                              @RequestParam("lastName") String lastName,
                                              @RequestParam("email") String email,
                                              @RequestParam("phone") String phone,
                                              @RequestParam("address") String address,
                                              @RequestParam("password") String password,
                                              @RequestParam(name = "profileImage", required = false) MultipartFile profileImage
                                              )
    {
        UserDTO userDTO= new UserDTO();
        userDTO.setId(id);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setPhone(phone);
        userDTO.setAddress(address);
        userDTO.setPassword(password);
        userDTO.setProfileImage(profileImage);
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    //http://localhost:9090/users/export/exel
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        //List<User> listUsers = service.listAll();
        //UserExcelExporter excelExporter=new UserExcelExporter(listUsers);
        UserExcelExporter excelExporter = userService.getContentForExcel();

        excelExporter.export(response);
    }

    //http://localhost:9090/users/export/pdf
    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

      //  List<User> listUsers = service.listAll();
       // UserPDFExporter exporter = new UserPDFExporter(listUsers);

        UserPDFExporter exporter=userService.getContentForPdf();
        exporter.export(response);

    }

    //Download CSV file
    @GetMapping("/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
         userService.getContentForCsv(response);


    }


}