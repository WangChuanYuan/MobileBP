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
     * 所列均为模拟操作
     */
    public static void main(String[] args) {
        initialize();
        addClient("123456789", "wcy", 10000);
        addPack();
        call("123456789", 5);
        sendMessage("123456789");
        useLocalData("123456789", 20);
        useGenData("123456789", 130);
        genBill("123456789", 2018, 10);
        genDetails("123456789", 2018, 10);
        getOrderedPacks("123456789");
        order("123456789", 1);
        cancelOrder("123456789", 2);
        preCancelOrder("123456789", 1);
        getPackHistory("123456789");
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
     * @param phoneNo
     * 手机号
     * @param name
     * 用户名
     * @param remain
     * 余额
     */
    private static void addClient(String phoneNo, String name, double remain) {
        long start = System.currentTimeMillis();
        System.out.println("添加新的客户");
        Client client = new Client(phoneNo, name, remain);
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
     * 为123456789订购1号套餐并扣费，注意同一套餐相同用户只能同时订购一个
     * 重复订购与套餐不存在均会订购失败
     * @param phoneNo
     * 手机号
     * @param pid
     * 套餐id
     */
    private static void order(String phoneNo, long pid) {
        long start = System.currentTimeMillis();
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
        } else System.out.println("订购失败");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 为123456789立即退订2号套餐，则会重新计费本月的消费，退还套餐费，并要求补交多余的费用
     * 注意如果用户拥有其他套餐，并且本月消费仍在套餐免费额度内，可能只需退还套餐费，不用补交费用
     * 套餐未订购与套餐不存在均会退订失败
     * @param phoneNo
     * 手机号
     * @param pid
     * 套餐id
     */
    private static void cancelOrder(String phoneNo, long pid) {
        long start = System.currentTimeMillis();
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
     * 套餐未订购与套餐不存在均会退订失败
     * @param phoneNo
     * 手机号
     * @param pid
     * 套餐id
     */
    private static void preCancelOrder(String phoneNo, long pid) {
        long start = System.currentTimeMillis();
        System.out.println("退订套餐且下月生效");
        System.out.println("手机号：" + phoneNo);
        System.out.println("套餐ID：" + pid);
        ResultMsg resultMsg = orderService.cancelPack(phoneNo, pid, OrderStatus.PRECANCEL);
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
     * @param phoneNo
     * 手机号
     */
    private static void getOrderedPacks(String phoneNo) {
        long start = System.currentTimeMillis();
        System.out.println("查询已购套餐");
        System.out.println("手机号：" + phoneNo);
        List<PackDetail> packs = orderService.getOrderedPacksBefore(phoneNo, LocalDateTime.now());
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
     * @param phoneNo
     * 手机号
     */
    private static void getPackHistory(String phoneNo) {
        long start = System.currentTimeMillis();
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
     * 模拟通话时间为5分钟
     * @param phoneNo
     * 手机号
     * @param useLen
     * 通话时间
     */
    private static void call(String phoneNo, double useLen) {
        long start = System.currentTimeMillis();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes((long)useLen);
        System.out.println("通话");
        System.out.println("手机号：" + phoneNo);
        double fee = callService.call(phoneNo, startTime, endTime);
        if(fee < 0)
            System.out.println("无法通话");
        else {
            System.out.println("通话时间：" + useLen + "minutes");
            System.out.println("通话费用：" + fee + "元");
        }
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 123456789发送短信，记录下此次短信费用，如果在免费额度内，则费用为0
     * @param phoneNo
     * 手机号
     */
    private static void sendMessage(String phoneNo) {
        long start = System.currentTimeMillis();
        LocalDateTime startTime = LocalDateTime.now();
        System.out.println("发送短信");
        System.out.println("手机号：" + phoneNo);
        double fee = msgService.sendMsg(phoneNo, startTime);
        if(fee < 0)
            System.out.println("无法发送");
        else
            System.out.println("短信费用：" + fee + "元");
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 123456789使用本地流量，记录下此次流量费用，如果在免费额度内，则费用为0
     * 模拟使用流量20M
     * @param phoneNo
     * 手机号
     * @param useLen
     * 流量
     */
    private static void useLocalData(String phoneNo, double useLen) {
        long start = System.currentTimeMillis();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(5);
        System.out.println("使用本地流量");
        System.out.println("手机号：" + phoneNo);
        double fee = dataService.useData(phoneNo, startTime, endTime, useLen, FeeType.LOCAL_DATA);
        if(fee < 0)
            System.out.println("无法使用");
        else {
            System.out.println("使用本地流量：" + useLen + "M");
            System.out.println("本地流量费用：" + fee + "元");
        }
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 123456789使用全国流量，记录下此次流量费用，如果在免费额度内，则费用为0
     * 模拟使用流量130M
     * @param phoneNo
     * 手机号
     * @param useLen
     * 流量
     */
    private static void useGenData(String phoneNo, double useLen) {
        long start = System.currentTimeMillis();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(5);
        System.out.println("使用全国流量");
        System.out.println("手机号：" + phoneNo);
        double fee = dataService.useData(phoneNo, startTime, endTime, useLen, FeeType.GEN_DATA);
        if(fee < 0)
            System.out.println("无法使用");
        else {
            System.out.println("使用全国流量：" + useLen + "M");
            System.out.println("全国流量费用：" + fee + "元");
        }
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 生成123456789的2018年10月的账单，包括功能费，通话时间，短信条数，本地流量用量，全国流量用量，通话费，短信费，本地流量费，全国流量费
     * @param phoneNo
     * 手机号
     * @param year
     * 年份
     * @param month
     * 月份
     */
    private static void genBill(String phoneNo, int year, int month) {
        long start = System.currentTimeMillis();
        System.out.println("生成" + year + "年" + month + "月账单");
        System.out.println("手机号：" + phoneNo);
        Bill bill = billService.getBillOf(phoneNo, year, month);
        bill.describe();
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

    /**
     * 生成123456789的2018年10月的账单明细，详细给出每一笔通话费，短信费，本地流量费，全国流量费的使用情况
     * @param phoneNo
     * 手机号
     * @param year
     * 年份
     * @param month
     * 月份
     */
    private static void genDetails(String phoneNo, int year, int month) {
        long start = System.currentTimeMillis();
        System.out.println("生成" + year + "年" + month + "月使用详情");
        System.out.println("手机号：" + phoneNo);
        System.out.println("通话使用详情：");
        List<Operation> callOperations = billService.getDetailsOf(phoneNo, year, month, FeeType.CALL);
        for (Operation operation : callOperations)
            operation.describe();
        System.out.println("短信发送详情：");
        List<Operation> msgOperations = billService.getDetailsOf(phoneNo, year, month, FeeType.MESSAGE);
        for (Operation operation : msgOperations)
            operation.describe();
        System.out.println("本地流量使用详情：");
        List<Operation> localDataOperations = billService.getDetailsOf(phoneNo, year, month, FeeType.LOCAL_DATA);
        for (Operation operation : localDataOperations)
            operation.describe();
        System.out.println("全国流量使用详情：");
        List<Operation> genDataOperations = billService.getDetailsOf(phoneNo, year, month, FeeType.GEN_DATA);
        for (Operation operation : genDataOperations)
            operation.describe();
        System.out.println("---------------");
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0 + "seconds");
        System.out.println("---------------");
        System.out.println();
    }

}
