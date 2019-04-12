package cn.zb.serialized.impl;

import org.springframework.stereotype.Component;

import cn.zb.serialize.Serialization;
import cn.zb.serialize.SerializationBuilder;
import cn.zb.serialize.SerializationFactory;
import cn.zb.utils.BeanFactory;

@Component
public class SerializationFactoryImpl implements SerializationFactory {

    private static final SerializationBuilder DEFAULT_BUILDER = new KryoSerializationBuilerImpl();

    private SerializationBuilder builder = null;

    public SerializationFactoryImpl() {

    }

    public SerializationFactoryImpl(SerializationBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(SerializationBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Serialization getSerialization() {
        if (builder == null) {
            return DEFAULT_BUILDER.build();
        }
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serialization> T getSerialization(Class<T> cl) {
        try {

            Serialization s = BeanFactory.getBean(cl);
            
            if (s != null) {
                
                return (T) s;
                
            }
            
            return cl.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

}
