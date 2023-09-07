package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

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
        return new StandardPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("jose")
                //en esta pararte se configura ya encriptada la contrasenia, la desencripcion lo hace spring security internamente
                .password("3883aa23993e8fd5a8385d357a4eed556e33ac3e6aef6bd23fab0defacebf3f5a3a3ec57189728c5")
                .roles("ADMIN").and()
                .withUser("user")
                //en esta pararte se configura ya encriptada la contrasenia, la desencripcion lo hace spring security internamente
                .password("3883aa23993e8fd5a8385d357a4eed556e33ac3e6aef6bd23fab0defacebf3f5a3a3ec57189728c5")
                .roles("USER");
//se puede utilizar con and o creando un nuevo metodo
        auth.inMemoryAuthentication()
                .withUser("scot")
                //en esta pararte se configura ya encriptada la contrasenia, la desencripcion lo hace spring security internamente
                .password("0beded76929ce2b6eb9ecc49b4ea5c182c257f9e4f06365e83c2ff53ba080969568497b8b8aed95d")
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
