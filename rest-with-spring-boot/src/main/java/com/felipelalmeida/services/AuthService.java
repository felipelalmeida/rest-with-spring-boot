package com.felipelalmeida.services;

import com.felipelalmeida.data.dto.PersonDTO;
import com.felipelalmeida.data.dto.security.AccountCredentialsDTO;
import com.felipelalmeida.data.dto.security.TokenDTO;
import com.felipelalmeida.exception.RequiredObjectIsNullException;
import com.felipelalmeida.model.Person;
import com.felipelalmeida.model.User;
import com.felipelalmeida.repository.UserRepository;
import com.felipelalmeida.security.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.felipelalmeida.mapper.ObjectMapper.parseObject;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    public ResponseEntity<TokenDTO> signIn(AccountCredentialsDTO credentials){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUserName(),
                        credentials.getPassword()
                )
        );

        var user = repository.findByUserName(credentials.getUserName());
        if (user == null) throw new UsernameNotFoundException("Username " + credentials.getUserName() + " not found!");

        var token = tokenProvider.createAccessToken(
                credentials.getUserName(),
                user.getRoles()
        );

        return ResponseEntity.ok(token);
    }

    public ResponseEntity<TokenDTO> refreshToken(String userName, String refreshToken) {

        TokenDTO token;
        var user = repository.findByUserName(userName);
        if (user != null) {
            token = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + userName + " not found!");
        }

        return ResponseEntity.ok(token);
    }

    public AccountCredentialsDTO create(AccountCredentialsDTO user) {
        if (user == null) throw new RequiredObjectIsNullException();
        logger.info("Creating one new user!");
        var entity = new User();
        entity.setFullName(user.getFullName());
        entity.setUserName(user.getUserName());
        entity.setPassword(generateHashPassword(user.getPassword()));
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setEnabled(true);
        return parseObject(repository.save(entity), AccountCredentialsDTO.class);
    }


    private String generateHashPassword(String password) {
        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdf2Encoder);

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        return passwordEncoder.encode(password);

    }

}
