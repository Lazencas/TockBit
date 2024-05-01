package kgt.tockbit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserInfo {
    //사용자 이메일
    @Id
    @Column(unique = true)
    private  String email;
    //사용자의 이름
    private String name;
    //인사말


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
