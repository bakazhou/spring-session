package service;

import annotation.Service;
import context.InitializingBean;
import repository.OrderRepository;

@Service
public class OrderService implements InitializingBean {
    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderService(final OrderRepository orderRepository,
                        final UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public void sayHello() {
        System.out.println("=============");
        orderRepository.sayHello();
        System.out.println("Hello, I am OrderService!");
        System.out.println("=============");
    }

    public void callUserService() {
        userService.sayHello();
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("OrderService coming!");
    }
}