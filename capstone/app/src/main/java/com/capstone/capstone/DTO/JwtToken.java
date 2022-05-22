package com.capstone.capstone.DTO;

public class JwtToken {
    private static String jwt;

    public static String getJwt() {
        return jwt;
    }

    public static void setToken(String jwt){
        JwtToken.jwt = jwt;
    }
}
