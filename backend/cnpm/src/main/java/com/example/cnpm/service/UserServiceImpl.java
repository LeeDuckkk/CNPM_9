package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.ConfessionDto;
import com.example.cnpm.controller.dtos.EditProfileRequest;
import com.example.cnpm.controller.dtos.UserDto;
import com.example.cnpm.entity.User;
import com.example.cnpm.repository.UserRepository;
import com.example.cnpm.utils.SecurityContextUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private SecurityContextUtil securityContextUtil;

    public UserServiceImpl(UserRepository userRepository, SecurityContextUtil securityContextUtil) {
        this.userRepository = userRepository;
        this.securityContextUtil = securityContextUtil;
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
        List<UserDto> userDtos = userRepository.findAllDesc().stream().map(UserDto::new).toList();
        int pageSize = 10;
        int totalPage = (int) Math.ceil(userDtos.size() / (double) pageSize);
        if (page > totalPage) {
            page = totalPage;
        }
        if (page <= 0) {
            page = 1;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, userDtos.size());
        return new PageImpl<>(userDtos.subList(start, end), PageRequest.of(page, pageSize), userDtos.size());
    }
}
