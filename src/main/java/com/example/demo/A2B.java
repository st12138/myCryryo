package com.example.demo;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

import static com.example.demo.myAES.*;
import static com.example.demo.mySM2.*;
import static com.example.demo.mySM3.*;

public class A2B {
    public static void main(String args[]) throws Exception {
        /***
        //AB公私钥生成
        getKey("Alice");
        getKey("Bob");
        ***/

        //随机密钥生成
        String key = getRandomString();
        System.out.println("会话密钥："+key);

        System.out.println("\n\n******************************Alice******************************");
        System.out.println("Alice选择传输文件：");
        Scanner in = new Scanner(System.in);
        String file = in.nextLine();


        //A生成文件消息摘要
        String ahash = hashFile(file);
        System.out.println(file+"文件哈希值："+ahash);
        //02312843F303130B7B64E433FEE403A29CD0110FA3DAEE92596DE34B59584A01

        //A生成数字签名
        Signature signature = mysign("publickey_Alice.pem","privatekey_Alice.pem",ahash,"Alice");


        //A生成加密文件
        try{
            enfile(file,key);
        }
        catch (Exception e){
            System.out.println("\nwrong!wrong!wrong!\n"+e);
        }

        //A用B公钥加密对称密钥
        enKey("key.txt","publickey_Bob.pem");


        System.out.println("\n\n******************************Bob******************************");
        //B用私钥揭秘对称密钥
        deKey("enkey","privatekey_Bob.pem");

        //B用对称密钥解密文件
        defile("en.txt","bobkey.txt");

        //B计算文件哈希值
        String bhash = hashFile("de.txt");
        System.out.println("文件哈希值："+bhash);
        //02312843F303130B7B64E433FEE403A29CD0110FA3DAEE92596DE34B59584A01

        //验证签名

        System.out.println(myverify(bhash,"Alice",signature,"publickey_Alice.pem"));

    }
}
