package com.nagaroo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class ApplicationConfig extends WebSecurityConfigurerAdapter { 
   @Bean 
   public PasswordEncoder passwordEncoder() { 
      return new BCryptPasswordEncoder(); 
   }
   @Override
   protected void configure(HttpSecurity http) throws Exception {
     http.csrf().disable() .authorizeRequests()
              .antMatchers("/admin/**").hasRole("ADMIN")
              .antMatchers("/user*").hasRole("USER")
              .antMatchers("/login**").permitAll().anyRequest()
              .authenticated().and()
              .formLogin()
             // .defaultSuccessUrl("/index",true)
              .and()
              .rememberMe()
              .and() .logout() .logoutUrl("/logout")
              .logoutSuccessUrl("/login") .deleteCookies("remember-me").and()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/login").maximumSessions(1).expiredUrl("/loginExpire");
   }

   @Bean
   protected UserDetailsService userDetailsService() {
      UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
      UserDetails user = User.withUsername("admin")
              .password(passwordEncoder().encode("admin")).roles("ADMIN")
              .authorities("ADMIN") .build();
      UserDetails user1 = User.withUsername("user")
              .password(passwordEncoder().encode("user")).roles("USER")
              .authorities("USER") .build();
      userDetailsManager.createUser(user);
      userDetailsManager.createUser(user1);

      return userDetailsManager;

   }

   @Bean
   public HttpSessionEventPublisher httpSessionEventPublisher() {
      return new HttpSessionEventPublisher();
   }
}