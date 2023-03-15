package ru.psuti.apache1337.homeowners.config;

import ru.psuti.apache1337.homeowners.security.OtpAuthenticationProvider;
import ru.psuti.apache1337.homeowners.config.handler.CustomAuthenticationFailureHandler;
import ru.psuti.apache1337.homeowners.config.handler.MySimpleUrlAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final MySimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler;
    private final OtpAuthenticationProvider otpAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/onetimecode").not().authenticated()
                .antMatchers("/registration-otp").not().authenticated()
                .antMatchers("/rest/users").not().authenticated()
                .antMatchers("/registration").not().authenticated()
                .antMatchers("/username").not().authenticated()
                .antMatchers("/rest/**").authenticated()
                .antMatchers("/rest/admin/**").hasRole("ADMIN")
                .antMatchers("/upload").hasRole("USER")
                .antMatchers("/request").hasRole("USER")
                .antMatchers("/request/**").hasRole("USER")
                .antMatchers("/files/**").hasRole("USER")
                .antMatchers("/version").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new CustomAuthenticationFailureHandler())
                .and()
                .logout()
                .permitAll().logoutSuccessUrl("/")
                .and()
                .logout().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and().exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and().authenticationProvider(otpAuthenticationProvider);
        return http.build();
    }

}