package com.capstone.capstone.DTO;

/*
{
    accessToken : Bearer eyJ0eXAiOiJBQ0NFU1NfVE9LRU4iLCJhbGciOiJIUzI1NiJ9
    .eyJzdWIiOiI1IiwiaWF0IjoxNjUzMTMxMDYyLCJleHAiOjE2NTMxMzQ2NjIsInJvbGUiOiJST0xFX1VTRVIifQ
    .ZUzqUXOfVkukkFfsXMKbi4iSTWZXjvs8KdQhAVBm3B4
}
 */
public class UserCreateDTO {
    private String accessToken;

    @Override
    public String toString() {
        return "UserCreateDTO{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }
}
