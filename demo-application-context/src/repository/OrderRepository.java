package repository;

import annotation.Repository;

@Repository
public class OrderRepository {
    public void sayHello() {
        System.out.println("Hello, I am OrderRepository!");
    }
}
