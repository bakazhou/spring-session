import context.MyApplicationContext;
import service.OrderService;
import service.UserService;

public class Main {
    public static void main(String[] args) {
//        UserRepository userRepository = new UserRepository();
//        OrderRepository orderRepository = new OrderRepository();
//        UserService userService = new UserService(userRepository, null);
//        OrderService orderService = new OrderService(orderRepository, userService);
//
//        userService = new UserService(userRepository, orderService);
//
//        userService.sayHello();
//        userService.callOrderService();
//
//        orderService.sayHello();
//        orderService.callUserService();

        MyApplicationContext context = new MyApplicationContext();

        UserService userService = context.getBean(UserService.class);

        OrderService orderService = (OrderService) context.getBean("OrderService");

        userService.sayHello();
        userService.callOrderService();

        orderService.sayHello();
        orderService.callUserService();
    }
}