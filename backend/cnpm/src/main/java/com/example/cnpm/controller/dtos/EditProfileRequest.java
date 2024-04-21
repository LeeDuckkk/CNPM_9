package com.example.cnpm.controller.dtos;

import com.example.cnpm.entity.enums.Placement;
import lombok.Data;

import java.util.Date;

@Data
public class EditProfileRequest {
    private String username;
    private String name;

    private String phoneNumber;
    private String email;

    private Date birthday;
    private String birthPlace;

    private String hobby;
    private String hatred;

    private String strength;
    private String weakness;

    private String lifeMotto;
    private String achievement;

    private Date joinDate;

    private Placement placement;
}
