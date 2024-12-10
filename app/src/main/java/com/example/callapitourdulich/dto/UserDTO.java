package com.example.callapitourdulich.dto;

public class UserDTO {
    private String name;
    private String password ;
    private String retypePassword ;
    private String phone ;
    private int role_id;

    public UserDTO(String name, String password, String retypePassword, String phone, int role_id) {
        this.name = name;
        this.password = password;
        this.retypePassword = retypePassword;
        this.phone = phone;
        this.role_id = role_id;
    }

    public UserDTO(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
