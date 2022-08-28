package com.example.hyperledgervirtualmoneyproject.DTO;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class QrCreateDTO {
    public Long receiverId;
    public String coinName;
    public Long amount;
}
