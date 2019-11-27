package com.axmor.dto;

import lombok.Data;

@Data
public class User {
    private int userId;
    private String userName;
    private String salt;
    private String hashPass;
}
