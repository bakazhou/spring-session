package service;

import annotation.Autowired;
import annotation.Service;
import repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public void sayHello() {
        orderRepository.sayHello();
        System.out.println("Hello, I am OrderService!");
    }
}