package service;

import annotation.Service;
import repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void sayHello() {
        userRepository.sayHello();
        System.out.println("Hello, I am UserService!");
    }
}
