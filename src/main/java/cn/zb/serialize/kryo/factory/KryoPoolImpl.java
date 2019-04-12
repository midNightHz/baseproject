package cn.zb.serialize.kryo.factory;

import java.util.Queue;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoCallback;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

/**
 * 
 * @ClassName:  KryoPollImpl   
 * @Description:kryo池的实现方式   
 * @author: 陈军
 * @date:   2019年2月21日 上午10:55:32   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class KryoPoolImpl implements KryoPool {

    private final Queue<Kryo> queue;
    private final KryoFactory factory;

    public KryoPoolImpl(KryoFactory factory, Queue<Kryo> queue) {
        this.factory = factory;
        this.queue = queue;
    }

    public int size() {
        return queue.size();
    }

    public Kryo borrow() {
        Kryo res;
        if ((res = queue.poll()) != null) {
            return res;
        }
        return factory.create();
    }

    public void release(Kryo kryo) {
        if (kryo != null)
            queue.offer(kryo);
    }

    public <T> T run(KryoCallback<T> callback) {
        Kryo kryo = borrow();
        try {
            return callback.execute(kryo);
        } finally {
            release(kryo);
        }
    }

    public void clear() {
        queue.clear();
    }

}
