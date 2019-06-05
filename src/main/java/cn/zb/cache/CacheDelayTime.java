package cn.zb.cache;

import java.util.concurrent.TimeUnit;

public class CacheDelayTime {

	public static final TimeUnit DEFAULT_UNIT = TimeUnit.MILLISECONDS;

	private long interval;

	private TimeUnit unit;

	private boolean flushable = true;

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
		this.unit = DEFAULT_UNIT;
	}

	public CacheDelayTime() {
	}

	public boolean isFlushable() {
		return flushable;
	}

	public void setFlushable(boolean flushable) {
		this.flushable = flushable;
	}

}
