package com.prepfortech.service;

import com.prepfortech.accessor.AuthAccessor;
import com.prepfortech.accessor.UserAccessor;
import com.prepfortech.accessor.model.UserDTO;
import com.prepfortech.exception.DependencyFailureException;
import com.prepfortech.exception.InvalidCredentailsException;
import com.prepfortech.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import javax.xml.ws.Response;
import java.util.Date;

@Component
public class AuthService {

    @Autowired
    private UserAccessor userAccessor;

    @Autowired
    private AuthAccessor authAccessor;

    /**
     *
     * @param email : Email of the user who wnats to login
     * @param password : password of the user who wnats to login
     * @return : jwt token if the email and password combination is correct
     */

    public String login(final String email , final String password){
        UserDTO userDTO = userAccessor.getUserByEmail(email);
        if (userDTO != null && userDTO.getPassword().equals(password)) {
            //Generate and store the token
            String token = Jwts.builder()
                    .setSubject(email)
                    .setAudience(userDTO.getRole().name())
                    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_KEY)
                    .compact();

            authAccessor.storeToken(userDTO.getUserId(), token);
            return token;
        }
        throw new InvalidCredentailsException("Either the email or password is incorrect");
    }
}
