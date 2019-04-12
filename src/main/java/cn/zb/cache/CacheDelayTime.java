package cn.zb.cache;

import java.util.concurrent.TimeUnit;

public class CacheDelayTime {

    private long interval;

    private TimeUnit unit;

    public long getInterval() {
        return interval;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public CacheDelayTime(long interval, TimeUnit unit) {
        super();
        this.interval = interval;
        this.unit = unit;
    }

    public CacheDelayTime(long interval) {
        this.interval = interval;
        this.unit = TimeUnit.SECONDS;
    }
    
    public CacheDelayTime() {
    }

}
