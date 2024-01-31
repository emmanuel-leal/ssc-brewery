package guru.sfg.brewery.config;


import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.RestUrlAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager){
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return  filter;
    }

    public RestUrlAuthFilter restUrlFilter(AuthenticationManager authenticationManager){
        RestUrlAuthFilter filter = new RestUrlAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return  filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(this.restHeaderAuthFilter(super.authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
        http.addFilterBefore(this.restUrlFilter(super.authenticationManager()),UsernamePasswordAuthenticationFilter.class);
        http.
                authorizeRequests(authorizer ->
                {
                    authorizer
                            .antMatchers("/h2-console/**").permitAll() // no usar en prod
                            .antMatchers("/", "/webjars/**", "/login", "/resources/**", "/beers/**").permitAll()
                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                })
                .authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
        // h2 console config
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("jose")
                //en esta pararte se configura ya encriptada la contrasenia, la desencripcion lo hace spring security internamente
                //.password("{ldap}{SSHA}5tf5HFsr99xAFSOPzVnoODoYZ/S8iDG3LDrswg==")
                .password("{ldap}{SSHA}pwH5XXtmaAKsVb5CNAh2tHj61/AIbxY38KvvgQ==")
                .roles("ADMIN").and()
                .withUser("user")
                //en esta pararte se configura ya encriptada la contrasenia, la desencripcion lo hace spring security internamente
                .password("{noop}password")
                .roles("USER");
//se puede utilizar con and o creando un nuevo metodo
        auth.inMemoryAuthentication()
                .withUser("scot")
                //en esta pararte se configura ya encriptada la contrasenia, la desencripcion lo hace spring security internamente
                .password("{bcrypt10}$2a$15$JrFmrLBrFsTzvAOfJZXZhu69Et8bHZxumwsdtLdegsVzMUYyxde7G")
                .roles("CUSTOMER");
    }*/

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
