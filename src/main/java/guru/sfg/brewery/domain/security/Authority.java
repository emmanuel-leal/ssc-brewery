package guru.sfg.brewery.domain.security;

import lombok.Builder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String role;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
}