package fr.eql.al36.spring.projet.eqlexchange.controller;

import java.util.ArrayList;
import java.util.List;

import fr.eql.al36.spring.projet.eqlexchange.domain.AuthenticationRequest;
import fr.eql.al36.spring.projet.eqlexchange.domain.UserTransfer;
import fr.eql.al36.spring.projet.eqlexchange.security.JpaUserDetailsService;
import fr.eql.al36.spring.projet.eqlexchange.security.JwtUtil;
import fr.eql.al36.spring.projet.eqlexchange.security.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("jpaUserDetailsService")
    private JpaUserDetailsService customUserDetailsService;




    @RequestMapping(value = "/authenticate", method = { RequestMethod.POST })
    @ApiOperation(value = "authenticate")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserTransfer.class),
            @ApiResponse(code = 403, message = SecurityConstant.FORBIDDEN),
            @ApiResponse(code = 422, message = SecurityConstant.USER_NOT_FOUND),
            @ApiResponse(code = 417, message = SecurityConstant.EXCEPTION_FAILED) })
    public ResponseEntity<UserTransfer> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            String username = authenticationRequest.getEmail();
            String password = authenticationRequest.getPassword();

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = this.authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            List<String> roles = new ArrayList();

            for (GrantedAuthority authority : userDetails.getAuthorities()) {
                roles.add(authority.toString());
            }

            return new ResponseEntity<>(new UserTransfer(userDetails.getUsername(), roles,
                    JwtUtil.createToken(userDetails), HttpStatus.OK), HttpStatus.OK);

        } catch (BadCredentialsException bce) {
            return new ResponseEntity<>(new UserTransfer(), HttpStatus.UNPROCESSABLE_ENTITY);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

    }

}
