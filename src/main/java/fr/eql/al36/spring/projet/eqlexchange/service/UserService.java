package fr.eql.al36.spring.projet.eqlexchange.service;

import fr.eql.al36.spring.projet.eqlexchange.domain.Authority;
import fr.eql.al36.spring.projet.eqlexchange.domain.User;
import fr.eql.al36.spring.projet.eqlexchange.repository.AuthorityRepository;
import fr.eql.al36.spring.projet.eqlexchange.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addNewUser(User user) {
        if (findUserByEmail(user.getEmail()) == null) {

            String role = "ROLE_USER";
            Authority authority = authorityRepository.findByRole(role);
            Set<Authority> authorities = new HashSet<>();
            authorities.add(authority);
            user.setAuthorities(authorities);

            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));

            String walletAddress = ("0x" + UUID.randomUUID()).replaceAll("-","");
            user.setWalletAddress(walletAddress);

            return userRepository.save(user);

        }
        return null;
    }

    public User findUserByEmail(String email) {
        if(userRepository.findByEmail(email).isPresent()){
            return userRepository.findByEmail(email).get();
        }
        return null;
    }

}
