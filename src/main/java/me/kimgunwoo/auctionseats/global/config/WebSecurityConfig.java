package me.kimgunwoo.auctionseats.global.config;

import org.springframework.http.HttpMethod;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.global.jwt.ExceptionHandlerFilter;
import me.kimgunwoo.auctionseats.global.jwt.JwtAuthenticationFilter;
import me.kimgunwoo.auctionseats.global.jwt.JwtAuthorizationFilter;
import me.kimgunwoo.auctionseats.global.jwt.JwtUtil;
import me.kimgunwoo.auctionseats.global.util.LettuceUtils;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;
    private final LettuceUtils lettuceUtils;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationConfiguration authenticationConfiguration;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, lettuceUtils);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, lettuceUtils);
    }
    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        (sessionManagement) ->
                                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        http.authorizeHttpRequests(
                (request) ->
                        request
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/api/v1/auth/**", "/api/v1/payments/getKey").permitAll()
                                .requestMatchers("/*.html", "/*/*.html").permitAll()
                                .requestMatchers("/api/v1/users/signup").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/shows-categorys/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/zones/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/auctions/*/bids/sse").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/shows/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/places/**").permitAll()
                                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
        );

        http.formLogin((formLogin) ->
                formLogin
                        .loginPage("/login.html")
                        .defaultSuccessUrl("/index.html")
                        .permitAll()
        );

        http
                .addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter(), JwtAuthorizationFilter.class)
                .exceptionHandling(handler -> handler.accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }
}