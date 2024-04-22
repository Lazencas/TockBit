package kgt.tockbit.controller;

import io.jsonwebtoken.Claims;
import jakarta.mail.Multipart;
import kgt.tockbit.domain.User;
import kgt.tockbit.dto.loginRequestDto;
import kgt.tockbit.jwt.JwtUtil;
import kgt.tockbit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import java.util.List;

@Controller
//@RequestMapping("/api")
public class UserController {


    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final UserService userService;

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




    @GetMapping("/create-cookie")
    public String createCookie(HttpServletResponse res) {
        addCookie("Robbie Auth", res);
        return "createCookie";
    }

    @ResponseBody
    @GetMapping("/get-cookie")
    public String getCookie(@CookieValue(AUTHORIZATION_HEADER) String value) {
        System.out.println("value = " + value);

        return "getCookie : " + value;
    }

    @GetMapping("/create-jwt")
    public String createJwt(HttpServletResponse res) {
        // Jwt 생성
        String token = jwtUtil.createToken("Robbie");

        // Jwt 쿠키 저장
        jwtUtil.addJwtToCookie(token, res);

        return "createJwt : " + token;
    }
    @ResponseBody
    @GetMapping("/get-jwt")
    public String getJwt(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);

        // 토큰 검증
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }

        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 username
        String username = info.getSubject();
        System.out.println("username = " + username);

        return "getJwt : " + username;
    }

    public static void addCookie(String cookieValue, HttpServletResponse res) {
        try {
            cookieValue = URLEncoder.encode(cookieValue, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, cookieValue); // Name-Value
            cookie.setPath("/");
            cookie.setMaxAge(30 * 60);

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestParam String toEmail){
        userService.sendToEmail(toEmail);
        return ResponseEntity.ok("이메일 전송 성공!");
    }






}