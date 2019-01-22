package com.slb.ttdandroidframework.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by mabin on 2018-09-09.
 */

public class ByteUtils {

    private static String hexStr =  "0123456789ABCDEF";
    private static String[] binaryArray =
            {"0000","0001","0010","0011",
                    "0100","0101","0110","0111",
                    "1000","1001","1010","1011",
                    "1100","1101","1110","1111"};

    /**
     *
     * @return 二进制数组转换为二进制字符串   2-2
     */
    public static String bytes2BinStr(byte[] bArray){

        String outStr = "";
        int pos = 0;
        for(byte b:bArray){
            //高四位
            pos = (b&0xF0)>>4;
            outStr+=binaryArray[pos];
            //低四位
            pos=b&0x0F;
            outStr+=binaryArray[pos];
        }
        return outStr;
    }

    /**
     *
     * @param bytes
     * @return 将二进制数组转换为十六进制字符串  2-16
     */
    public static String bin2HexStr(byte[] bytes){

        String result = "";
        String hex = "";
        for(int i=0;i<bytes.length;i++){
            //字节高4位
            hex = String.valueOf(hexStr.charAt((bytes[i]&0xF0)>>4));
            //字节低4位
            hex += String.valueOf(hexStr.charAt(bytes[i]&0x0F));
            result +=hex;  //+" "
        }
        return result;
    }

    /**
     *
     * @param hexString
     * @return 将十六进制转换为二进制字节数组   16-2
     */
    public static byte[] hexStr2BinArr(String hexString){
        //hexString的长度对2取整，作为bytes的长度
        int len = hexString.length()/2;
        byte[] bytes = new byte[len];
        byte high = 0;//字节高四位
        byte low = 0;//字节低四位
        for(int i=0;i<len;i++){
            //右移四位得到高位
            high = (byte)((hexStr.indexOf(hexString.charAt(2*i)))<<4);
            low = (byte)hexStr.indexOf(hexString.charAt(2*i+1));
            bytes[i] = (byte) (high|low);//高地位做或运算
        }
        return bytes;
    }

    /**
     *
     * @param hexString
     * @return 将十六进制转换为二进制字符串   16-2
     */
    public static String hexStr2BinStr(String hexString){
        return bytes2BinStr(hexStr2BinArr(hexString));
    }

    /**
     * 翻转byte数组, 用于大小端转换.
     *
     * @param array
     * @return
     */
    public static byte[] reverse(byte[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            byte temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    }

    /**
     * 将bit 字符串翻转
     * @param byteStr
     * @return
     */
    public static String reverseByteString(String byteStr) {
        String returnStr="";
        for(int i=byteStr.length()-1;i>=0;i--){
            returnStr+=byteStr.charAt(i);
        }
        return  returnStr;
    }
    public static byte[] int2byte(int res) {
        byte[] targets = new byte[4];

        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }

    public static int byte2int(byte[] res) {
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    public static byte[] intToByteArray(int i) throws Exception {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);
//        System.out.println("i:" + i);
        out.writeInt(i);
        byte[] b = buf.toByteArray();
//        System.out.println("i:" + b);
        out.close();
        buf.close();
        return b;
    }

    /**
     * 将一个4字节数组转换为unsinged int
     * @param res
     * @return
     */
    public static long byteToUnsingedInt(byte[] res){
//        reverse(res);
        return (long)(res[0]&0xFF << 24)
                +( (long)(res[1]&0xFF) << 16 )
                +( (long)(res[2]&0xFF) << 8 )
                +( (long)(res[3]&0xFF) );

    }

    /**
     * 将16位的short转换成byte数组
     *
     * @param s short
     * @return byte[] 长度为2
     */
    public static byte[] shortToByteArray(short s) {
        byte[] targets = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xff);
        }
        return targets;
    }

    /**
     * 注释：字节数组到short的转换！
     *
     * @param b
     * @return
     */
    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);
        short s1 = (short) (b[1] & 0xff);// 最低位
        s0 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }

    /**
     * 注释：字节数组到short的转换！
     *
     * @param b
     * @return
     */
    public static short byteToShort1(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);
        return s0;
    }

    /**
     * 把byte[]转换成16进制进制字符串
     *
     * @param b
     * @return
     */
    public static String bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    public static String byte2HexString(byte b){
        return Integer.toHexString((b & 0x000000FF) | 0xFFFFFF00).substring(6);
    }

    /**
     * byte[]转换成bit
     *
     * @param bytes
     * @return
     */
    public static String bytesToBits(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            sb.append(byteToBits(b));
        }
        return sb.toString();
    }

    /**
     * byte转换成8位bit
     *
     * @param b
     * @return
     */
    public static String byteToBits(byte b) {
        int z = b;
        z |= 256;
        String str = Integer.toBinaryString(z);
        int len = str.length();
        return str.substring(len - 8, len);
    }


    /**
     * 计算校验和
     *
     * @param bytes
     * @return
     */
    public static final int calculateCheckSum(byte[] bytes) {
        int sum = 0;
        for (byte b : bytes) {
            sum += (short) b;
        }
        return sum > 65535 ? (sum - 65535) : sum;
    }


    /**
     * 转换short为byte
     *
     * @param b
     * @param s     需要转换的short
     * @param index
     */
    public static void putShort(byte b[], short s, int index) {
        b[index + 1] = (byte) (s >> 8);
        b[index + 0] = (byte) (s >> 0);
    }

    /**
     * 通过byte数组取到short
     *
     * @param b
     * @param index 第几位开始取
     * @return
     */
    public static short getShort(byte[] b, int index) {
        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
    }

    /**
     * 转换int为byte数组
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putInt(byte[] bb, int x, int index) {
        bb[index + 3] = (byte) (x >> 24);
        bb[index + 2] = (byte) (x >> 16);
        bb[index + 1] = (byte) (x >> 8);
        bb[index + 0] = (byte) (x >> 0);
    }

    /**
     * 通过byte数组取到int
     *
     * @param bb
     * @param index 第几位开始
     * @return
     */
    public static int getInt(byte[] bb, int index) {
        return (int) ((((bb[index + 3] & 0xff) << 24)
                | ((bb[index + 2] & 0xff) << 16)
                | ((bb[index + 1] & 0xff) << 8) | ((bb[index + 0] & 0xff) << 0)));
    }

    /**
     * 转换long型为byte数组
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putLong(byte[] bb, long x, int index) {
        bb[index + 7] = (byte) (x >> 56);
        bb[index + 6] = (byte) (x >> 48);
        bb[index + 5] = (byte) (x >> 40);
        bb[index + 4] = (byte) (x >> 32);
        bb[index + 3] = (byte) (x >> 24);
        bb[index + 2] = (byte) (x >> 16);
        bb[index + 1] = (byte) (x >> 8);
        bb[index + 0] = (byte) (x >> 0);
    }

    /**
     * 通过byte数组取到long
     *
     * @param bb
     * @param index
     * @return
     */
    public static long getLong(byte[] bb, int index) {
        return ((((long) bb[index + 7] & 0xff) << 56)
                | (((long) bb[index + 6] & 0xff) << 48)
                | (((long) bb[index + 5] & 0xff) << 40)
                | (((long) bb[index + 4] & 0xff) << 32)
                | (((long) bb[index + 3] & 0xff) << 24)
                | (((long) bb[index + 2] & 0xff) << 16)
                | (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0));
    }

    /**
     * 字符到字节转换
     *
     * @param ch
     * @return
     */
    public static void putChar(byte[] bb, char ch, int index) {
        int temp = (int) ch;
        // byte[] b = new byte[2];
        for (int i = 0; i < 2; i++) {
            bb[index + i] = new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
    }

    /**
     * 字节到字符转换
     *
     * @param b
     * @return
     */
    public static char getChar(byte[] b, int index) {
        int s = 0;
        if (b[index + 1] > 0)
            s += b[index + 1];
        else
            s += 256 + b[index + 0];
        s *= 256;
        if (b[index + 0] > 0)
            s += b[index + 1];
        else
            s += 256 + b[index + 0];
        char ch = (char) s;
        return ch;
    }

    /**
     * float转换byte
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putFloat(byte[] bb, float x, int index) {
        // byte[] b = new byte[4];
        int l = Float.floatToIntBits(x);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = new Integer(l).byteValue();
            l = l >> 8;
        }
    }

    /**
     * 通过byte数组取得float
     *
     * @param b
     * @param index
     * @return
     */
    public static float getFloat(byte[] b, int index) {
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    /**
     * double转换byte
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putDouble(byte[] bb, double x, int index) {
        // byte[] b = new byte[8];
        long l = Double.doubleToLongBits(x);
        for (int i = 0; i < 8; i++) {
            bb[index + i] = new Long(l).byteValue();
            l = l >> 8;
        }
    }

    /**
     * 通过byte数组取得double
     *
     * @param b
     * @param index
     * @return
     */
    public static double getDouble(byte[] b, int index) {
        long l;
        l = b[0];
        l &= 0xff;
        l |= ((long) b[1] << 8);
        l &= 0xffff;
        l |= ((long) b[2] << 16);
        l &= 0xffffff;
        l |= ((long) b[3] << 24);
        l &= 0xffffffffl;
        l |= ((long) b[4] << 32);
        l &= 0xffffffffffl;
        l |= ((long) b[5] << 40);
        l &= 0xffffffffffffl;
        l |= ((long) b[6] << 48);
        l &= 0xffffffffffffffl;
        l |= ((long) b[7] << 56);
        return Double.longBitsToDouble(l);
    }

    /**
     * 将16进制字符串转换为byte数组
     * @param s
     * @param index
     * @return
     */
    public static byte[] putHexString(String s, int index) {
        byte[] bb = new byte[s.length()/2];
        for (int i = 0; i < s.length(); i += 2) {
            byte c = (byte) (charToByte(s.charAt(i)) << 4 | charToByte(s.charAt(i + 1)));
            bb[index + (i >> 1)] = c;
        }
        return bb;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String getHexString(byte[] b, int index, int count) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (b == null || index < 0 || b.length < index + count) {
            return null;
        }
        for (int i = index; i < count + index; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String getBinaryString(int d, int length) {
        StringBuilder stringBuilder = new StringBuilder("");
        String hv = Integer.toBinaryString(d);
        for (int j = 0; j < length - hv.length(); j++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);
        return stringBuilder.toString();
    }

    public static String getBinaryReverseString(int d, int length) {
        StringBuilder stringBuilder = new StringBuilder("");
        String hv = Integer.toBinaryString(d);
        for (int j = 0; j < length - hv.length(); j++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);
        stringBuilder.reverse();

        return stringBuilder.toString();
    }


    //网络字节逆序
    public static byte[] ReversEndian(byte b[], int count, boolean big) {
        byte by;
        byte data[] = new byte[count];
        for (int i = 0; i < count; i++) {
            data[i] = b[i];
        }
        if (big == false) {
            for (int i = 0; i < count; i++) {
                by = b[i];
                data[count - i - 1] = by;
            }
        }
        return data;
    }



    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }
//    private static byte charToByte(char c) {
//        return (byte) "0123456789ABCDEF".indexOf(c);
//    }

    public static int hexStringToInt(String hex){
        BigInteger bi = new BigInteger(hex, 16);
        return bi.intValue();
    }

    public static long hexStringToLong(String hex){
        BigInteger bi = new BigInteger(hex, 16);
        return bi.longValue();
    }

    public static short hexStringToShort(String hex){
        BigInteger bi = new BigInteger(hex, 16);
        return bi.shortValue();
    }

    public static byte[] concatAll(byte[] first, byte[]... others){
        int totalLength = first.length;
        for ( byte[] array : others ){
            totalLength += array.length;
        }

        byte[] result = Arrays.copyOf(first,totalLength);
        int offset = first.length;

        for ( byte[] array : others ){
            System.arraycopy(array,0,result, offset,array.length);
            offset += array.length;
        }

        return result;
    }


    public static byte bitToByte(String bit) {
        int re, len;
        if (null == bit) {
            return 0;
        }
        len = bit.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit处理
            if (bit.charAt(0) == '0') {// 正数
                re = Integer.parseInt(bit, 2);
            } else {// 负数
                re = Integer.parseInt(bit, 2) - 256;
            }
        } else {//4 bit处理
            re = Integer.parseInt(bit, 2);
        }
        return (byte) re;
    }
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

    /**
     * 10进制转16进制
     * @return
     */
    public static String intToHex(String a) {
        int n = Integer.valueOf(a);
        StringBuffer s = new StringBuffer();
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            s = s.append(b[n%16]);
            n = n/16;
        }
        a = s.reverse().toString();
        if(a.length() == 1){
            a = "0"+a;
        }
        return a;
    }

}
