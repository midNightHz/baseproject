package cn.zb.utils;

/**
 * 查看程序运行的工具
 * 
 * @author chen
 *
 */
public class Time {

    private long startTime = System.currentTimeMillis();

    private long endTime;

    // private long runTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        endTime = System.currentTimeMillis();
    }

    public Long getTime() {
        return endTime - startTime;
    }

}
