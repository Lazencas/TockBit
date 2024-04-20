package kgt.tockbit.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    //사용자 고유 id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //사용자 이메일
    private  String email;
    //사용자의 암호화 된 비밀번호
    private String password;
    //사용자의 이름
    private String name;
    //인사말
    private String greet;
    //프로플이미지
    private byte[] image;

    private boolean islogin;

    public boolean isIslogin() {
        return islogin;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }

    private boolean emailverified = false;

    public boolean isEmailverified() {
        return emailverified;
    }

    public void setEmailverified(boolean emailverified) {
        this.emailverified = emailverified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
