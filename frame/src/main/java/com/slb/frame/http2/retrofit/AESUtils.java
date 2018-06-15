package com.slb.frame.http2.retrofit;



import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者：Juan on 2017/7/19 22:35
 * 邮箱：154336119@qq.com
 * 描述：
 */
public  class AESUtils {
//
//    public static final String KEY = "2a4ac92b8217a77a"; // 秘钥
//    public static final String IV  = "44bf0314c4e1b101";//补码向量
//    private static final String KEY_ALGORITHM = "AES";
//    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";//默认的加密算法
//
//    private AESUtils(){}
//
//    /**
//     * AES加密 。模式CBC，补码方式  PKCS5Padding
//     * @param key 。密匙
//     * @param iv 。补码向量
//     * @return
//     * @throws Exception
//     */
//    private static byte[] aesEncipherByte(String key,String iv, String data) throws Exception{
//        // 获取密钥
//        SecretKeySpec secreKey = getSecretKey(key.getBytes());
//        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// "算法/模式/补码方式"0102030405060708
//        IvParameterSpec ivP = new IvParameterSpec(iv.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
//        cipher.init(Cipher.ENCRYPT_MODE, secreKey, ivP);
//        byte[] encrypted = cipher.doFinal(data.getBytes());
//        return encrypted;
//    }
//
//    /**
//     * AES加密,返回结果经BASE64处理过 。模式CBC，补码方式  PKCS5Padding
//     * @param key 。密匙  密匙长度16位
//     * @param iv 。补码向量 长度16位
//     * @return
//     * @throws Exception
//     */
//    public static String aesEncipherString(String key,String iv,String body) throws Exception{
////        String enc = Base64.encodeBase64String(AESUtils.aesEncipherByte(key,iv, body));
////        //为了防止 HTTP将Base64中的“+”等符号替换，所以采用两次Base64
//        return null;
//    }
//
//
//
//
//
//    /**
//     * AES 解密 模式CBC，date解密前已使用Base64解码，补码方式  PKCS5Padding
//     * @param key 。密匙  密匙长度16位
//     * @param iv 。补码向量 长度16位
//     * @param body 。加密数据
//     * @return
//     * @throws Exception
//     */
//    public static String decryptString(String key,String iv,String body) throws Exception{
//        // 还原密钥
//        SecretKeySpec secreKey = getSecretKey(key.getBytes());
//        ////为了防止 HTTP将Base64中的“+”等符号替换，采用两次Base64加密，所以解密时要两次解密Base64
////        String decryptString = new String(AESUtils.decrypt(Base64.decodeBase64(Base64.decodeBase64(body.getBytes())), secreKey, iv));
//        return null;
//    }
//
//    // 密钥
//    private static SecretKeySpec getSecretKey(byte[] key){
//        //生成密钥
//        return new SecretKeySpec(key, KEY_ALGORITHM);
//    }
//
//    /**
//     * AES 解密 模式CBC，补码方式  PKCS5Padding
//     * @param data  加密数据
//     * @param key  密匙
//     * @param iv  补码向量
//     * @return
//     * @throws Exception
//     */
//    private static byte[] decrypt(byte[] data,Key key,String iv) throws Exception{
//        //实例化
//        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
//        //使用密钥初始化，设置为解密模式
//        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(iv.getBytes()));
//        //执行操作
//        return cipher.doFinal(data);
//    }
//
//    public static void main(String[] arg) throws Exception {
//        String s = "{\n" +
//                "    \"appCode\": \"TTDXT\",\n" +
//                "    \"type\": \"SYSUSER\"}";
//        String ss = AESUtils.aesEncipherString(KEY,IV,s);
//        System.out.println("AES加密后:"+ss);
//        String sss = AESUtils.decryptString(KEY,IV,"ZGNuT09zbkdCZ25Ed2VnN05JTFhKNE01dmlJeHB6K3o2bHlub2tlUjU1Z29Sb2F6OXZTMW5uemdRdUcrK2lncEFxWGZyYml6UGJVZHJJTWRGTThCZit5NExaZnBBZFdBRXhET0k4ZXUwb1p3MURmMjZiaWFvKzNXSXVaL2QrU1J4bUJEMzlMRk0wWmlaMGRTTXFIVllFVDRzS2t0SStrdmFPYmo4NVhlbThOVEFRbzI1TnREd1VQbDliSE5aMTJ2");
//        System.out.println("AES解密后:"+sss);
////		StringBuffer vi = new StringBuffer("1476688794");
////		System.out.println(vi.length());
////		if (vi.length()<16){
////			do {
////				vi.append("0");
////			}while (vi.length()<16);
////		}else {
////			vi = new StringBuffer(vi.substring(0,16));
////		}
////		System.out.println(vi);
////		System.out.println(vi.length());
////		System.out.println(AESUtils.aesEncipherString(AESUtils.KEY,AESUtils.IV,s));
//
//
//    }
//    /**
//     * 获取向量
//     * @param timestamp
//     * @return
//     */
//    public static final String getIv(String timestamp){
//        return DigestUtils.sha256Hex(getKey(timestamp) + timestamp).substring(0, 16);
//    }
//
//    /**
//     * 获取KEY
//     * @param timestamp
//     * @return
//     */
//    public static final String getKey(String timestamp){
//        return DigestUtils.sha256Hex(timestamp).substring(0, 16);
//    }
}