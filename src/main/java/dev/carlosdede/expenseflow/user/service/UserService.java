package dev.carlosdede.expenseflow.user.service;

import dev.carlosdede.expenseflow.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


}
