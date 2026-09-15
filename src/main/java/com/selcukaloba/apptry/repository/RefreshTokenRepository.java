package com.selcukaloba.apptry.repository;

import com.selcukaloba.apptry.entity.RefreshToken;
import com.selcukaloba.apptry.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository  extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String token);
    void deleteByUser(User user);
    List<RefreshToken> findAllByUser(User user);
}
