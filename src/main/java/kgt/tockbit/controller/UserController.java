package kgt.tockbit.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import kgt.tockbit.domain.User;
import kgt.tockbit.dto.loginRequestDto;
import kgt.tockbit.jwt.JwtUtil;
import kgt.tockbit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
//@RequestMapping("/api")
public class UserController {


    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String siteURL = "localhost:8080";
    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/auth/register")
    public String createForm(){
        return "users/createUserForm";
    }

    @PostMapping("/auth/register")
    public String create(UserForm form, @RequestParam("image")MultipartFile multipartFile) throws IOException {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setName(form.getName());
        user.setPassword(form.getPassword());
        user.setGreet(form.getGreet());
        // 이미지를 Base64로 인코딩하여 문자열로 변환
        byte[] imageBytes = multipartFile.getBytes();
        String imageString = Base64.getEncoder().encodeToString(imageBytes);
        user.setImage(imageString);
        userService.join(user);
        return "redirect:/";
    }

    @PostMapping("/profile")
    public String update(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue ,UserForm form,@RequestParam("image")MultipartFile multipartFile) throws IOException {
        String token = jwtUtil.substringToken(tokenValue);
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("프로필 토큰 에러");
        }
        Claims info = jwtUtil.getUserInfoFromToken(token);
        String email = info.getSubject();
        User user = userService.findOne(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        System.out.println("이거 찍힘?"+ form.getName());
        if (form.getPassword() != null && !form.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(form.getPassword()));
        }
        if (form.getName() != null && !form.getName().isEmpty()){
            user.setName(form.getName());
        }
        if (form.getGreet() != null && !form.getGreet().isEmpty()){
            user.setGreet(form.getGreet());
        }
        if (!multipartFile.isEmpty()){            // 이미지 파일을 byte 배열로 변환
            byte[] imageBytes = multipartFile.getBytes();
            user.setImage(Base64.getEncoder().encodeToString(imageBytes));
        }
        userService.updateUser(user);
        return "users/home";
    }



    @GetMapping("/users")
    public String list(Model model){
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "users/userList";
    }


    @GetMapping("/auth/login")
    public String loginPage() {
        return "users/login";
    }

    @PostMapping("/auth/login")
    public String login(loginRequestDto requestDto, HttpServletResponse res){
        try {
            userService.login(requestDto, res);
        } catch (Exception e) {
            return "redirect:/auth/login?error";
        }
        return "users/home";

    }



    public String bringme_email_jwt(String tokenValue){
        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);
        // 토큰 검증
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }
        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 email
        String email = info.getSubject();
        return  email;
    }



    @GetMapping("/auth/logout")
    public String logout(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue, HttpServletResponse response) {
        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);
        // 토큰 검증
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }
        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 email
        String email = info.getSubject();
        userService.logout(email);
        jwtUtil.clearCookie(response,"Authorization");
        return "users/login";
    }


    @GetMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestParam String toEmail){
        //JWT생성
        String token = jwtUtil.createToken(toEmail);
//        token = jwtUtil.substringToken(token);
        //이메일에 포함될 링크 생성
        String comfirmURI = siteURL + "/confirm?token=" + token;
        //이메일 보내기
        userService.sendToEmail(toEmail,comfirmURI);
        return ResponseEntity.ok("이메일 전송 성공!");
    }

    @GetMapping("confirm")
    public String confirm(@RequestParam("token") String tokenValue) {
        //JWT토큰 자르기
        String token = jwtUtil.substringToken(tokenValue);
        //토큰검증
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("토큰오류");
        }
        //토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        //사용자 email
        String email = info.getSubject();
        //사용자 활성화
        String result = "";
        result = userService.verified(email);

        return result;
    }






}