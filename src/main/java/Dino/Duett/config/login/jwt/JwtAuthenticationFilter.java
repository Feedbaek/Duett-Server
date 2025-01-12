package Dino.Duett.config.login.jwt;

import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;
import Dino.Duett.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//해당 클래스는 JwtTokenProvider가 검증을 끝낸 Jwt로부터 유저 정보를 조회해와서 UserPasswordAuthenticationFilter 로 전달합니다.
@Component
@Slf4j(topic = "JwtAuthenticationFilter")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // 헤더에서 JWT 를 받아옵니다.
            String jwt = jwtTokenProvider.resolveToken(request);
            // 토큰이 존재하는 경우
            if (jwt != null) {
                // 유효한 토큰인지 확인합니다.
                Jws<Claims> claims = jwtTokenProvider.validateAndParseToken(jwt);
                // 토큰 타입이 올바른지 확인합니다.
                if (!(jwtTokenProvider.isAccessToken(claims) || jwtTokenProvider.isRefreshToken(claims))) {
                    throw new JwtException("토큰 타입이 올바르지 않습니다.");
                }
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = jwtTokenProvider.getAuthentication(claims);
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) { // 유효하지 않은 토큰
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            setErrorResponse(response, ErrorCode.EXPIRED_JWT_TOKEN, e);
            return;
        } catch (JwtException e) { // 유효하지 않은 토큰
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            setErrorResponse(response, ErrorCode.INVALID_JWT_TOKEN, e);
            return;
        } catch (Exception e) { // 그 외 에러
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            setErrorResponse(response, ErrorCode.BAD_REQUEST, e);
            return;
        }
        // 요청으로 들어온 request, response 를 다음 필터로 넘깁니다.
        chain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode,  Exception e) throws IOException {
        response.setStatus(response.getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> errors = new HashMap<>();
        String fieldName = "Exception";
        String errorMessage = e.getMessage();
        errors.put(fieldName, errorMessage);
        CustomException customException = CustomException.of(errorCode, errors);
        ErrorResponse errorResponse = ErrorResponse.from(customException);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}