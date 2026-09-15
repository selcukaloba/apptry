package com.selcukaloba.apptry.service.auth;

import com.selcukaloba.apptry.entity.RefreshToken;
import com.selcukaloba.apptry.entity.User;
import com.selcukaloba.apptry.exception.BaseException;
import com.selcukaloba.apptry.exception.ErrorMessage;
import com.selcukaloba.apptry.exception.MessageType;
import com.selcukaloba.apptry.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${app.jwt.refresh-expiration}")
    private long refreshExpiration;

    @Transactional
    public String createRefreshToken(User user)
    {
        String token = UUID.randomUUID().toString();
        LocalDateTime expireDate = LocalDateTime.now().plus(refreshExpiration, ChronoUnit.MILLIS);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpirationDate(expireDate);
        refreshToken.setRefreshToken(token);
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    @Transactional
    public RefreshToken findByRefreshToken(String token)
    {
        Optional<RefreshToken> opt = refreshTokenRepository.findByRefreshToken(token);
        if(opt.isEmpty())
        {
            throw new BaseException(new ErrorMessage(null, MessageType.INVALID_REFRESH_TOKEN));
        }

        RefreshToken refreshToken = opt.get();
        LocalDateTime now = LocalDateTime.now();
        if (refreshToken.getExpirationDate().isBefore(now))
        {
            refreshTokenRepository.delete(refreshToken);
            throw new BaseException(new ErrorMessage(null, MessageType.EXPIRED_REFRESH_TOKEN));
        }
        return refreshToken;
    }

    @Transactional
    public void deleteByUser(User user)
    {
        refreshTokenRepository.deleteByUser(user);
    }
}