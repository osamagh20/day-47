package com.example.pro3.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {
    private final UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/user/customer-register").permitAll()
                .requestMatchers("/api/v1/user/customer-register").permitAll()
                .requestMatchers("/api/v1/user/get-all-users","/api/v1/user/activate-account/","/api/v1/user/block-account/","/api/v1/customer/get-all-customers","/api/v1/account/get-all-accounts","/api/v1/employees/get-all-employees").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/deposit/","/api/v1/customer/update-customer",
                        "/api/v1/customer/withdraw/","/api/v1/customer/transfer/",
                        "/api/v1/account/add-account","/api/v1/account/get-account-details/",
                        "/api/v1/account/update-account","/api/v1/account/delete-account").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/employees/update").hasAuthority("EMPLOYEE")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
                return http.build();
    }
}
