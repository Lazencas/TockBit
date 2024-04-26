package kgt.tockbit.dto;

public class loginResponseDto {
    private String email;
    private String name;
    private byte[] iamge;
    private  String greet;

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

    public byte[] getIamge() {
        return iamge;
    }

    public void setIamge(byte[] iamge) {
        this.iamge = iamge;
    }

    public String getGreet() {
        return greet;
    }

    public void setGreet(String greet) {
        this.greet = greet;
    }
}

