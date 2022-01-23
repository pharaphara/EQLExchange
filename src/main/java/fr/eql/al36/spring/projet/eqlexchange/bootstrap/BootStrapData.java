package fr.eql.al36.spring.projet.eqlexchange.bootstrap;

import fr.eql.al36.spring.projet.eqlexchange.domain.*;
import fr.eql.al36.spring.projet.eqlexchange.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class BootStrapData implements CommandLineRunner {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;


    public BootStrapData(UserRepository userRepository,
                         AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {
        initData();
    }


    private void initData() {


        ////////////////////////////////
        // USERS ROLES
        ////////////////////////////////

        Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
        Authority userRole = authorityRepository.save(Authority.builder().role("ROLE_USER").build());

        Set<Authority> adminSet = new HashSet<>();
        adminSet.add(adminRole);
        Set<Authority> userSet = new HashSet<>();
        userSet.add(userRole);

        ////////////////////////////////
        // USERS
        ////////////////////////////////

       userRepository.save(User.builder().firstName("EQL").lastName("Exchange").username("EQLExchange")
                                                 .dateOfBirth(LocalDate.of(2014, 6, 1)).email("bigbux@eqlexchange.io")
                                                 .password(passwordEncoder.encode("admin")).walletAddress(
                        "EQL_0F887AC6986B00BBDE4AA91A0B82430A782E93603973AE81A6EC1041789EDA94").authorities(adminSet)
                                                 .build());

        userRepository.save(User.builder().firstName("Alain").lastName("Musque").username("Alain_420")
                                                 .dateOfBirth(LocalDate.of(2016, 6, 1)).email("alain.musque@yahoo.fr")
                                                 .password(passwordEncoder.encode("toto")).walletAddress(
                        "EQL_DBF8B58DEC30242DD3E1A64331B9DACDB58CFA0F7742AA47E0984CF4098997AB").authorities(userSet)
                                                 .build());

        userRepository.save(User.builder().firstName("Anne-Sophie").lastName("Ladouille").username(
                "douilledu13").dateOfBirth(LocalDate.of(2019, 4, 1)).email("douilledu13@yopmail.com").password(
                passwordEncoder.encode("douilledouilledouille")).walletAddress(
                "EQL_DEBD5C88C70C54820665D03373F1DB3EFE45551F5D3856EDD6A9EAC7920435D7").authorities(userSet).build());

        userRepository.save(User.builder().firstName("Robert").lastName("Pushard").username(
                "pusher_bobby").dateOfBirth(LocalDate.of(2020, 11, 8)).email("pouchard11@numericable.fr").password(
                passwordEncoder.encode("jesusmarietf1")).walletAddress(
                "EQL_DEBD5C88C70C54820665D03373F1DB3EFE45551F5D3856EDD6A9EAC7920435D9").authorities(userSet).build());

    }
}
