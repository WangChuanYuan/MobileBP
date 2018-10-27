import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.operation.BillService;
import service.operation.CallService;
import service.operation.DataService;
import service.operation.MsgService;
import service.order.OrderService;
import service.pack.PackService;

public class Main {

    private BillService billService;

    private CallService callService;

    private MsgService msgService;

    private DataService dataService;

    private OrderService orderService;

    private PackService packService;

    public static void initialize() {

    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

}
