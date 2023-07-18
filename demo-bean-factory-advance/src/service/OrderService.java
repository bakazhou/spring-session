package service;

import annotation.Bean;

@Bean
public class OrderService {
    public void placeOrder() {
        System.out.println("Placing order...");
    }
}