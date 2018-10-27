import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import po.Client;
import po.Pack;
import po.Plan;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.operation.*;
import service.order.OrderService;
import service.order.OrderServiceImpl;
import service.pack.PackService;
import service.pack.PackServiceImpl;
import util.FeeType;
import util.ResultMsg;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        addPack();
        order();
        call();
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
            client.describe();
        }
        else System.out.println("添加失败，该客户已存在");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    private static void addPack() {
        long start = System.currentTimeMillis();
        System.out.println("添加新的套餐");
        List<Plan> plans = new ArrayList<>();
        plans.add(new Plan(100, FeeType.CALL));
        Pack pack = new Pack("基础话费套餐", 20, plans);
        ResultMsg resultMsg = packService.addPack(pack);
        if(resultMsg == ResultMsg.SUCCESS) {
            System.out.println("添加成功");
            pack.describe();
        }
        else System.out.println("添加失败");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    private static void order(){
        long start = System.currentTimeMillis();
        String phoneNo = "123456789";
        long pid = 1;
        System.out.println("订购套餐");
        System.out.println("手机号：" + phoneNo);
        System.out.println("套餐ID：" + pid);
        ResultMsg resultMsg = orderService.orderPack(phoneNo, pid);
        if(resultMsg == ResultMsg.SUCCESS) {
            System.out.println("订购成功");
            Client client = clientService.getClientByPN(phoneNo);
            Pack pack = packService.getPackByPid(pid);
            System.out.println("扣除月功能费" + pack.getFee() + "元");
            System.out.println("余额" + client.getRemain() + "元");
        }
        else System.out.println("订购失败，不可重复订购");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    private static void call(){
        long start = System.currentTimeMillis();
        String phoneNo = "123456789";
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(5);
        System.out.println("通话");
        System.out.println("手机号：" + phoneNo);
        double fee = callService.call(phoneNo, startTime, endTime);
        double useLen = Duration.between(startTime, endTime).toMinutes();
        System.out.println("通话时间：" + useLen + "minutes");
        System.out.println("通话费用：" + fee + "元");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

}
