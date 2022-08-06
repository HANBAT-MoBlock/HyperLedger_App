package com.example.hyperledgervirtualmoneyproject.DTO;

public class UserCreateBodyDTO {
    private String identifier;
    private String password;
    private String name;
    private String userRole;

    public UserCreateBodyDTO(String identifier, String password, String name, String userRole) {
        this.identifier = identifier;
        this.password = password;
        this.name = name;
        this.userRole = userRole;
    }
}
