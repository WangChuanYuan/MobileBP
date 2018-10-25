package entity;

public class BaseFee {

    public static final double[] FEE;

    static {
        FEE = new double[FeeType.values().length];
        //通话基准资费0.5元/分钟
        FEE[FeeType.CALL.ordinal()] = 0.5;
        //短信基准资费0.1元/条
        FEE[FeeType.MESSAGE.ordinal()] = 0.1;
        //本地流量基准资费2元/M
        FEE[FeeType.LOCAL_DATA.ordinal()] = 2;
        //全国流量基准资费5元/M
        FEE[FeeType.GEN_DATA.ordinal()] = 5;
    }
}
