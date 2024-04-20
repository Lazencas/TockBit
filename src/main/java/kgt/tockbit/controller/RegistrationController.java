package kgt.tockbit.controller;

import kgt.tockbit.entity.User;
import kgt.tockbit.service.EmailService;
import kgt.tockbit.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final EmailService emailService;

    public RegistrationController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String email, @RequestParam String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // 비밀번호는 해싱하여 저장해야 합니다.
        user.setEmailverified(false);
        userService.save(user);
        String token = UUID.randomUUID().toString();
        // token을 데이터베이스에 저장하고, 이메일 인증 시 사용합니다.

        String message = String.format(
                "회원가입을 완료하려면 다음 토큰을 사용하여 인증 API를 호출하세요: %s",
                token
        );
            emailService.sendEmail(user.getEmail(), "회원가입 인증", message);

        return ResponseEntity.ok(token);
        }
    }
