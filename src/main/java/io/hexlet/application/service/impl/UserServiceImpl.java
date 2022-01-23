package io.hexlet.application.service.impl;

import io.hexlet.application.dto.UserEditDto;
import io.hexlet.application.dto.UserRegistrationDto;
import io.hexlet.application.entity.User;
import io.hexlet.application.repository.UserRepository;
import io.hexlet.application.service.UserService;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(final UserRepository userRepository,
                           final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(final UserRegistrationDto dto) {
        final User user = new User(
                dto.email(),
                dto.firstName(),
                passwordEncoder.encode(dto.password())
        );
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(final Long id, final UserEditDto dto) {
        return userRepository.findById(id).map(user -> {
            user.setEmail(dto.email());
            user.setFirstName(dto.firstName());
            return userRepository.save(user);
        }).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(this::toSpringUser)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user with email: " + username));
    }

    private org.springframework.security.core.userdetails.User toSpringUser(final User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("USER"))
        );

    }
}
