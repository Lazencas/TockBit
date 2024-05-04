package kgt.tockbit.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follower_email")
    private String follower;

    @Column(name = "followed_email")
    private String followed;

    private Timestamp createdAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }
}
