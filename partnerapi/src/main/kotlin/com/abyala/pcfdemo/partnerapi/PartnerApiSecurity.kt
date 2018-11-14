package com.abyala.pcfdemo.partnerapi

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class CardServiceSecurity : WebSecurityConfigurerAdapter() {
    @Bean
    fun passwordEncoder() = object : PasswordEncoder {
        override fun encode(rawPassword: CharSequence): String =
                rawPassword.toString()

        override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean =
                rawPassword == encodedPassword
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("demo")
                .password("pcf")
                .roles("USER")
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/order").hasRole("USER")
                .and().httpBasic()
        http.csrf().disable()
    }
}