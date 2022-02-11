package fr.eql.al36.spring.projet.eqlexchange.controller;

import fr.eql.al36.spring.projet.eqlexchange.domain.User;
import fr.eql.al36.spring.projet.eqlexchange.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    public ResponseEntity<User> displayDashboard(@RequestParam(value = "email") String email) {
        User user = userService.findUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/newUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.addNewUser(user);
        return  new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping("/exist")
    public Boolean checkExistEmail(@RequestParam(value = "email") String email) {
        User user = userService.findUserByEmail(email);
        if(user != null) {
            return true;
        }
        return false;
    }

    @GetMapping("access-denied")
    public String accessDenied(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String connectedUserEmail = userDetails.getUsername();
        User connectedUser = userService.findUserByEmail(connectedUserEmail);
        model.addAttribute("sessionUser", connectedUser);
        return "access-denied";
    }

}
