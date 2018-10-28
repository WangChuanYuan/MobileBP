import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import po.Client;
import po.Operation;
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
import util.OrderStatus;
import util.ResultMsg;
import vo.Bill;
import vo.PackDetail;

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


    /**
     * 可以进行的操作
     * 具体说明见方法注释或说明文档
     */
    public static void main(String[] args) {
        initialize();
//        addClient();
//        addPack();
//        call();
//        sendMessage();
//        useLocalData();
//        useGenData();
//        genBill();
//        genDetails();
//        getOrderedPacks();
//        order();
//        cancelOrder();
//        preCancelOrder();
//        getPackHistory();
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

    /**
     * 添加一名新的用户，注意相同手机号客户只能存在一位
     * 手机号123456789
     */
    private static void addClient() {
        long start = System.currentTimeMillis();
        System.out.println("添加新的客户");
        Client client = new Client("123456789", "wcy", 10000);
        ResultMsg resultMsg = clientService.addClient(client);
        if (resultMsg == ResultMsg.SUCCESS) {
            System.out.println("添加成功");
            client.describe();
        } else System.out.println("添加失败，该客户已存在");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 添加一个新的套餐包
     * 示例的套餐为1号套餐，20元可以免费通话100分钟通话，发送20条短信，使用100M本地流量，使用100M全国流量
     */
    private static void addPack() {
        long start = System.currentTimeMillis();
        System.out.println("添加新的套餐");
        List<Plan> plans = new ArrayList<>();
        plans.add(new Plan(100, FeeType.CALL));
        plans.add(new Plan(20, FeeType.MESSAGE));
        plans.add(new Plan(100, FeeType.LOCAL_DATA));
        plans.add(new Plan(100, FeeType.GEN_DATA));
        Pack pack = new Pack("基础套餐", 20, plans);
        ResultMsg resultMsg = packService.addPack(pack);
        if (resultMsg == ResultMsg.SUCCESS) {
            System.out.println("添加成功");
            pack.describe();
        } else System.out.println("添加失败");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 为123456789订购1号套餐，注意同一套餐相同用户只能同时订购一个
     */
    private static void order() {
        long start = System.currentTimeMillis();
        String phoneNo = "123456789";
        long pid = 1;
        System.out.println("订购套餐");
        System.out.println("手机号：" + phoneNo);
        System.out.println("套餐ID：" + pid);
        ResultMsg resultMsg = orderService.orderPack(phoneNo, pid);
        if (resultMsg == ResultMsg.SUCCESS) {
            System.out.println("订购成功");
            Client client = clientService.getClientByPN(phoneNo);
            Pack pack = packService.getPackByPid(pid);
            System.out.println("扣除月功能费" + pack.getFee() + "元");
            System.out.println("余额" + client.getRemain() + "元");
        } else System.out.println("订购失败，不可重复订购");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 为123456789立即退订2号套餐，则会重新计费本月的消费，退还套餐费，并要求补交多余的费用
     * 注意如果用户拥有其他套餐，并且本月消费仍在套餐免费额度内，可能只需退还套餐费，不用补交费用
     */
    private static void cancelOrder() {
        long start = System.currentTimeMillis();
        String phoneNo = "123456789";
        long pid = 2;
        System.out.println("退订套餐且立即生效");
        System.out.println("手机号：" + phoneNo);
        System.out.println("套餐ID：" + pid);
        double remainBeforeCancel = clientService.getClientByPN(phoneNo).getRemain();
        System.out.println("退订前余额：" + remainBeforeCancel + "元");
        ResultMsg resultMsg = orderService.cancelPack(phoneNo, pid, OrderStatus.CANCEL);
        if (resultMsg == ResultMsg.SUCCESS) {
            System.out.println("退订成功，即刻生效，已重新计算本月消费，并退还/收取费用");
            double remainAfterCancel = clientService.getClientByPN(phoneNo).getRemain();
            System.out.println("退订后余额" + remainAfterCancel + "元");
        } else System.out.println("退订失败");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 为123456789次月退订1号套餐
     */
    private static void preCancelOrder() {
        long start = System.currentTimeMillis();
        String phoneNo = "123456789";
        long pid = 1;
        System.out.println("退订套餐且下月生效");
        System.out.println("手机号：" + phoneNo);
        System.out.println("套餐ID：" + pid);
        ResultMsg resultMsg = orderService.cancelPack(phoneNo, pid, OrderStatus.CANCEL);
        if (resultMsg == ResultMsg.SUCCESS) {
            System.out.println("退订成功，下月生效");
        } else System.out.println("退订失败");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 得到123456789目前订购的套餐（包括订购的和次月取消的）
     */
    private static void getOrderedPacks() {
        long start = System.currentTimeMillis();
        String phoneNo = "123456789";
        System.out.println("查询已购套餐");
        System.out.println("手机号：" + phoneNo);
        List<PackDetail> packs = orderService.getOrderedPacks(phoneNo);
        for (PackDetail pack : packs)
            pack.describe();
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 得到123456789套餐历史（包括已经取消的套餐）
     */
    private static void getPackHistory() {
        long start = System.currentTimeMillis();
        String phoneNo = "123456789";
        System.out.println("查询套餐历史");
        System.out.println("手机号：" + phoneNo);
        List<PackDetail> packs = orderService.getPackHistory(phoneNo);
        for (PackDetail pack : packs)
            pack.describe();
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 123456789通话，记录下此次通话费用，如果在免费额度内，则费用为0
     */
    private static void call() {
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

    /**
     * 123456789发送短信，记录下此次短信费用，如果在免费额度内，则费用为0
     */
    private static void sendMessage() {
        long start = System.currentTimeMillis();
        String phoneNo = "123456789";
        LocalDateTime startTime = LocalDateTime.now();
        System.out.println("发送短信");
        System.out.println("手机号：" + phoneNo);
        double fee = msgService.sendMsg(phoneNo, startTime);
        System.out.println("短信费用：" + fee + "元");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 123456789使用本地流量，记录下此次流量费用，如果在免费额度内，则费用为0
     */
    private static void useLocalData() {
        long start = System.currentTimeMillis();
        String phoneNo = "123456789";
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(5);
        double useLen = 20;
        System.out.println("使用本地流量");
        System.out.println("手机号：" + phoneNo);
        double fee = dataService.useData(phoneNo, startTime, endTime, useLen, FeeType.LOCAL_DATA);
        System.out.println("使用本地流量：" + useLen + "M");
        System.out.println("本地流量费用：" + fee + "元");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 123456789使用全国流量，记录下此次流量费用，如果在免费额度内，则费用为0
     */
    private static void useGenData() {
        long start = System.currentTimeMillis();
        String phoneNo = "123456789";
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(5);
        double useLen = 130;
        System.out.println("使用全国流量");
        System.out.println("手机号：" + phoneNo);
        double fee = dataService.useData(phoneNo, startTime, endTime, useLen, FeeType.GEN_DATA);
        System.out.println("使用全国流量：" + useLen + "M");
        System.out.println("全国流量费用：" + fee + "元");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 生成123456789的2018年10月的账单，包括功能费，通话时间，短信条数，本地流量用量，全国流量用量，通话费，短信费，本地流量费，全国流量费
     */
    private static void genBill() {
        long start = System.currentTimeMillis();
        String phoneNo = "123456789";
        int year = 2018;
        int month = 10;
        System.out.println("生成" + year + "年" + month + "月账单");
        System.out.println("手机号：" + phoneNo);
        Bill bill = billService.getBillOf(phoneNo, 2018, 10);
        bill.describe();
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 生成123456789的2018年10月的账单明细，详细给出每一笔通话费，短信费，本地流量费，全国流量费的使用情况
     */
    private static void genDetails() {
        long start = System.currentTimeMillis();
        String phoneNo = "123456789";
        int year = 2018;
        int month = 10;
        System.out.println("生成" + year + "年" + month + "月使用详情");
        System.out.println("手机号：" + phoneNo);
        System.out.println("通话使用详情：");
        List<Operation> callOperations = billService.getDetailsOf(phoneNo, 2018, 10, FeeType.CALL);
        for (Operation operation : callOperations)
            operation.describe();
        System.out.println("短信发送详情：");
        List<Operation> msgOperations = billService.getDetailsOf(phoneNo, 2018, 10, FeeType.MESSAGE);
        for (Operation operation : msgOperations)
            operation.describe();
        System.out.println("本地流量使用详情：");
        List<Operation> localDataOperations = billService.getDetailsOf(phoneNo, 2018, 10, FeeType.LOCAL_DATA);
        for (Operation operation : localDataOperations)
            operation.describe();
        System.out.println("全国流量使用详情：");
        List<Operation> genDataOperations = billService.getDetailsOf(phoneNo, 2018, 10, FeeType.GEN_DATA);
        for (Operation operation : genDataOperations)
            operation.describe();
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

}
