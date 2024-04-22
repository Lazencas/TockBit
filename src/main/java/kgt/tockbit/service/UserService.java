package kgt.tockbit.service;

import jakarta.servlet.http.HttpServletResponse;
import kgt.tockbit.domain.User;
import kgt.tockbit.dto.loginRequestDto;
import kgt.tockbit.dto.loginResponseDto;
import kgt.tockbit.dto.updateUserRequestDto;
import kgt.tockbit.jwt.JwtUtil;
import kgt.tockbit.repository.MemoryUserRepository;
import kgt.tockbit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService mailService;

    @Autowired
    private JwtUtil jwtUtil;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /*
        이메일 인증을 이용한 회원가입
         */
    public String join(User user) {
        //같은 이메일 X
        vaildateDuplicateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user.getEmail();
    }
    private void vaildateDuplicateUser(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이메일입니다.");
                 });
    }

    /*
    JWT토큰을 이용한 로그인
     */
    public ResponseEntity login(loginRequestDto loginRequestDto, HttpServletResponse res){
    String email = loginRequestDto.getEmail();
    String password = loginRequestDto.getPassword();
        //사용자확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        //비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //인증된 사용자에게 JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getEmail());
        jwtUtil.addJwtToCookie(token, res);
        user.setIslogin(true);
        userRepository.save(user);

        String responseBody = "Hello, World!";
    return ResponseEntity.ok(responseBody);
    }

    /*
    로그아웃
     */
    public ResponseEntity<String> logout(String email){
        userRepository.findByEmail(email).
                ifPresent(foundUser -> {
                    foundUser.setIslogin(false);
                    userRepository.save(foundUser);
                });
        return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);

    }
    /*
    회원정보 수정
     */
    public User updateUser(String email, updateUserRequestDto requestDto) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email=" + email));
        user.setName(requestDto.getName());
        user.setGreet(requestDto.getGreet());
        user.setImage(requestDto.getImage());
        user.setPassword(requestDto.getPassword());

        return userRepository.save(user);
    }

    /*
    회원탈퇴
     */
    public ResponseEntity<String> deleteUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 사용자가 없습니다. email=" + email));

        // 비밀번호 확인
        if (!user.getPassword().equals(password)) {
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }

        userRepository.deleteByEmail(email);
        return new ResponseEntity<>("회원 탈퇴가 완료되었습니다.", HttpStatus.OK);
    }
    /*
    전체 회원 조회
     */

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(String userEmail) {
        return  userRepository.findByEmail(userEmail);
    }

    public void sendToEmail(String toEmail) {
        String title = "Travel with me 이메일 인증 번호";
        String authCode = "안냐때염";
        mailService.sendEmail(toEmail, title, authCode);
    }


//    public User findByToken(String token) {
//        // 토큰으로 사용자를 찾는 로직을 구현합니다.
//        // 이 예시에서는 토큰을 데이터베이스에 저장하고, 이메일 인증 시 사용한다고 가정했습니다.
//    }
}
