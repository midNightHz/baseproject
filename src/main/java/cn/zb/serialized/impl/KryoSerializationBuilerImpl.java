package cn.zb.serialized.impl;

import cn.zb.serialize.Serialization;
import cn.zb.serialize.SerializationBuilder;
import cn.zb.serialized.kryo.KryoSerialization;
import cn.zb.utils.BeanFactory;

public class KryoSerializationBuilerImpl implements SerializationBuilder {

    private static Serialization s;

    @Override
    public Serialization build() {
        if (s == null) {

            s = BeanFactory.getBean(KryoSerialization.class);
            if (s != null) {
                return s;
            }
            s = new KryoSerialization();
        }
        return s;
    }

}
