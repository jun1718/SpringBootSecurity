package shop.gaship.gashipfront.security.social.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/all")
                .permitAll()
            .antMatchers("/manager")
                .hasRole("USER");

        http.sessionManagement().disable();
        http.csrf().disable();

        http.formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/loginAction")
                .usernameParameter("id")
                .passwordParameter("pw")
            .defaultSuccessUrl("/all")
            .failureUrl("/login")
            .and()
            .logout();

        http.oauth2Login()
            .loginPage("/login")
            .defaultSuccessUrl("/all")
            .failureUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user1")
            .password("$2a$10$pnrG3Vqknwc.Okcw9ab6A.S5lGjtw8UyDVd530Wwhi3GZA4V9nJVO")
            .roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
