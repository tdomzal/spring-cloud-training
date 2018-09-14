package pl.training.cloud.users.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import pl.training.cloud.users.model.Authority;
import pl.training.cloud.users.model.User;
import pl.training.cloud.users.service.UsersService;

import javax.annotation.PostConstruct;

@EnableAuthorizationServer
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(passwordEncoder);
    }

    @PostConstruct
    public void init() {
        try {
            usersService.loadUserByUsername("admin");
        } catch (UsernameNotFoundException ex) {
            User user = new User("admin", "123");
            user.addAuthority(new Authority(Role.ADMIN.name()));
            usersService.addUser(user);
        }
    }

}
