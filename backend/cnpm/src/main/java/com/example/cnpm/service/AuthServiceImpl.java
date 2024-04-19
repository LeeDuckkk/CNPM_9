package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.LoginDto;
import com.example.cnpm.controller.dtos.RegisterDto;
import com.example.cnpm.controller.dtos.UserDto;
import com.example.cnpm.entity.Role;
import com.example.cnpm.entity.User;
import com.example.cnpm.exception.CustomApiException;
import com.example.cnpm.repository.RoleRepository;
import com.example.cnpm.repository.UserRepository;
import com.example.cnpm.security.JwtTokenProvider;
import com.example.cnpm.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public String login(LoginDto loginDto) {
        // find user ... Load by username
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                loginDto.getPassword()));
        // check
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        return token;

    }

    @Override
    public UserDto changePassword(UserDto user) {
        return null;
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

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName(Constant.roleMember);
        Role role = userRole.orElseGet(() -> roleRepository.save(new Role(Constant.roleMember)));
        roles.add(role);
        user.setRoles(roles);

        return userRepository.save(user);
    }
}

