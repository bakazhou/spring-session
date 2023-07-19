package repository;

import annotation.Repository;

@Repository
public class UserRepository {
    public void sayHello() {
        System.out.println("Hello, I am UserRepository!");
    }
}
