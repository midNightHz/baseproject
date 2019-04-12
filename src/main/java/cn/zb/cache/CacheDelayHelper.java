package cn.zb.cache;

public class CacheDelayHelper {

    protected final static ThreadLocal<CacheDelayTime> DELAY_TIME = new ThreadLocal<>();

    public static void setDelayTime(CacheDelayTime time) {

        DELAY_TIME.set(time);

    }

    public static CacheDelayTime getDelayTime() {
        return DELAY_TIME.get();
    }

    public static void setDelayTime(long time) {
        setDelayTime(new CacheDelayTime(time));
    }

}
