package cn.zb.utils.ipdb;

import java.util.Arrays;

import cn.zb.utils.ThreadFactory;

public class TestMain {

    public static void main(String[] args) {
        // https://www.ipip.net
        try {
            // City类可用于IPDB格式的IPv4免费库，IPv4与IPv6的每周高级版、每日标准版、每日高级版、每日专业版、每日旗舰版
            final City db = new City("C:\\Users\\chen\\Downloads\\17monipdb/ipipfree.ipdb");
            final String ip = "192.56.7.23";

            for (int i = 0; i < 10; i++) {
                Runnable r = new Runnable() {

                    @Override
                    public void run() {
                        for (int i = 0; i < 200; i++) {
                            try {
                                System.out.println(ip + ":" + (db.findMap(ip, "CN")));
                            } catch (InvalidDatabaseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IPFormatException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                };
                ThreadFactory.excute(r);
            }
            // 183.128.141.52
            // for (int i = 1; i < 256; i++) {
            //
            // for (int j = 0; j < 256; j++) {
            // for (int k = 0; k < 256; k++) {
            // for (int s = 0; s < 256; s++) {
            // String ip=i+"."+j+"."+k+"."+s;
            // System.out.println(ip+":"+(db.findMap(ip, "CN")));
            // }
            // }
            // }
            // }
            // db.find(address, language) 返回索引数组

            // db.findInfo(address, language) 返回 CityInfo 对象
            /*
             * CityInfo info = db.findInfo("183.128.163.34", "CN"); System.out.println(info);
             */

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
