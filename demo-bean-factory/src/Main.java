import factory.MyBeanFactory;
import service.UserService;

public class Main {
    public static void main(String[] args) {
        MyBeanFactory beanFactory = new MyBeanFactory();

        beanFactory.registerBean("userService", new UserService());

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.sayHello();
    }
}