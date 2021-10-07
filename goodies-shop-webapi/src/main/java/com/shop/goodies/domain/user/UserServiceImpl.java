package com.shop.goodies.domain.user;

import com.shop.goodies.common.DomainException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final HashGenerator hashGenerator;


    @Override
    public User createUser(UserForm userForm) {

        Optional<User> optUser = userRepository.findByEmail(userForm.getEmail());

        if (optUser.isPresent()) {
            throw new DomainException("User already exists");
        }


        User user = User.builder().email(userForm.getEmail())
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .password(hashGenerator.hash(userForm.getPassword()))
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}