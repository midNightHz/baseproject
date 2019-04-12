package cn.zb.serialized.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;

import cn.zb.serialize.Serialization;
import cn.zb.serialized.kryo.factory.KryoFacotryImpl;
import cn.zb.serialized.kryo.factory.KryoPoolImpl;

/**
 * 
 * @ClassName:  KryoSerialization   
 * @Description:基于Kryo的序列化器   
 * @author: 陈军
 * @date:   2019年2月21日 上午9:28:54   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Component
public class KryoSerialization implements Serialization {

    private static final KryoPool POOL = new KryoPoolImpl(new KryoFacotryImpl(), new LinkedBlockingDeque<Kryo>());

    private static final OutputPool OUTPUT_POOL = new OutputPool(new LinkedBlockingDeque<Output>());

    private static Logger logger = LoggerFactory.getLogger(KryoSerialization.class);

    @Override
    public byte[] serialize(Object object) {
        Kryo kryo = null;
        Output output = null;
        try {
            kryo = POOL.borrow();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            output =  new Output(byteArrayOutputStream); //*/ OUTPUT_POOL.getOutput();
            kryo.writeClassAndObject(output, object);
            // 一定要刷新，不然不会写到out里面进去
            output.flush();
           // return ((ByteArrayOutputStream) (output.getOutputStream())).toByteArray();
             return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            logger.error("序列化失败 :{}", e);
            return null;
        } finally {
            if (kryo != null) {
                POOL.release(kryo);
            }

            if (output != null) {
                OUTPUT_POOL.closeOutput(output);
            }

        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> cl) {

        Kryo kryo = null;
        try {
            kryo = POOL.borrow();
            // kryo = new Kryo();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream);
            input.close();
            return (T) kryo.readClassAndObject(input);
        } catch (Exception e) {
            logger.error("反序列化失败 :{}", e);
            return null;
        } finally {
            if (kryo != null) {
                POOL.release(kryo);
            }
        }

    }

}

class OutputPool {
    private Queue<Output> queue;

    public OutputPool(Queue<Output> queue) {
        this.setQueue(queue);
    }

    public Output getOutput() {
        Output out;
        if ((out = queue.poll()) != null) {
            return out;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        return new Output(byteArrayOutputStream);
    }

    public void closeOutput(Output output) {
        if (output != null) {
            output.clear();
            queue.offer(output);
        }

    }

    public Queue<Output> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Output> queue) {
        this.queue = queue;
    }
}
