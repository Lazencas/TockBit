package kgt.tockbit.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_email")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followed_user_email")
    private User followedUser;

    private Timestamp createdAt;

    // 생성자, getter 및 setter 생략
}
