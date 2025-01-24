package com.tpe.security.config;

import com.tpe.security.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthTokenFilter authTokenFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//RES_API'de session yok
                .and()
                .authorizeHttpRequests()
                .antMatchers("/register","/login").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /*    Spring Security'nin AuthenticationProvider kullanımı
    doğrudan kodda yer almıyor, ancak bu eksiklik bir problem
    oluşturmaz çünkü AuthenticationManagerBuilder arka planda
    bir DaoAuthenticationProvider oluşturur ve kullanır.
    Bu otomatik yapılandırma sayesinde,
    AuthenticationProvider'ı
    manuel olarak tanımlamak zorunda kalmazsınız.*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            //super.configure(auth);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //passwordencoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();// varsayilan 10'dur gucluluk seviyesi
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
