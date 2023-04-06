package com.example.ctsbe.controller;


import com.example.ctsbe.dto.account.*;
import com.example.ctsbe.dto.project.ProjectInProfileDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.entity.Project;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/accounts")
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
    public ResponseEntity<?> addAccount(@Valid @RequestBody AccountAddDTO dto) {
        try {
            if (accountService.getAccountByUsername(dto.getUsername()) != null) {
                throw new Exception("Tên đăng nhập này đã tồn tại!");
            } else if (staffService.findStaffByEmail(dto.getStaffAddDTO().getEmail()) != null) {
                throw new Exception("Email này đã được đăng kí!");
            } else {
                accountService.addAccount(dto);
            }
            return new ResponseEntity<>("Add account " + dto.getUsername() + " successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateAccount/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable("id") int id, @RequestBody ProfileUpdateDTO dto) {
        try {
            int tokenId = getIdFromToken();
            if (tokenId != id) throw new AccessDeniedException("You are not authorized to access this resource");
            else accountService.updateAccount(id, dto);
            return new ResponseEntity<>("Update account successfully!", HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable int id,@RequestBody @Valid AccountUpdateDTO dto) {
        try {
            Account existedAccount = accountService.getAccountById(id);
            if(!passwordEncoder.matches(dto.getPassword(),existedAccount.getPassword())){
                return new ResponseEntity<>("Mật khẩu không đúng!",HttpStatus.BAD_REQUEST);
            }else if(!dto.getNewPassword().equals(dto.getConfirmNewPassword())){
                return new ResponseEntity<>("Mật khẩu không khớp.Vui lòng nhập lại!",HttpStatus.BAD_REQUEST);
            }
            else {
                accountService.resetPassword(existedAccount,dto.getNewPassword());
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
    public ResponseEntity<?> changeEnableAccount(@PathVariable("id") int id) {
        try {
            accountService.changeEnableAccount(id);
            return new ResponseEntity<>("Update enable status successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getAllAccount")
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
            //int id = getIdFromToken();
            Account account = accountService.getAccountByUsername(username);
            ProfileDTO dto = AccountMapper.convertAccountToProfile(account);
            List<Project> list = staffProjectService.getListProjectInProfile(account.getStaff().getId());
            List<ProjectInProfileDTO> listDto = list.stream().
                    map(ProjectMapper::convertProjectToProjectProfileDto).collect(Collectors.toList());
            dto.setListProject(listDto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
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
