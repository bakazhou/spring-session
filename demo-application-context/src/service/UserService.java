package service;

import annotation.Service;
import context.DisposableBean;
import repository.UserRepository;

@Service
public class UserService implements DisposableBean {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void sayHello() {
        userRepository.sayHello();
        System.out.println("Hello, I am UserService!");
    }

    @Override
    public void destroy() {
        System.out.println("UserService gone!");
    }
}
