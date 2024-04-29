package kgt.tockbit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import java.sql.Timestamp;

@Entity
public class User {
    //사용자 이메일
    @Id
    @Column(unique = true)
    private  String email;
    //사용자의 암호화 된 비밀번호
    private String password;
    //사용자의 이름
    private String name;
    //인사말
    private String greet;
    //프로플이미지
    private String image;

    private boolean islogin;

    @Column(nullable = false)
    private Timestamp join_date;

    public Timestamp getJoin_date() {
        return join_date;
    }

    @PrePersist
    public void prePersist(){
        this.join_date = new Timestamp(System.currentTimeMillis());
    }

    public void setJoin_date(Timestamp join_data) {
        this.join_date = join_data;
    }

    public boolean isIslogin() {
        return islogin;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }

    private boolean verified = false;

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean emailverified) {
        this.verified = emailverified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGreet() {
        return greet;
    }

    public void setGreet(String greet) {
        this.greet = greet;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
