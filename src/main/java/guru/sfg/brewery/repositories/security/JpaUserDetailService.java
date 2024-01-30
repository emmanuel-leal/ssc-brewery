package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JpaUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUserName(username).orElseThrow(()->{
           return  new UsernameNotFoundException("User name: "+username+" not found");
        });
        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),user.getEnabled(),
                user.getAccountNonExpired(),user.getCredentialsNonExpired(),user.getAccountNonLocked(),convertToSprintAuthorities(user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> convertToSprintAuthorities(Set<Authority> authorities) {
        if(authorities != null && !authorities.isEmpty()){
            return authorities.stream()
                    .map(Authority::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }else{
            return new HashSet<>();
        }
    }
}
