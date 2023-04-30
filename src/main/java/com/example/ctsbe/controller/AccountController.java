package com.example.ctsbe.controller;


import com.example.ctsbe.dto.account.*;
import com.example.ctsbe.dto.project.ProjectInProfileDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.exception.StaffSelfDisableException;
import com.example.ctsbe.exception.ExceptionObject;
import com.example.ctsbe.filter.JwtTokenFilter;
import com.example.ctsbe.mapper.AccountMapper;
import com.example.ctsbe.mapper.ProjectMapper;
import com.example.ctsbe.service.AccountService;
import com.example.ctsbe.service.EmailService;
import com.example.ctsbe.service.StaffProjectService;
import com.example.ctsbe.service.StaffService;
import com.example.ctsbe.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Slf4j
@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffProjectService staffProjectService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private EmailService emailService;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/addAccount")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> addAccount(@Valid @RequestBody AccountAddDTO dto) {
        try {
            ExceptionObject exceptionObject = new ExceptionObject();
            Map<String, String> errorMap = new HashMap<>();
            int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            exceptionObject.setCode(errorCode);
            if (accountService.getAccountByUsername(dto.getUsername()) != null) {
                errorMap.put("username", "Tên đăng nhập này đã tồn tại!");
                exceptionObject.setError(errorMap);
                return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
            } else if (staffService.findStaffByEmail(dto.getStaffAddDTO().getEmail()) != null) {
                errorMap.put("staffAddDTO.email", "Email này đã được đăng kí!");
                exceptionObject.setError(errorMap);
                return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
            } else {
                accountService.addAccount(dto);
            }
            return new ResponseEntity<>("Thêm tài khoản " + dto.getUsername() + " thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateAccount/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable("id") int id, @RequestBody ProfileUpdateDTO dto) {
        try {
            int tokenId = getIdFromToken();
            Account acc = accountService.getAccountById(id);
            if (acc.getRole().getId() != 2) {
                if (tokenId != id) {
                    throw new AccessDeniedException("Bạn không có quyền sửa tài khoản này");
                } else {
                    accountService.updateAccount(id, dto);
                    return new ResponseEntity<>("Chỉnh sửa tài khoản thành công.", HttpStatus.OK);
                }
            } else {
                accountService.updateAccount(id, dto);
                return new ResponseEntity<>("Chỉnh sửa tài khoản thành công.", HttpStatus.OK);
            }
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable int id, @RequestBody @Valid AccountUpdateDTO dto) {
        try {
            int tokenId = getIdFromToken();
            ExceptionObject exceptionObject = new ExceptionObject();
            Map<String, String> errorMap = new HashMap<>();
            int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            exceptionObject.setCode(errorCode);
            Account existedAccount = accountService.getAccountById(id);
            if (tokenId != id) {
                errorMap.put("exception", "Bạn không có quyền đổi mật khẩu");
                exceptionObject.setError(errorMap);
                return new ResponseEntity<>(exceptionObject, HttpStatus.FORBIDDEN);
            } else if (!passwordEncoder.matches(dto.getPassword(), existedAccount.getPassword())) {
                errorMap.put("password", "Mật khẩu không đúng!");
                exceptionObject.setError(errorMap);
                return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
            } else if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
                errorMap.put("confirmNewPassword", "Mật khẩu không khớp.Vui lòng nhập lại!");
                exceptionObject.setError(errorMap);
                return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
            } else {
                accountService.resetPassword(existedAccount, dto.getNewPassword());
                return new ResponseEntity<>("Đổi mật khẩu thành công", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sendForgotPassword")
    public ResponseEntity<?> sendForgotPassword(@RequestParam String email) {
        try {
            if (accountService.getAccountByEmail(email) == null) {
                throw new Exception("Email này chưa được đăng kí. Vui lòng thử lại!");
            } else {
                emailService.sendEmail(email);
                return new ResponseEntity<>("Email đổi mật khẩu đã được gửi đến mail của bạn. Vui lòng kiểm tra mail.", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/changeEnableAccount/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> changeEnableAccount(@PathVariable("id") int id) {
        try {
            int currentAdmin = getIdFromToken();
            if (id == currentAdmin) {
                throw new StaffSelfDisableException("Không thể khoá tài khoản của chính bản thân!");
            }
            accountService.changeEnableAccount(id);
            return new ResponseEntity<>("Chỉnh sửa trạng thái tài khoản thành công.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/getAllAccount")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Map<String, Object>> getAllAccount(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "3") int size
            , @RequestParam(required = false) String username
            , @RequestParam(defaultValue = "2") int enable) {
        try {
            List<Account> list = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Account> accountPage;
            if (username == null) {
                if (enable != 1 && enable != 0) {
                    accountPage = accountService.getAll(pageable);
                } else {
                    accountPage = accountService.getAccountByEnable((byte) enable, pageable);
                }
            } else {
                if (enable == 2) {
                    accountPage = accountService.getAccountByUsernameContain(username, pageable);
                } else {
                    accountPage = accountService.getListAccount(username, (byte) enable, pageable);
                }
            }
            list = accountPage.getContent();
            List<AccountDTO> listDto = list.stream().
                    map(AccountMapper::convertEntityToDTO).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            response.put("currentPage", accountPage.getNumber());
            response.put("allProducts", accountPage.getTotalElements());
            response.put("allPages", accountPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getProfile/{username}")
    public ResponseEntity<?> getProfile(@PathVariable("username") String username) {
        try {
            int id = getIdFromToken();
            Account acc = accountService.getAccountById(id);
            Account account = accountService.getAccountByUsername(username);
            if (acc.getRole().getId() == 2 || account.getId() == id) {
                ProfileDTO dto = AccountMapper.convertAccountToProfile(account);
                List<Project> list = staffProjectService.getListProjectInProfile(account.getStaff().getId());
                List<ProjectInProfileDTO> listDto = list.stream().
                        map(ProjectMapper::convertProjectToProjectProfileDto).collect(Collectors.toList());
                dto.setListProject(listDto);
                return new ResponseEntity<>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Bạn không có quyền truy cập", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public int getIdFromToken() {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
        }
        int id = jwtTokenUtil.getIdFromToken(jwtToken);
        return id;
    }


}
