package com.ecommerce.ecommerce.service.impl;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.exception.UserNotFoundException;
import com.ecommerce.ecommerce.payLoad.UserDTO;
import com.ecommerce.ecommerce.repository.UserRepository;
import com.ecommerce.ecommerce.service.UserService;
import com.ecommerce.ecommerce.utils.UserExcelExporter;
import com.ecommerce.ecommerce.utils.UserPDFExporter;
import com.ecommerce.ecommerce.utils.UserResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    public UserServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Value("${user.profile.image.upload.path}")
    private String userProfileImageUploadPath;

    @Override
    public Page<UserDTO> findAllUsers(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<User> users = userRepository.findAll(pageRequest);
        return users.map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO, MultipartFile profileImage) {

        // Save user
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles());

        if (userDTO.getProfileImage() != null) {
            try {
                String fileName = UUID.randomUUID().toString();
                String extension = FilenameUtils.getExtension(userDTO.getProfileImage().getOriginalFilename());
                String filePath = "user_profile_image/" + fileName + "." + extension;
                File file = new File(filePath);
                byte[] imageData = userDTO.getProfileImage().getBytes();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(imageData);
                fos.close();
                user.setProfilePicture(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save profile image", e);
            }
        }
        userRepository.save(user);
        return maptoDto(user);
    }

    @Override
    public UserResponse getAllUsers(int pageNo, int pageSize,String sortBy, String sortDir) {
        Sort sort = sortBy.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pagable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> page = userRepository.findAll(pagable);
        List<User> all = page.getContent();
       // List<User> all = userRepository.findAll();
        List<UserDTO> collect = all.stream().map(n -> maptoDto(n)).collect(Collectors.toList());
        UserResponse response= new UserResponse();
        response.setContent(collect);
        response.setLast(page.isLast());
        response.setTotalPage(page.getTotalPages());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setPageNo(page.getNumber());
        return response;
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        userRepository.delete(user);
    }

    //shubra's
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setAddress(userDTO.getAddress());

            if (userDTO.getProfileImage() != null) {
                try {
                    String fileName = UUID.randomUUID().toString();
                    String extension = FilenameUtils.getExtension(userDTO.getProfileImage().getOriginalFilename());
                    String filePath = "user_profile_image/" + fileName + "." + extension;
                    File file = new File(filePath);
                    byte[] imageData = userDTO.getProfileImage().getBytes();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(imageData);
                    fos.close();
                    user.setProfilePicture(filePath);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save profile image", e);
                }
            }
            userRepository.save(user);
            return maptoDto(user);
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public UserExcelExporter getContentForExcel() {
        List<User> all = userRepository.findAll();
        UserExcelExporter excelExporter=new UserExcelExporter(all);
        return excelExporter;
    }

    @Override
    public UserPDFExporter getContentForPdf() {
        List<User> allUsers = userRepository.findAll();
        UserPDFExporter userPDFExporter= new UserPDFExporter(allUsers);
        return userPDFExporter;
    }

    @Override
    public void getContentForCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userRepository.findAll();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"User ID", "E-mail", "First Name", "Last Name", "Address","Phone No","Password"};
        String[] nameMapping = {"id", "email", "firstName", "lastName", "address","phone","password"};
        csvWriter.writeHeader(csvHeader);

        for (User user : listUsers) {
            csvWriter.write(user, nameMapping);
        }

        csvWriter.close();
    }

    private UserDTO maptoDto(User user)
    {
        UserDTO dto= new UserDTO();
        dto.setId(user.getId());
        dto.setProfileImageFileName(user.getProfileImageFileName());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setPassword(user.getPassword());
        dto.setProfilePicture(user.getProfilePicture());
        return dto;
        //return modelMapper.map(user,UserDTO.class);
    }



    }


