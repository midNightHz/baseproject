package cn.zb.utils;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.alibaba.fastjson.JSONObject;

/**
 * 加密工具
 * 
 * @author chen
 *
 */
public class ScretUtil {

    /**
     * 加密秘钥
     */
    private static final String KEY = "zb@@2008";

    /**
     * 加密
     * 
     * @param o
     * @return
     */
    public static String encrypt(Object o) {
        try {
            return encrypt(o.toString());
        } catch (Exception e) {
            return null;
        }

    }

    private static String encrypt(String str) throws Exception {
        byte[] input = str.getBytes();
        byte[] out = encryptByKey(input);
        byte[] outbytes = Base64.getEncoder().encode(out);
        return new String(outbytes, "utf-8");
    }

    private static byte[] encryptByKey(byte[] datasource) {
        try {
            SecureRandom random = new SecureRandom();

            DESKeySpec desKey = new DESKeySpec(KEY.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String data) {
        if (data == null) {
            return null;
        }
        try {
            byte[] in = Base64.getDecoder().decode(data);
            byte[] bt = decrypt(in);
            String strs = new String(bt, "utf-8");
            return strs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] decrypt(byte[] src) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(KEY.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }

    /**
     * 对密码进行加密
     * 
     * @param password
     * @return
     * @throws Exception
     */
    public static String encryPassword(String password) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        Encoder encoder = Base64.getEncoder();

        return new String(encoder.encode(md5.digest(password.getBytes("utf-8"))));

    }

    public static void main(String[] args) throws Exception {
        // String str = encrypt(17551);
        // System.out.println(str);
        // System.out.println(decrypt("mAViFhqZnTA="));
        // System.out.println(decrypt("cw3KGIoTtc8="));

        Map<String, String> map = new HashMap<String, String>();

        map.put("validityDate", "2019-10-01");
        map.put("ip", InetAddress.getLocalHost().getHostAddress());
        map.put("aaaaa", "gtwrhytrefeqwerewr");
        System.out.println(JSONObject.toJSON(map));

        String key = MD5Util.getMD5String("chenjun");
        System.out.println(key);

        try {
            SecureRandom random = new SecureRandom();

            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            // 现在，获取数据并加密
            // 正式执行加密操作
            byte[] bs= cipher.doFinal((JSONObject.toJSON(map).toString()).getBytes("utf-8"));
            System.out.println(new String (Base64.getEncoder().encode(bs),"utf-8")+key);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
