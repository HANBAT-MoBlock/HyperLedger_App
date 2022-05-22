package com.capstone.capstone.DTO;

public class UserLoginRequestDTO {
    private Long id;
    private String password;

    public UserLoginRequestDTO(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginRequestDTO{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
