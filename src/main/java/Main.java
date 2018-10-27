import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import po.Client;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.operation.*;
import service.order.OrderService;
import service.order.OrderServiceImpl;
import service.pack.PackService;
import service.pack.PackServiceImpl;
import util.ResultMsg;

public class Main {

    private static BillService billService;
    private static CallService callService;
    private static MsgService msgService;
    private static DataService dataService;
    private static OrderService orderService;
    private static PackService packService;
    private static ClientService clientService;


    public static void main(String[] args) {
        initialize();
        addClient();
    }

    private static void initialize() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        billService = context.getBean("billService", BillServiceImpl.class);
        callService = context.getBean("callService", CallServiceImpl.class);
        msgService = context.getBean("msgService", MsgServiceImpl.class);
        dataService = context.getBean("dataService", DataServiceImpl.class);
        orderService = context.getBean("orderService", OrderServiceImpl.class);
        packService = context.getBean("packService", PackServiceImpl.class);
        clientService = context.getBean("clientService", ClientServiceImpl.class);
    }

    private static void addClient() {
        long start = System.currentTimeMillis();
        System.out.println("添加新的客户");
        Client client = new Client("123456789", "wcy", 10000);
        ResultMsg resultMsg = clientService.addClient(client);
        if(resultMsg == ResultMsg.SUCCESS) {
            System.out.println("添加成功");
            System.out.println("手机号：" + client.getPhoneNo());
            System.out.println("客户名：" + client.getName());
            System.out.println("余额：" + client.getRemain());
        }
        else System.out.println("添加失败，该客户已存在");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    private static void addPack() {

    }

}
