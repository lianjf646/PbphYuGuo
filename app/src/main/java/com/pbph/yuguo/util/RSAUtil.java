package com.pbph.yuguo.util;

import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by 朱志强 on 2017/5/22.
 */

public class RSAUtil {
    //构建Cipher实例时所传入的的字符串，默认为"RSA/NONE/PKCS1Padding"
    private String sTransform = "RSA/NONE/PKCS1Padding";
    private String PUBLIC_KEY_STR = "";
    //进行Base64转码时的flag设置，默认为Base64.DEFAULT
    private int sBase64Mode = Base64.DEFAULT;
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static RSAUtil getInstance() {
        return RSAUtilHolder.mInstance;
    }

    private static class RSAUtilHolder {
        private static RSAUtil mInstance = new RSAUtil();
    }

    public void setPublicKeyString(String publicKeyString) {
        PUBLIC_KEY_STR = publicKeyString;
    }
    //初始化方法，设置参数
//    public  void init(String transform, int base64Mode) {
//        sTransform = transform;
//        sBase64Mode = base64Mode;
//    }


    /*
        产生密钥对
        @param keyLength
        密钥长度，小于1024长度的密钥已经被证实是不安全的，通常设置为1024或者2048，建议2048
     */
//    public  KeyPair generateRSAKeyPair(int keyLength) {
//        KeyPair keyPair = null;
//        try {
//
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//            //设置密钥长度
//            keyPairGenerator.initialize(keyLength);
//            //产生密钥对
//            keyPair = keyPairGenerator.generateKeyPair();
//            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//            // 得到公钥
//            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//            // 得到公钥字符串
//            String publicKeyString = Base64.encodeToString(publicKey.getEncoded(), sBase64Mode);
//            // 得到私钥字符串
//            String privateKeyString = Base64.encodeToString(privateKey.getEncoded(), sBase64Mode);
//            Log.e("publicKeyString", publicKeyString);
//            Log.e("privateKeyString", privateKeyString);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//
//        return keyPair;
//    }

    /*
        加密或解密数据的通用方法
        @param srcData
        待处理的数据
        @param key
        公钥或者私钥
        @param mode
        指定是加密还是解密，值为Cipher.ENCRYPT_MODE或者Cipher.DECRYPT_MODE

     */
    private byte[] processData(byte[] srcData, Key key, int mode) {
        //用来保存处理结果
        byte[] resultBytes = null;
        try {
            //获取Cipher实例
            Cipher cipher = Cipher.getInstance(sTransform);
            //初始化Cipher，mode指定是加密还是解密，key为公钥或私钥
            cipher.init(mode, key);
            //处理数据
//            resultBytes = cipher.doFinal(srcData);
            int inputLen = srcData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(srcData, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(srcData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            resultBytes = out.toByteArray();
            out.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultBytes;
    }


    /*
        使用公钥加密数据，结果用Base64转码
     */
    public String encryptDataByPublicKey(byte[] srcData) {
        if (TextUtils.isEmpty(PUBLIC_KEY_STR)) {
            return new String(srcData);
        }
        PublicKey publicKey = keyStrToPublicKey(PUBLIC_KEY_STR);
        byte[] resultBytes = processData(srcData, publicKey, Cipher.ENCRYPT_MODE);
//        Log.e("resultBytes", "length " + resultBytes.length);
        return Base64.encodeToString(resultBytes, sBase64Mode);

    }

    //    public  byte[] encryptByPublicKey(byte[] data)
//            throws Exception {
//        PublicKey publicKey = keyStrToPublicKey(PUBLIC_KEY_STR);
//        byte[] keyBytes = Base64Utils.decode(publicKey);
//        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        Key publicK = keyFactory.generatePublic(x509KeySpec);
//        // 对数据加密
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//        cipher.init(Cipher.ENCRYPT_MODE, publicK);
//        int inputLen = data.length;
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        int offSet = 0;
//        byte[] cache;
//        int i = 0;
//        // 对数据分段加密
//        while (inputLen - offSet > 0) {
//            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
//                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
//            } else {
//                cache = cipher.doFinal(data, offSet, inputLen - offSet);
//            }
//            out.write(cache, 0, cache.length);
//            i++;
//            offSet = i * MAX_ENCRYPT_BLOCK;
//        }
//        byte[] encryptedData = out.toByteArray();
//        out.close();
//        return encryptedData;
//    }
    /*
        使用私钥解密，返回解码数据
     */
    public byte[] decryptDataByPrivate(String encryptedData, PrivateKey privateKey) {
        byte[] bytes = Base64.decode(encryptedData, sBase64Mode);
        return processData(bytes, privateKey, Cipher.DECRYPT_MODE);
    }

    /*
        使用私钥进行解密，解密数据转换为字符串，使用utf-8编码格式
     */
    public String decryptedToStrByPrivate(String encryptedData, PrivateKey privateKey) {
        return new String(decryptDataByPrivate(encryptedData, privateKey));
    }

    /*
        使用私钥解密，解密数据转换为字符串，并指定字符集
     */
    public String decryptedToStrByPrivate(String encryptedData, PrivateKey privateKey, String charset) {
        try {

            return new String(decryptDataByPrivate(encryptedData, privateKey), charset);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }


    /*
        使用私钥加密，结果用Base64转码
     */

    public String encryptDataByPrivateKey(byte[] srcData, PrivateKey privateKey) {

        byte[] resultBytes = processData(srcData, privateKey, Cipher.ENCRYPT_MODE);

        return Base64.encodeToString(resultBytes, sBase64Mode);
    }

    /*
        使用公钥解密，返回解密数据
     */

    public byte[] decryptDataByPublicKey(String encryptedData, PublicKey publicKey) {

        byte[] bytes = Base64.decode(encryptedData, sBase64Mode);

        return processData(bytes, publicKey, Cipher.DECRYPT_MODE);

    }

    /*
        使用公钥解密，结果转换为字符串，使用默认字符集utf-8
     */
    public String decryptedToStrByPublicKey(String encryptedData, PublicKey publicKey) {
        return new String(decryptDataByPublicKey(encryptedData, publicKey));
    }


    /*
        使用公钥解密，结果转换为字符串，使用指定字符集
     */

    public String decryptedToStrByPublicKey(String encryptedData, PublicKey publicKey, String charset) {
        try {

            return new String(decryptDataByPublicKey(encryptedData, publicKey), charset);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }




    /*
        将字符串形式的公钥转换为公钥对象
     */

    public PublicKey keyStrToPublicKey(String publicKeyStr) {

        PublicKey publicKey = null;

        byte[] keyBytes = Base64.decode(publicKeyStr, sBase64Mode);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        try {

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            publicKey = keyFactory.generatePublic(keySpec);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return publicKey;

    }

    /*
        将字符串形式的私钥，转换为私钥对象
     */

    public PrivateKey keyStrToPrivate(String privateKeyStr) {

        PrivateKey privateKey = null;

        byte[] keyBytes = Base64.decode(privateKeyStr, sBase64Mode);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        try {

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            privateKey = keyFactory.generatePrivate(keySpec);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return privateKey;

    }

}
