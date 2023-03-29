package com.example.ctsbe.service;

import com.example.ctsbe.entity.Account;
import com.example.ctsbe.repository.AccountRepository;
import com.example.ctsbe.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public void sendEmail(String to) {
        StringUtil stringUtil = new StringUtil();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String randomPass = stringUtil.randomString();
        Account existedAccount = accountRepository.getAccByEmail(to);
        existedAccount.setPassword(passwordEncoder.encode(randomPass));
        existedAccount.setLastUpdated(Instant.now());
        accountRepository.save(existedAccount);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("hunglmhe153708@fpt.edu.vn");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("Đây là email đổi mật khẩu của bạn!");
        simpleMailMessage.setText("Đây là mật khẩu mới của bạn. " +
                "Tuyệt đối không được chia sẻ cho bất kì ai, " +
                "bạn nên đổi lại mật khẩu ngay sau khi nhận được email này! " +
                "Mật khẩu mới của bạn là: "+randomPass);
        mailSender.send(simpleMailMessage);
    }
}
