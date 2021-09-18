package io.github.nhannht.webquizzangular.securityconfig;

import io.github.nhannht.webquizzangular.entity.User;
import io.github.nhannht.webquizzangular.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository users;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = users.findByEmail(email);
        if (user.isEmpty())
            throw new UsernameNotFoundException(email);

        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                new ArrayList<>()
        );
    }
}
