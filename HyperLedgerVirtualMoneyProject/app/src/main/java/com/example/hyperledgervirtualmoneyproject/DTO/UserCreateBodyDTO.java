package com.example.hyperledgervirtualmoneyproject.DTO;

public class UserCreateBodyDTO {
    private String studentId;
    private String password;
    private String name;

    public UserCreateBodyDTO(String studentId, String password, String name) {
        this.studentId = studentId;
        this.password = password;
        this.name = name;
    }
}
