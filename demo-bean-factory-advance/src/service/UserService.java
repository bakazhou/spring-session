package service;

import annotation.Bean;

@Bean
public class UserService {
    public void sayHello() {
        System.out.println("Hello, I am UserService!");
    }
}
