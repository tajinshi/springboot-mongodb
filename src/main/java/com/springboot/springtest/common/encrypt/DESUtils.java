package com.springboot.springtest.common.encrypt;



import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class DESUtils {
    public static final String DES = "DES";

    /**编码格式；默认使用uft-8*/
    public String charset = "utf-8";
    /**DES*/
    public int keysizeDES = 0;

    public static DESUtils me;

    private DESUtils(){
        //单例
    }
    //双重锁
    public static DESUtils getInstance(){
        if (me==null) {
            synchronized (DESUtils.class) {
                if(me == null){
                    me = new DESUtils();
                }
            }
        }
        return me;
    }

    /**
     * 使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错
     * @param res 加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key  加密的秘钥
     * @param keysize
     * @param isEncode
     * @return
     */
    private String keyGeneratorES(String res,String algorithm,String key,int keysize,boolean isEncode){
        try {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            if (keysize == 0) {
                //为了解决linux和windows下编码规则一直，用SUN提供的强随机种子算法
                SecureRandom secureRandom =SecureRandom.getInstance("SHA1PRNG","SUN");
                /**
                 * SecureRandom 实现完全随操作系统本身的內部状态，除非调用方在调用 getInstance 方法，
                 * 然后调用 setSeed 方法；该实现在 windows 上每次生成的 key 都相同，但是在 solaris 或部分 linux 系统上则不同
                 */
                secureRandom.setSeed(key.getBytes());
                kg.init(secureRandom);
            }else if (key==null) {
                kg.init(keysize);
            }else {
                //使用SUN提供的强随机种子算法
                SecureRandom secureRandom =SecureRandom.getInstance("SHA1PRNG","SUN");
                secureRandom.setSeed(key.getBytes());
                kg.init(keysize, secureRandom);
            }
            SecretKey sk = kg.generateKey();
            SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            if (isEncode) {
                cipher.init(Cipher.ENCRYPT_MODE, sks);
                byte[] resBytes = charset==null?res.getBytes():res.getBytes(charset);
                return parseByte2HexStr(cipher.doFinal(resBytes));
            }else {
                cipher.init(Cipher.DECRYPT_MODE, sks);
                return new String(cipher.doFinal(parseHexStr2Byte(res)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**将二进制转换成16进制 */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    /**将16进制转换为二进制*/
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    /**
     * 使用DES加密算法进行加密（可逆）
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    public String DESencode(String res, String key) {
        return keyGeneratorES(res, DES, key, keysizeDES, true);
    }

    /**
     * 对使用DES加密算法的密文进行解密（可逆）
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return
     */
    public String DESdecode(String res, String key) {
        return keyGeneratorES(res, DES, key, keysizeDES, false);
    }

}