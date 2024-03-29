package com.example.ctsbe.config;

import com.example.ctsbe.filter.JwtTokenFilter;
import com.example.ctsbe.repository.AccountRepository;
import com.example.ctsbe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> accountService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found.")));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/auth/login"
                        //, "/accounts/addAccount"
                        //, "/accounts/resetPassword/*"
                        //, "/accounts/getAllAccount"
                        //, "/accounts/changePassword/*"
                        , "/accounts/sendForgotPassword"
                        //, "/accounts/changeEnableAccount/*"
                        //, "/accounts/updateAccount/*"
                        //, "/accounts/getProfile/*"
                        , "/staffs/*"
                        , "/staffs/*/*"
                        ,"/groups/*"
                        , "/groups/*/*"
                        ,"/levels/*"
                        , "/levels/*/*"
                        ,"/projects/*"
                        ,"/holidays/*"
                        ,"/reports/*"
                        ,"/complaints/*"
                        ,"/timesheets/getTimesheet/*"
                        ,"/projects/*/*"
                        ,"/projects/*/*"
                        ,"/check-in/facial-recognition/verify"
                        ,"/check-in/*/facial-recognition/setup"
                        ,"/image/image-verify"
                        ,"/staffs/*/get-image-setup"
                        ,"/timesheets/*/*"
                        ,"/timesheets/*"
                        ,"/image-setup/*/*").permitAll()
                //.antMatchers("/accounts/getProfile/*").hasRole("ROLE_HUMAN RESOURCE")
                .anyRequest().authenticated();
        http.cors();

        http.exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                );

//        http.cors().configurationSource(request -> {
//            final CorsConfiguration cors = new CorsConfiguration();
//            cors.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://192.168.1.207:8080"));
 //           cors.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
            //cors.setAllowedHeaders(Collections.singletonList("*"));
//            return cors;
//        });

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
