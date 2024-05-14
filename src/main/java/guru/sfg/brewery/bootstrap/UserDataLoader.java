package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.RoleRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {

        if( authorityRepository.count() == 0 ){
            loadsecurityData();
        }
    }

    private void loadsecurityData() {
//        Authority adminRole= authorityRepository.save(Authority.builder().permission("ROLE_ADMIN").build());
//        Authority userRole = authorityRepository.save(Authority.builder().permission("ROLE_USER").build());
//        Authority customerRole = authorityRepository.save(Authority.builder().permission("ROLE_CUSTOMER").build());
        Authority createBeer = authorityRepository.save(Authority.builder()
                        .permission("beer.create")
                .build());
        Authority updateBeer = authorityRepository.save(Authority.builder()
                .permission("beer.update")
                .build());
        Authority readBeer = authorityRepository.save(Authority.builder()
                .permission("beer.read")
                .build());
        Authority deleteBeer = authorityRepository.save(Authority.builder()
                .permission("beer.delete")
                .build());

        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role customerRole = roleRepository.save(Role.builder().name("CUSTOMER").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());

        adminRole.setAuthorities(Set.of(createBeer,updateBeer,readBeer,deleteBeer));
        customerRole.setAuthorities(Set.of(readBeer));
        userRole.setAuthorities(Set.of(readBeer));

        roleRepository.saveAll(Arrays.asList(adminRole,customerRole,userRole));

        userRepository.save(User.builder()
                .userName("spring")
                        .password(passwordEncoder.encode("password"))
                        .role(adminRole)
                .build());
        userRepository.save(User.builder()
                .userName("jose")
                .password(passwordEncoder.encode("Calamardo"))
                .role(adminRole)
                .build());
        userRepository.save(User.builder()
                .userName("scot")
                .password(passwordEncoder.encode("tiger"))
                .role(adminRole)
                .build());
        userRepository.save(User.builder()
                .userName("userRole")
                .password(passwordEncoder.encode("password"))
                .role(userRole)
                .build());
        userRepository.save(User.builder()
                .userName("scott")
                .password(passwordEncoder.encode("tiger"))
                .role(customerRole)
                .build());
        log.debug("Users loaded: {}", userRepository.count());
    }
}
