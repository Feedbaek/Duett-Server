package Dino.Duett.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

// spring security 설정 파일 입니다.
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity() // prePostEnabled 어노테이션 활성화
public class SecurityConfig {
    private final EnvBean envBean;

    // GET 메소드 허용 경로
    private final String[] GET_LIST = {

    };

    // 모든 사용자 허용 경로
    private final String[] WHITE_LIST = {
        // swagger
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/swagger-resources/**",

        // 에러 페이지
        "/error"
    };

    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    protected SecurityFilterChain apiConfig(HttpSecurity http) throws Exception {
        // 로그인 설정
        http
//            .formLogin(form -> form
//                    .loginPage("/login") // 로그인 페이지
//                    .loginProcessingUrl("/login") // 로그인 처리 url
//                    .defaultSuccessUrl("/index", true) // 로그인 성공시 이동할 페이지
//                    .failureUrl("/login?error=true") // 로그인 실패시 이동할 페이지
//                    .permitAll()) // 로그인 페이지는 모든 사용자 허용
//            .logout(logout -> logout
//                    .logoutUrl("/logout") // 로그아웃 url
//                    .deleteCookies("JSESSIONID") // 쿠키 삭제
//                    .logoutSuccessUrl("/login") // 로그아웃 성공시 이동할 페이지
//                    .permitAll()) // 로그아웃 페이지는 모든 사용자 허용

            // 허용 경로 설정
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(WHITE_LIST).permitAll() // 모든 사용자 허용 경로 (모든 메소드)
                    .anyRequest().permitAll()
            );


        // cors 설정
        http.cors(cors -> cors
                .configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOriginPattern("*"); // 모든 ip에 응답을 허용합니다.
                    corsConfiguration.addAllowedMethod("*");
                    corsConfiguration.addAllowedHeader("*");
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setMaxAge(3600L);
                    return corsConfiguration;
                })
            )

        // 예외 처리
        .exceptionHandling(exception -> exception
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value()); // 403
                })); // 권한 없음

        // csrf 비활성화 및 jwt 필터 추가
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // 세션 생성 정책
        .csrf(AbstractHttpConfigurer::disable); // csrf 비활성화

//        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt 필터 추가

        return http.build();
    }
}