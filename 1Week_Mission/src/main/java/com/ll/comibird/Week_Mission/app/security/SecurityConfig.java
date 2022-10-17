package com.ll.comibird.Week_Mission.app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        http
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests
                                .antMatchers("/**").permitAll()
                )
                // 타 도메인에서 API 호출 가능
                .cors(
                        cors -> cors
                                .disable()
                )
                // csrf 토큰 사용 X (추후 수정 필요)
                .csrf(
                        csrf -> csrf
                                .disable()
                )
                // httpBaic 로그인 방식 끄기
                .httpBasic(
                        httpBasic -> httpBasic
                                .disable()
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/member/login")
                                // 로그인 성공 시 호출되는 URL
                                .defaultSuccessUrl("/")
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                )
        ;

        return http.build();
    }
}
