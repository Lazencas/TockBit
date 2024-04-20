package kgt.tockbit.service;

import kgt.tockbit.domain.User;
import kgt.tockbit.dto.loginRequestDto;
import kgt.tockbit.dto.loginResponseDto;
import kgt.tockbit.dto.updateUserRequestDto;
import kgt.tockbit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
@Autowired
    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /*
    이메일 인증을 이용한 회원가입
     */
    public Long join(User user) {
        //같은 이메일 X
        vaildateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
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
    public ResponseEntity login(loginRequestDto loginRequestDto){
    String email = loginRequestDto.getEmail();
    String password = loginRequestDto.getPassword();
        String responseBody = "Hello, World!";
    return ResponseEntity.ok(responseBody);
    }

    /*
    로그아웃
     */
    public void logout(User user){
        userRepository.findByEmail(user.getEmail()).
                ifPresent(foundUser -> {
                    foundUser.setIslogin(false);
                    userRepository.save(foundUser);
                });


    }
    /*
    회원정보 수정
     */
    public void updateUser(updateUserRequestDto user){
        userRepository.findByEmail(user.get).
                ifPresent(foundUser ->{
                    foundUser.
                });

    }

    /*
    회원탈퇴
     */

    /*
    전체 회원 조회
     */

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userId) {
        return  userRepository.findById(userId);
    }


//    public User findByToken(String token) {
//        // 토큰으로 사용자를 찾는 로직을 구현합니다.
//        // 이 예시에서는 토큰을 데이터베이스에 저장하고, 이메일 인증 시 사용한다고 가정했습니다.
//    }
}
