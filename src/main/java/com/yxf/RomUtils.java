package com.yxf;

import java.util.Random;

public class RomUtils {
    private static byte[] lock = new byte[0];

    // 位数，默认是8位
    private final static long w = 10000;

    //根据时间戳生成商品id
    public static String createID() {
        long r = 0;
        synchronized (lock) {
            r = (long) ((Math.random() + 1) * w);
        }

        return System.currentTimeMillis() + String.valueOf(r).substring(1);
    }

    public static String createSupID() {//6为
        long t = System.currentTimeMillis();//获得当前时间的毫秒数

        Random rd = new Random(t);//作为种子数传入到Random的构造器中

        System.out.println(rd.nextInt());//生成随即整数
        return String.valueOf(rd.nextInt(1000000)+100000);
    }

}
