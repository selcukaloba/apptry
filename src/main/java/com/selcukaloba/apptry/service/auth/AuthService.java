package com.selcukaloba.apptry.service.auth;

import com.selcukaloba.apptry.dto.auth.LoginRequest;
import com.selcukaloba.apptry.dto.auth.RefreshTokenRequest;
import com.selcukaloba.apptry.dto.auth.RegisterRequest;
import com.selcukaloba.apptry.dto.auth.AuthResponse;
import com.selcukaloba.apptry.entity.RefreshToken;
import com.selcukaloba.apptry.entity.User;
import com.selcukaloba.apptry.exception.BaseException;
import com.selcukaloba.apptry.exception.ErrorMessage;
import com.selcukaloba.apptry.exception.MessageType;
import com.selcukaloba.apptry.repository.RefreshTokenRepository;
import com.selcukaloba.apptry.repository.UserRepository;
import com.selcukaloba.apptry.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public AuthResponse register(RegisterRequest request)
    {
        Optional<User> usr = userRepository.findByUsername(request.username());
        if(!usr.isEmpty()) throw new BaseException(new ErrorMessage(request.username(), MessageType.USER_ALREADY_EXISTS));

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        User savedUser = userRepository.save(user);
        String accessToken = jwtService.generateAccessToken(savedUser.getUsername());
        String refreshToken = refreshTokenService.createRefreshToken(savedUser);
        return new AuthResponse(accessToken, refreshToken, savedUser.getUsername());
    }

    public AuthResponse login(LoginRequest request)
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        } catch (Exception e)
        {
            throw new BaseException(new ErrorMessage(request.username(), MessageType.USERNAME_OR_PASSWORD_INVALID));
        }

        Optional<User> usr = userRepository.findByUsername(request.username());
        if(usr.isEmpty()) throw new BaseException(new ErrorMessage(request.username(), MessageType.USERNAME_NOT_FOUND));

        User user = usr.get();
        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = refreshTokenService.createRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken, user.getUsername());
    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request)
    {
        RefreshToken reftoken = refreshTokenService.findByRefreshToken(request.refreshToken());
        User user = reftoken.getUser();
        refreshTokenService.deleteByUser(user);
        String newAccessToken = jwtService.generateAccessToken(user.getUsername());
        String newRefreshToken = refreshTokenService.createRefreshToken(user);
        return new AuthResponse(newAccessToken, newRefreshToken, user.getUsername());
    }

    @Transactional
    public void logout(RefreshTokenRequest request)
    {
        RefreshToken refToken = refreshTokenService.findByRefreshToken(request.refreshToken());
        User user = refToken.getUser();
        refreshTokenRepository.deleteByUser(user);
    }
}
