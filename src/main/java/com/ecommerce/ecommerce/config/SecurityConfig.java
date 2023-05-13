package com.ecommerce.ecommerce.config;
import com.ecommerce.ecommerce.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/find").permitAll()
                .antMatchers(HttpMethod.GET, "/users/export/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products/**").permitAll()
                .antMatchers("/api/orderDetails/**").permitAll()
                .antMatchers("/api/orders/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/categories/**").permitAll()
                .antMatchers("/api/wishlist/**").permitAll()
                .antMatchers("/api/carts/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/payments/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/shipping/**").permitAll()
                .antMatchers("/api/reviews/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws
            Exception {auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

}
