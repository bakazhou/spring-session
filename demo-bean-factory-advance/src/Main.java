import factory.MyBeanFactory;
import service.OrderService;
import service.UserService;

public class Main {
    public static void main(String[] args) {
        MyBeanFactory beanFactory = new MyBeanFactory();

        UserService userService = (UserService) beanFactory.getBean("UserService");
        userService.sayHello();

        OrderService orderService = (OrderService) beanFactory.getBean("OrderService");
        orderService.placeOrder();
    }
}