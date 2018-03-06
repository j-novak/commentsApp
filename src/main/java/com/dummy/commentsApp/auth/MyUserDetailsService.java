package com.dummy.commentsApp.auth;

import com.dummy.commentsApp.model.ApplicationUser;
import com.dummy.commentsApp.repository.ApplicationUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    public MyUserDetailsService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserContext(
                applicationUser.getUsername(), applicationUser.getPassword(), applicationUser.isEnabled(),
                true, true,
                true, new ArrayList<>());
    }
}
