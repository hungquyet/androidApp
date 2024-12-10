package com.example.callapitourdulich.response;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    private String roleId;

    @SerializedName("id")
    private int id;

    private String name;
    private String phone;
    private String address;
    private String email;
    private String gender;

    // Getter và Setter
    public String getRoleId() { return roleId; }
    public void setRoleId(String roleId) { this.roleId = roleId; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}
