package kgt.tockbit.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "followed_email")
    private String followed;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private String content;

    private Timestamp createdAt; // timestamp로 변경



    // 생성자, getter 및 setter
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

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    // 활동 타입 열거형
    public enum ActivityType {
        FOLLOW, // 팔로우
        POST,   // 게시글 작성
        COMMENT, // 댓글 작성
        PLIKE,// 게시글 좋아요
        CLIKE//댓글 좋아요
    }
}
