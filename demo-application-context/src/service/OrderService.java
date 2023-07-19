package service;

import annotation.Autowired;
import annotation.Service;
import context.InitializingBean;
import repository.OrderRepository;

@Service
public class OrderService implements InitializingBean {
    @Autowired
    private OrderRepository orderRepository;
    public void sayHello() {
        orderRepository.sayHello();
        System.out.println("Hello, I am OrderService!");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("OrderService coming!");
    }
}