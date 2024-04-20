package kgt.tockbit.service;

import kgt.tockbit.domain.User;
import kgt.tockbit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
@Autowired
    private UserService(UserRepository memberRepository) {
        this.userRepository = memberRepository;
    }

    public User save(User member) {
        return userRepository.save(member);
    }

//    public User findByToken(String token) {
//        // 토큰으로 사용자를 찾는 로직을 구현합니다.
//        // 이 예시에서는 토큰을 데이터베이스에 저장하고, 이메일 인증 시 사용한다고 가정했습니다.
//    }
}
