package com.example.demo;

import java.io.*;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

public class myAES {

    private static byte[] iv = { 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x38, 0x37,
            0x36, 0x35, 0x34, 0x33, 0x32, 0x31 };
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 加密
     * @param content  需要加密的内容
     * @param password  加密密码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] encrypt(byte[] content, String password) throws Exception {
        Key key = new SecretKeySpec(password.getBytes("utf-8"), "AES");
        Cipher in = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] enc = in.doFinal(content);
        return enc;
    }

    /**
     * 解密
     * @param content 待解密内容
     * @param password 解密密钥
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] decrypt(byte[] content, String password) throws Exception {
        Key key = new SecretKeySpec(password.getBytes("utf-8"), "AES");
        Cipher out = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] dec = out.doFinal(content);
        return dec;
    }

    public static String getRandomString() throws IOException {
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<32;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        FileOutputStream key = new FileOutputStream("key.txt");
        key.write(sb.toString().getBytes());
        System.out.println("key.txt文件已保存");
        return sb.toString();
    }

    /**
     *
     * @param path
     * @param key
     * @throws Exception
     */
    public static void enfile(String path,String key) throws Exception {
        System.out.println("\n-----------------加密文件-----------------");
        //读取源文件
        InputStream fis = new FileInputStream(path);
        byte[] buf = new byte[10000000];
        int num = fis.read(buf);
        fis.close();
        byte[] buf2 = new byte[num];
        System.arraycopy(buf,0, buf2, 0, num);

        //加密
        byte[] result =encrypt(buf2,key);
        FileOutputStream en = new FileOutputStream("en.txt");
        String str = new String(Base64.encode(result),"utf-8");
        System.out.println("加密文件长度："+result.length);
        en.write(str.getBytes());
        en.close();
        System.out.println("加密✅");
        System.out.println("加密文件保存为 en.txt");
    }

    public static void defile(String path,String keypath) throws Exception {
        System.out.println("\n------------------解密文件-------------------");
        //读取密钥
        InputStream keyfile = new FileInputStream(keypath);
        byte[] keey =new byte[32];
        keyfile.read(keey);
        String key = new String(keey);

        //读取加密文件
        InputStream myen = new FileInputStream(path);
        byte[] enbuf = new byte[10000000];
        int num2 = myen.read(enbuf);
        byte[] buf3 = new byte[num2];
        System.out.println("读取加密文件长度(base64)："+num2);
        System.arraycopy(enbuf,0, buf3, 0, num2);
        byte[] de64 = Base64.decode(buf3);

        //System.out.println(Arrays.equals(enbuf,result));
        byte[] result = decrypt(de64,key);
        FileOutputStream de = new FileOutputStream("de.txt");
        de.write(result);  // 这样写入的数据，会将文件中的原数据覆盖
        de.close();
        System.out.println("解密✅");
        System.out.println("解密文件保存为 de.txt");
    }
    public static void main(String[] args) throws Exception{
        //随机密钥生成
        String key = getRandomString();

        try{
            enfile("西游记.txt",key);
            defile("en.txt","key.txt");
        }
        catch (Exception e){
            System.out.println("\nwrong!wrong!wrong!\n"+e);
        }

    }

}