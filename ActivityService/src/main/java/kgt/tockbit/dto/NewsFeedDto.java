package kgt.tockbit.dto;

import java.sql.Timestamp;

public class NewsFeedDto {

    private Long id;

    private String type;

    private String userEmail;

    private String followed;
    private String post_id;

    private String title;

    private String comment_id;

    private String content;

    private Timestamp createdAt; // timestamp로 변경

    // 생성자, getter 및 setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // 활동 타입 열거형
    public enum ActivityType {
        FOLLOW, // 팔로우
        POST,   // 게시글 작성
        COMMENT, // 댓글 작성
        PLIKE,// 게시글 좋아요
        CLIKE//댓글 좋아요
    }

//    public NewsFeedDto(Activity activity) {
//        this.id = activity.getId();
//        this.type = activity.getType().toString();
//        this.userEmail = activity.getUserEmail();
//        if (activity.getFollowed() !=null){
//            this.followed = activity.getFollowed();
//        }
//        if (activity.getPost() != null){
//            this.post_id = activity.getPost().getId().toString();
//        }
//        if (activity.getComment() != null){
//            this.comment_id = activity.getComment().getId().toString();
//        }
//        if (activity.getContent() != null){
//            this.content = activity.getContent();
//        }
//        this.createdAt = activity.getCreatedAt();
//    }
}
