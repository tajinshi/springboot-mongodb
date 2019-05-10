package com.springboot.springtest.common.encrypt;


import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @author tuojinshi
 * @ClassName: DesUtil
 * @Description： DES 加密文件内容
 * des对称加密，是一种比较传统的加密方式，其加密运算、解密运算使用的是同样的密钥，
 * 信息的发送者和信息的接收者在进行信息的传输与处理时，必须共同持有该密码（称为对称密码），是一种对称加密算法
 * @Date 2019/5/7 15:09
 */
public class DESUtil {
    Key key;
    public DESUtil(String str) {
        getKey(str);//生成密匙
    }
    /**
     * 根据参数生成KEY
     */
    public void getKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            _generator.init(new SecureRandom(strKey.getBytes()));
            this.key = _generator.generateKey();
            _generator = null;
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
    }

    /**
     * 文件file进行加密并保存目标文件destFile中
     * @param file   要加密的文件 如c:/test/srcFile.txt
     * @param destFile 加密后存放的文件名 如c:/加密后文件.txt
     */
    public void encrypt(String file, String destFile) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, this.key);
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }
    /**
     * 文件采用DES算法解密文件
     * @param file 已加密的文件 如c:/加密后文件.txt
     *         * @param destFile
     *         解密后存放的文件名 如c:/ test/解密后文件.txt
     */
    public void decrypt(String file, String dest) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, this.key);
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(dest);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = is.read(buffer)) >= 0) {
            System.out.println();
            cos.write(buffer, 0, r);
        }
        cos.close();
        out.close();
        is.close();
    }



    public static void encryptFile (String filePath) {
        File file = new File(filePath);
        DESUtil td = new DESUtil("ebbbf6f87f224efc9c28e929bac11c6a");
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
//                    logger.info("目录：" + files[i].getPath());
                    encryptFile(files[i].getPath());
                } else {
                    try {
                        //从11.json 加密到JM11.JSON
                        td.encrypt(files[i].getPath(), files[i].getParent() + File.separator + "JM" + files[i].getName()); //解密
                        //删除11.json
                        files[i].delete();
                        //修改JM11.JSON 为11.json
                        File newname = new File(files[i].getParent() + File.separator + "JM" + files[i].getName());
                        newname.renameTo(files[i]);
                    } catch (Exception e) {
//                        logger.info("加密出错：" + e.getMessage());
                    }
                }

            }
        } else {
            try {
                td.encrypt("C:\\Users\\admin\\Desktop\\11.txt", "C:\\Users\\admin\\Desktop\\22.txt"); //解密
            } catch (Exception e) {
//                logger.info("加密出错：" + e.getMessage());
            }
        }
    }
    public static void main(String[] args) throws Exception {
        String filePath = "F:\\test" ;
        //加密文件路径
        encryptFile(filePath);


    }

}
