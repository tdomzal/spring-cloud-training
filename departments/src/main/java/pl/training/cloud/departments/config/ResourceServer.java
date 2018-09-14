package pl.training.cloud.departments.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import pl.training.cloud.departments.controller.UriBuilder;

@EnableResourceServer
@Configuration
public class ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(UriBuilder.PUBLIC_URIS).permitAll()
                .antMatchers(UriBuilder.ALL_URIS).hasRole(Role.ADMIN.name());
    }

}