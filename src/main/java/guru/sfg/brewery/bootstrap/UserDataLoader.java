package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {

        if( authorityRepository.count() == 0 ){
            loadsecurityData();
        }
    }

    private void loadsecurityData() {
        Authority admin= authorityRepository.save(Authority.builder().role("ADMIN").build());
        Authority userRole= authorityRepository.save(Authority.builder().role("USER").build());
        Authority customer= authorityRepository.save(Authority.builder().role("CUSTOMER").build());

        userRepository.save(User.builder()
                .userName("spring")
                        .password(passwordEncoder.encode("password"))
                        .authority(admin)
                .build());
        userRepository.save(User.builder()
                .userName("jose")
                .password(passwordEncoder.encode("Calamardo"))
                .authority(admin)
                .build());
        userRepository.save(User.builder()
                .userName("scot")
                .password(passwordEncoder.encode("tiger"))
                .authority(admin)
                .build());
        userRepository.save(User.builder()
                .userName("user")
                .password(passwordEncoder.encode("password"))
                .authority(userRole)
                .build());
        userRepository.save(User.builder()
                .userName("scott")
                .password(passwordEncoder.encode("tiger"))
                .authority(customer)
                .build());
        log.debug("Users loaded: {}", userRepository.count());
    }
}
