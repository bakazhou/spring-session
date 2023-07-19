package service;

import annotation.Service;
import context.DisposableBean;
import repository.UserRepository;

@Service
public class UserService implements DisposableBean {

    private final UserRepository userRepository;
    private final OrderService orderService;

    public UserService(final UserRepository userRepository,
                       final OrderService orderService) {
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    public void sayHello() {
        System.out.println("=============");
        userRepository.sayHello();
        System.out.println("Hello, I am UserService!");
        orderService.sayHello();
        System.out.println("=============");
    }

    @Override
    public void destroy() {
        System.out.println("UserService gone!");
    }
}
