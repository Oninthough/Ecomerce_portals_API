package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.payLoad.UserDTO;
import com.ecommerce.ecommerce.utils.UserExcelExporter;
import com.ecommerce.ecommerce.utils.UserPDFExporter;
import com.ecommerce.ecommerce.utils.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;

public interface UserService {
    Page<UserDTO> findAllUsers(int pageNumber, int pageSize);

    UserDTO createUser(UserDTO userDTO, MultipartFile profileImage);

    UserResponse getAllUsers(int pageNo,int pageSize, String sortBy, String sortDir);

    void deleteUserById(Long id);
    public UserDTO updateUser(Long id, UserDTO userDTO);
   // public StringReader downloadUsersAsExcel();

    public UserExcelExporter getContentForExcel();

    public UserPDFExporter getContentForPdf();

    public void getContentForCsv(HttpServletResponse response) throws IOException;
}
