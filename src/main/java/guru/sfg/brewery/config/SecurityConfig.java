package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import security.SfgPasswordEncoderFactories;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests(authorizer ->
                {
                    authorizer.antMatchers("/", "/webjars/**", "/login", "/resources/**", "/beers/**").permitAll()
                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                })
                .authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("jose")
                //en esta pararte se configura ya encriptada la contrasenia, la desencripcion lo hace spring security internamente
                .password("{ldap}{SSHA}5tf5HFsr99xAFSOPzVnoODoYZ/S8iDG3LDrswg==")
                .roles("ADMIN").and()
                .withUser("user")
                //en esta pararte se configura ya encriptada la contrasenia, la desencripcion lo hace spring security internamente
                .password("{noop}password")
                .roles("USER");
//se puede utilizar con and o creando un nuevo metodo
        auth.inMemoryAuthentication()
                .withUser("scot")
                //en esta pararte se configura ya encriptada la contrasenia, la desencripcion lo hace spring security internamente
                .password("{bcrypt}$2a$10$bNxA09teyU81drVDF1erk.5ji.Nu.w4cV1u6tzpA7Hsh6kvbj8xTK")
                .roles("CUSTOMER");
    }

    /*    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("jose")
                .password("leal")
                .roles("ADMIN")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin,user);
    }*/
}
