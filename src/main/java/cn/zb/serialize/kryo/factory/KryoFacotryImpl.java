package cn.zb.serialize.kryo.factory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;

/**
 * 
 * @ClassName:  KryoFacotryImpl   
 * @Description:kryo工厂   
 * @author: 陈军
 * @date:   2019年2月22日 下午2:38:37   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class KryoFacotryImpl implements KryoFactory {

    @Override
    public Kryo create() {
        Kryo kryo = new Kryo();
        return kryo;
    }

}
