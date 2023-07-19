import context.ApplicationContext;
import context.MyApplicationContext;
import service.OrderService;
import service.UserService;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new MyApplicationContext();

        UserService userService = context.getBean(UserService.class);
        userService.sayHello();

        OrderService orderService = (OrderService) context.getBean("OrderService");
        orderService.sayHello();
    }
}