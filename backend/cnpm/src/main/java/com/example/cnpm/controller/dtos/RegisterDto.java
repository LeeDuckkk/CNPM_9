package com.example.cnpm.controller.dtos;

import com.example.cnpm.entity.Role;
import com.example.cnpm.entity.enums.Placement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {

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
    private Set<Role> roles;

    private Placement placement;

    private String username;
    private String password;

}
