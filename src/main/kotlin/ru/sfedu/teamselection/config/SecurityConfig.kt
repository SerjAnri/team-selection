package ru.sfedu.teamselection.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import ru.sfedu.teamselection.repository.UserRepository
import ru.sfedu.teamselection.security.SimpleAuthenticationSuccessHandler
import ru.sfedu.teamselection.service.Oauth2UserService

@Configuration
class SecurityConfig(
    private val oauth2UserService: Oauth2UserService,
    private val userRepository: UserRepository
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.authorizeHttpRequests()
            .antMatchers("/").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Login()
            .loginPage("http://localhost:3000/")
            .userInfoEndpoint()
            .userService(oauth2UserService)
            .and()
            .successHandler(SimpleAuthenticationSuccessHandler(userRepository))
    }
}