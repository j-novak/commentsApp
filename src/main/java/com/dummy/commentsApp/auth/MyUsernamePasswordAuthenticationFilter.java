package com.dummy.commentsApp.auth;

import com.dummy.commentsApp.dto.LoginResponseDTO;
import com.dummy.commentsApp.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import static com.dummy.commentsApp.auth.SecurityConstants.EXPIRATION_TIME;
import static com.dummy.commentsApp.auth.SecurityConstants.SECRET;


public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public MyUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            UserDTO credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), UserDTO.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        UserContext principal = (UserContext) auth.getPrincipal();
        Claims claims = Jwts.claims().setSubject(principal.getUsername());
        claims.put("scopes", auth.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

        Date tokenExpirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(tokenExpirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();

        LoginResponseDTO userDto = new LoginResponseDTO();
        userDto.setEmail(principal.getUsername());
        userDto.setToken(token);
        userDto.setExpirationDate(tokenExpirationDate);
        new ObjectMapper().writeValue(res.getWriter(), userDto);
    }
}
