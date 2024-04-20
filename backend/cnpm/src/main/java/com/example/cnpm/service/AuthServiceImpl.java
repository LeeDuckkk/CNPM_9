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
import com.example.cnpm.utils.RandomPasswordGenerator;
import com.example.cnpm.utils.SendMail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private final SendMail sendMail;

    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           UserService userService,
                           SendMail sendMail) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.sendMail = sendMail;
    }

    @Override
    public String login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenProvider.generateToken(authentication);
        } catch (AuthenticationException e) {
            throw new CustomApiException(HttpStatus.UNAUTHORIZED, "Invalid username/password supplied");
        }
    }

    @Override
    public void changePasswordByEmail(User user) {
        RandomPasswordGenerator rpg = new RandomPasswordGenerator();
        String pass = rpg.generateRandomPassword();

        user.setPassword(passwordEncoder.encode(pass));
        userRepository.save(user);

        try {
            String bodyEmail = "Mật khẩu mới cho tài khoản " + user.getUsername() + " là: " + pass;
            sendMail.sendMailRender(user.getEmail(), "PASSWORD RECOVER", bodyEmail);
        } catch (Exception e) {
            log.error("Error when send password to email: {}", e.getMessage());
            throw new CustomApiException(HttpStatus.BAD_REQUEST, "Error when send password to email: " + e.getMessage());
        }
    }

    @Override
    public void changePasswordByOldPassword(User user, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new CustomApiException(HttpStatus.BAD_REQUEST, "Mật khẩu cũ của bạn không chính xác");
        } else if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new CustomApiException(HttpStatus.BAD_REQUEST, "Mật khẩu mới của bạn không thể giống với mật khẩu hiện tại");
        } else {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
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

