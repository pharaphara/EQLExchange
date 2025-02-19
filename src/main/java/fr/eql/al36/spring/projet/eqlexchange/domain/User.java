package fr.eql.al36.spring.projet.eqlexchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String walletAddress;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_authority", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private Set<Authority> authorities;


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName,
                                                                                                          user.lastName) &&
               Objects.equals(username, user.username) && Objects.equals(dateOfBirth, user.dateOfBirth) &&
               Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(
                walletAddress, user.walletAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, dateOfBirth, email, password, walletAddress);
    }



}
