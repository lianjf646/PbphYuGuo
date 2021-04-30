package com.pbph.yuguo.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/7/20.
 */

public final class MoneyHelper {
    private BigDecimal money;
    private MoneyUnit unit;

    public static MoneyHelper getInstance4Yuan(Object object) {
        return getInstance().initByYuan(object);
    }

    public static MoneyHelper getInstance4Jiao(Object object) {
        return getInstance().initByJiao(object);
    }

    public static MoneyHelper getInstance4Fen(Object object) {
        return getInstance().initByFen(object);
    }

    public static MoneyHelper getInstance() {
        return InnerInstance.INSTANCE;
    }

    private static class InnerInstance {
        private static MoneyHelper INSTANCE = new MoneyHelper();
    }

    private MoneyHelper() {
    }

    public MoneyHelper initByYuan(Object object) {
        return init(object, MoneyUnit.Unit_Yuan);
    }

    public MoneyHelper initByJiao(Object object) {
        return init(object, MoneyUnit.Unit_Jiao);
    }

    public MoneyHelper initByFen(Object object) {
        return init(object, MoneyUnit.Unit_Fen);
    }

    public MoneyHelper init(Object object, MoneyUnit moneyUnit) {
        money = createBigDecimal(object);
        this.unit = moneyUnit;
        return this;
    }

    public BigDecimal createBigDecimal(Object object) {

        if (object == null) return new BigDecimal(0);

        if (object instanceof BigDecimal) return (BigDecimal) object;

        if (object instanceof Integer) return new BigDecimal((Integer) object);
        if (object instanceof Long) return new BigDecimal((Long) object);
        if (object instanceof Float) return new BigDecimal((Float) object);
        if (object instanceof Double) return new BigDecimal((Double) object);
        if (object instanceof String) return new BigDecimal((String) object);
        if (object instanceof BigInteger) return new BigDecimal((BigInteger) object);

        return new BigDecimal(0);
    }

    //加法
    public MoneyHelper add(Object object) {
        money = money.add(createBigDecimal(object));
        return this;
    }

    //减法
    public MoneyHelper subtract(Object object) {
        money = money.subtract(createBigDecimal(object));
        return this;
    }

    //乘法
    public MoneyHelper multiply(Object object) {
        money = money.multiply(createBigDecimal(object));
        return this;
    }

    //除法
    public MoneyHelper divide(Object object) {
        money = money.divide(createBigDecimal(object));
        return this;
    }

    public MoneyHelper change2Yuan() {
        return changeUnit(MoneyUnit.Unit_Yuan);
    }

    public MoneyHelper change2Jiao() {
        return changeUnit(MoneyUnit.Unit_Jiao);
    }

    public MoneyHelper change2Fen() {
        return changeUnit(MoneyUnit.Unit_Fen);
    }


    public MoneyHelper changeUnit(MoneyUnit wardUnit) {

        if (unit == wardUnit) return this;
        //换算出要转换到对应单位单位需要*10 、*100、 *0.1、 *0.01；
        double dv = Math.pow(10, unit.getDv() - wardUnit.getDv());
        multiply(dv);

        unit = wardUnit;

        return this;
    }

    public Integer getInt() {
        return money.intValue();
    }

    public Long getLong() {
        return money.longValue();
    }

    public Float getFloat() {
        return money.floatValue();
    }

    public Double getDouble() {
        return money.doubleValue();
    }

    public String getString() {
        return format(unit.getPattern1());
    }

    public String getStringDelZero() {
        return format(unit.getPattern2());
    }


    public String format(String pattern) {
        return new DecimalFormat(pattern).format(money);
    }

    public enum MoneyUnit {
        Unit_Yuan(2, "0.00", "#.##"), Unit_Jiao(1, "0.0", "#.#"), Unit_Fen(0, "0", "#");

        //对应单位转换到分进位数
        // 例如：元到分：2进位；角到分：1进位
        private int dv;
        //对应单位钱的通常显示格式。
        private String pattern1;
        private String pattern2;

        MoneyUnit(int dv, String pattern1, String pattern2) {
            this.dv = dv;
            this.pattern1 = pattern1;
            this.pattern2 = pattern2;
        }

        public int getDv() {
            return dv;
        }

        public String getPattern1() {
            return pattern1;
        }

        public String getPattern2() {
            return pattern2;
        }
    }


    public static void main(String[] args) {
        MoneyHelper helper = MoneyHelper.getInstance4Fen("55550");

        helper.change2Yuan();
        System.out.println(helper.getDouble());
        System.out.println(helper.getLong().toString());
        System.out.println(helper.getString());
        System.out.println(helper.getStringDelZero());
    }


}
