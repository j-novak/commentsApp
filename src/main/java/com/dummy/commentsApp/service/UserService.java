package com.dummy.commentsApp.service;

import com.dummy.commentsApp.dto.UserDTO;
import com.dummy.commentsApp.exceptions.UserAlreadyExistException;
import com.dummy.commentsApp.model.ApplicationUser;
import com.dummy.commentsApp.repository.ApplicationUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private final ApplicationUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(ApplicationUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewUserAccount(UserDTO userDto) throws UserAlreadyExistException {
        if (usernameExist(userDto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + userDto.getUsername());
        }
        final ApplicationUser user = new ApplicationUser();

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setEnabled(true);
        userRepository.save(user);
    }

    private boolean usernameExist(final String username) {
        return userRepository.findByUsername(username) != null;
    }
}
