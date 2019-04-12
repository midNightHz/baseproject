package cn.zb.cache.impl;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.zb.cache.CacheDelayHelper;
import cn.zb.cache.CacheDelayTime;
import cn.zb.utils.ThreadFactory;
import cn.zb.utils.Time;

/**
 * 
 * @ClassName: ZBDelayedCache
 * @Description:带自动清除超时功能的缓存
 * @author: 陈军
 * @date: 2019年2月22日 上午11:03:05
 * 
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
@Component("delayCache")
public class ZBDelayedCache extends AbstractCacheImpl {

	private static Logger logger = LoggerFactory.getLogger(ZBDelayedCache.class);
	/**
	 * 默认存活时间
	 */
	private static final long DEFAULT_LIVE_TIME = 30 * 60 * 1000;

	/*
	 * @Autowired private AppConfig config;
	 */
	/**
	 * 维护了key的过期时间
	 */
	private final static Map<String, DelayQueue<DelayedKey>> DELAYED_MAP = new ConcurrentHashMap<>();
	/**
	 * 这里维护了key的deleykey对象之前的关系
	 */
	private final static Map<String, Map<Object, DelayedKey>> DELAYED_KEY_MAP = new ConcurrentHashMap<>();

	static {
		Runnable r = new Runnable() {

			@Override
			public void run() {

				cleanOutTime();

			}
		};
		ThreadFactory.excute(r);
	}

	public static void cleanOutTime() {

		DelayQueue<DelayedKey> queue = null;

		Map<Object, byte[]> caches = null;

		Map<Object, DelayedKey> keymap = null;

		while (true) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Set<String> keyset = DELAYED_MAP.keySet();

			if (keyset.isEmpty()) {
				continue;
			}
			for (String str : keyset) {
				while (true) {

					queue = DELAYED_MAP.get(str);

					caches = CACHE_MAP.get(str);

					keymap = DELAYED_KEY_MAP.get(str);
					
					if(queue==null) {
						break;
					}

					DelayedKey key = queue.poll();

					if (key == null) {
						break;
					}

					Object keyObject = key.getKey();

					if (keyObject == null) {
						continue;
					}

					if (caches == null) {
						continue;
					}

					caches.remove(keyObject);

					if (keymap == null) {
						continue;
					}

					keymap.remove(keyObject);
				}
			}
		}
	}

	@Override
	public void put(String name, Object key, Object value) {

		Time time = new Time();

		Map<Object, byte[]> map = null;

		DelayQueue<DelayedKey> queue = null;

		Map<Object, DelayedKey> keymap = null;

		// 设置缓存时间的方式
		CacheDelayTime delaytime = CacheDelayHelper.getDelayTime();

		long liveTime = DEFAULT_LIVE_TIME;

		if (delaytime != null && delaytime.getInterval() > 0) {

			TimeUnit unit = delaytime.getUnit();

			if (unit == null) {

				unit = TimeUnit.SECONDS;

			}

			liveTime = TimeUnit.SECONDS.convert(delaytime.getInterval(), unit);
		}

		try {

			map = CACHE_MAP.get(name);

			queue = DELAYED_MAP.get(name);

			keymap = DELAYED_KEY_MAP.get(name);

			if (map == null) {
				map = new ConcurrentHashMap<>(32);
				CACHE_MAP.put(name, map);
			}
			if (queue == null) {
				queue = new DelayQueue<>();
				DELAYED_MAP.put(name, queue);
			}
			if (keymap == null) {
				keymap = new ConcurrentHashMap<>();

				DELAYED_KEY_MAP.put(name, keymap);
			}
			if (key != null) {
				byte[] bytes = serialization().serialize(value);
				// debug日志
				if (logger.isDebugEnabled()) {
					logger.debug(Arrays.toString(bytes));
					logger.debug(Arrays.toString(map.get(key)));
				}
				if (bytes != null) {
					map.remove(key);
					map.put(key, bytes);
					// debug日志
					if (logger.isDebugEnabled()) {
						logger.debug(Arrays.toString(map.get(key)));
					}
					DelayedKey delayedKey = keymap.get(key);
					if (delayedKey != null) {
						// 这里要判断是否需要更新delayed的时间

						delayedKey.setMoveTime(System.currentTimeMillis() + delayedKey.getLiveTime());

					} else {
						delayedKey = new DelayedKey(key, liveTime);
						keymap.put(key, delayedKey);
						queue.offer(delayedKey);
					}
				}
			}

		} finally {
			// 记录一下日志
			if (logger.isDebugEnabled()) {
				time.stop();
				logger.debug("this cache impl :{}", this.getClass().getName());
				logger.debug("this serialization:{}", serialization().getClass().getName());
				logger.debug("添加缓存对象:key={} object :{} 耗时:{} ms", key, JSONObject.toJSON(value), time.getTime());
				logger.debug("caches:{}", JSONObject.toJSON(map.keySet()));
				logger.debug("keys:{}", JSONObject.toJSON(keymap));
				logger.debug("keyqueue:{}", JSONObject.toJSON(queue));
			}

		}

	}

	@Override
	public Object get(String name, Object key, Class<?> cl) {
		Time time = new Time();
		Map<Object, byte[]> map = null;

		DelayQueue<DelayedKey> queue = null;

		Map<Object, DelayedKey> keymap = null;

		Object value = null;

		try {

			map = CACHE_MAP.get(name);

			queue = DELAYED_MAP.get(name);

			keymap = DELAYED_KEY_MAP.get(name);

			if (map == null) {
				map = new ConcurrentHashMap<>(32);
				CACHE_MAP.put(name, map);
			}
			if (queue == null) {
				queue = new DelayQueue<>();
				DELAYED_MAP.put(name, queue);
			}
			if (keymap == null) {

				keymap = new ConcurrentHashMap<>();

				DELAYED_KEY_MAP.put(name, keymap);
			}
			if (key != null) {
				byte[] bytes = map.get(key);
				if (logger.isDebugEnabled()) {
					logger.debug(Arrays.toString(bytes));
					logger.debug(Arrays.toString(map.get(key)));
				}
				if (bytes != null) {
					// map.remove(key);
					value = serialization().deserialize(bytes, cl);
					if (logger.isDebugEnabled()) {
						logger.debug(Arrays.toString(map.get(key)));
					}
					DelayedKey delayedKey = keymap.get(key);
					if (delayedKey != null) {
						// 这里要判断是否需要更新delayed的时间
						delayedKey.setMoveTime(System.currentTimeMillis() + delayedKey.getLiveTime());

					}
				}
			}
			return value;

		} finally {
			// 记录一下日志
			if (logger.isDebugEnabled()) {
				time.stop();
				logger.debug("this cache impl :{}", this.getClass().getName());
				logger.debug("this serialization:{}", serialization().getClass().getName());
				logger.debug("添加缓存对象:key={} object :{} 耗时:{} ms", key, JSONObject.toJSON(value), time.getTime());
				logger.debug("caches:{}", JSONObject.toJSON(map.keySet()));
				logger.debug("keys:{}", JSONObject.toJSON(keymap));
				logger.debug("keyqueue:{}", JSONObject.toJSON(queue));
			}

		}
	}

}

class DelayedKey implements Delayed {

	private Object key;

	private long liveTime;
	// @JSONField(format="yyyy-MM-dd HH:mm:ss")
	private long moveTime;

	public DelayedKey() {

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

}
