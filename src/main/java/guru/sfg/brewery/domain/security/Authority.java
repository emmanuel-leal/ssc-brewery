package guru.sfg.brewery.domain.security;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@Getter
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String role;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
}
