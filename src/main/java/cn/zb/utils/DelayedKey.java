package cn.zb.utils;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedKey implements Delayed {

    private String id;

    private Object key;

    private long liveTime;
    // @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private long moveTime;
    
    private boolean flushable=true;

    public DelayedKey() {

    }
    
    public DelayedKey(Object key, long liveTime,boolean flushable) {
        super();
        this.key = key;
        this.liveTime = liveTime;
        this.moveTime = System.currentTimeMillis() + liveTime;
        this.flushable=flushable;
    }

    public DelayedKey(Object key, long liveTime, long moveTime,boolean flushable) {
        super();
        this.key = key;
        this.liveTime = liveTime;
        this.moveTime = moveTime;
        this.flushable=flushable;
    }

    public DelayedKey(Object key, long liveTime) {
        super();
        this.key = key;
        this.liveTime = liveTime;
        this.moveTime = System.currentTimeMillis() + liveTime;
    }

    public DelayedKey(Object key, long liveTime, long moveTime) {
        super();
        this.key = key;
        this.liveTime = liveTime;
        this.moveTime = moveTime;
    }

    public Object getKey() {
        return key;
    }

    public long getLiveTime() {
        return liveTime;
    }

    public long getMoveTime() {
        return moveTime;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public void setLiveTime(long liveTime) {
        this.liveTime = liveTime;
    }

    public void setMoveTime(long moveTime) {
        this.moveTime = moveTime;
    }

    @Override
    public int compareTo(Delayed o) {

        if (o == null) {
            return 1;

        }
        if (!(o instanceof DelayedKey)) {
            return 1;
        }

        DelayedKey other = (DelayedKey) o;

        if (getMoveTime() == other.getMoveTime()) {
            return 0;
        }
        if (getMoveTime() > other.getMoveTime()) {
            return 1;
        }

        return -1;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(moveTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        // result = prime * result + (int) (liveTime ^ (liveTime >>> 32));
        // result = prime * result + (int) (moveTime ^ (moveTime >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DelayedKey other = (DelayedKey) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (liveTime != other.liveTime)
            return false;
        if (moveTime != other.moveTime)
            return false;
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFlushable() {
        return flushable;
    }

    public void setFlushable(boolean flushable) {
        this.flushable = flushable;
    }

    

}
