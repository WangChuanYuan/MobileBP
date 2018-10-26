import dao.OrderDAO;
import dao.OrderDAOImpl;
import entity.Order;
import entity.OrderStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        OrderDAO orderDAO = context.getBean(OrderDAOImpl.class);
        Order order = new Order(1, "123", 1, LocalDateTime.now(), OrderStatus.ORDER);
        orderDAO.save(order);
    }

}
