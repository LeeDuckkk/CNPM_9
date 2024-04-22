package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.ConfessionDto;
import com.example.cnpm.controller.dtos.EditProfileRequest;
import com.example.cnpm.controller.dtos.RegisterDto;
import com.example.cnpm.controller.dtos.UserDto;
import com.example.cnpm.entity.Role;
import com.example.cnpm.entity.User;
import com.example.cnpm.exception.CustomApiException;
import com.example.cnpm.repository.RoleRepository;
import com.example.cnpm.repository.UserRepository;
import com.example.cnpm.utils.Constant;
import com.example.cnpm.utils.SecurityContextUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private SecurityContextUtil securityContextUtil;

    public UserServiceImpl(UserRepository userRepository, SecurityContextUtil securityContextUtil, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.securityContextUtil = securityContextUtil;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new CustomApiException(HttpStatus.BAD_REQUEST, "Username is already exists");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setEmail(registerDto.getEmail());

        user.setBirthday(registerDto.getBirthday());
        user.setBirthPlace(registerDto.getBirthPlace());

        user.setHobby(registerDto.getHobby());
        user.setHatred(registerDto.getHatred());

        user.setStrength(registerDto.getStrength());
        user.setWeakness(registerDto.getWeakness());

        user.setLifeMotto(registerDto.getLifeMotto());
        user.setAchievement(registerDto.getAchievement());

        user.setJoinDate(registerDto.getJoinDate());
        user.setPlacement(registerDto.getPlacement());

        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = registerDto.getRoles();
        if (roles == null || roles.isEmpty()) {
            Optional<Role> userRole = roleRepository.findByName(Constant.roleMember);
            Role role = userRole.orElseGet(() -> roleRepository.save(new Role(Constant.roleMember)));
            roles = new HashSet<>();
            roles.add(role);
        } else {
            roles = roles.stream().map(role -> {
                Optional<Role> roleOptional = roleRepository.findByName(role.getName());
                return roleOptional.orElseGet(() -> roleRepository.save(role));
            }).collect(Collectors.toSet());
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public User edit(Long id, RegisterDto registerDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomApiException(HttpStatus.NOT_FOUND, "User not found"));
        user.setName(registerDto.getName());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setEmail(registerDto.getEmail());

        user.setBirthday(registerDto.getBirthday());
        user.setBirthPlace(registerDto.getBirthPlace());

        user.setHobby(registerDto.getHobby());
        user.setHatred(registerDto.getHatred());

        user.setStrength(registerDto.getStrength());
        user.setWeakness(registerDto.getWeakness());

        user.setLifeMotto(registerDto.getLifeMotto());
        user.setAchievement(registerDto.getAchievement());

        user.setJoinDate(registerDto.getJoinDate());
        user.setPlacement(registerDto.getPlacement());

        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = registerDto.getRoles();
        if (roles == null || roles.isEmpty()) {
            Optional<Role> userRole = roleRepository.findByName(Constant.roleMember);
            Role role = userRole.orElseGet(() -> roleRepository.save(new Role(Constant.roleMember)));
            roles = new HashSet<>();
            roles.add(role);
        } else {
            roles = roles.stream().map(role -> {
                Optional<Role> roleOptional = roleRepository.findByName(role.getName());
                return roleOptional.orElseGet(() -> roleRepository.save(role));
            }).collect(Collectors.toSet());
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Remove all roles from the user
        user.getRoles().clear();

        // Save the user with the updated roles
        userRepository.save(user);

        // Now it's safe to delete the user
        userRepository.deleteById(id);
    }

    @Override
    public User getCurrentUser() {
        Long currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            return userRepository.getReferenceById(currentUserId);
        }
        throw new RuntimeException("User ID is null");
    }

    @Override
    public Long getCurrentUserId() {
        return securityContextUtil.getCurrentUserId();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User editProfile(EditProfileRequest editProfileRequest) {
        User user = getCurrentUser();
        user.setName(editProfileRequest.getName());
        user.setPhoneNumber(editProfileRequest.getPhoneNumber());
        user.setEmail(editProfileRequest.getEmail());
        user.setBirthday(editProfileRequest.getBirthday());
        user.setBirthPlace(editProfileRequest.getBirthPlace());
        user.setHobby(editProfileRequest.getHobby());
        user.setHatred(editProfileRequest.getHatred());
        user.setStrength(editProfileRequest.getStrength());
        user.setWeakness(editProfileRequest.getWeakness());
        user.setLifeMotto(editProfileRequest.getLifeMotto());
        user.setAchievement(editProfileRequest.getAchievement());
        user.setPlacement(editProfileRequest.getPlacement());
        return userRepository.save(user);
    }

    @Override
    public Page<UserDto> getAllUser(int page) {
        List<UserDto> userDtos = userRepository.findAllDesc().stream().map(UserDto::new).collect(Collectors.toList());
        int pageSize = 10;
        int totalElements = userDtos.size();
        int totalPage = (int) Math.ceil((double) totalElements / pageSize);
        if (page > totalPage) {
            page = totalPage;
        }
        if (page <= 0) {
            page = 1;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalElements);
        return new PageImpl<>(userDtos.subList(start, end), PageRequest.of(page - 1, pageSize), totalElements);
    }
}
