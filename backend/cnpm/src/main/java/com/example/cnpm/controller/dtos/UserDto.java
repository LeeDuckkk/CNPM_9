package com.example.cnpm.controller.dtos;

import com.example.cnpm.entity.Role;
import com.example.cnpm.entity.User;
import com.example.cnpm.entity.enums.Placement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private Long id;

    private Set<Role> role;

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

    public UserDto(User user) {
        this.id = user.getId();
        this.role = user.getRoles();
        this.username = user.getUsername();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.birthday = user.getBirthday();
        this.birthPlace = user.getBirthPlace();
        this.hobby = user.getHobby();
        this.hatred = user.getHatred();
        this.strength = user.getStrength();
        this.weakness = user.getWeakness();
        this.lifeMotto = user.getLifeMotto();
        this.achievement = user.getAchievement();
        this.joinDate = user.getJoinDate();
        this.placement = user.getPlacement();
    }
}
