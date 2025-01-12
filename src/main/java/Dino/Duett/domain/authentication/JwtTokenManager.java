package Dino.Duett.domain.authentication;

import Dino.Duett.config.login.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenManager {
    private final JwtTokenProvider jwtTokenProvider;
}
