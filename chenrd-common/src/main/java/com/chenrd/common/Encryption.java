/*
 * 文件名：Encryption.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年6月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common;

import java.security.MessageDigest;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author chenrd
 * @version 2015年6月7日
 * @see Encryption
 * @since
 */
public final class Encryption
{

    
    /**
     * 十六进制下数字到字符的映射数组  
     */
    private static final String[] HEXDIGITS = {"0", "1", "2", "3", "4",  
        "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    
    /**
     * 对字符串进行MD5加密 
     * @param originString 
     * @return ""
     * @see
     */
    public static String encodeByMD5(String originString)
    {  
        if (originString != null)
        {  
            try
            {  
                //创建具有指定算法名称的信息摘要  
                MessageDigest md = MessageDigest.getInstance("MD5");  
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算  
                byte[] results = md.digest(originString.getBytes());  
                //将得到的字节数组变成字符串返回  
                String resultString = byteArrayToHexString(results);  
                return resultString.toUpperCase();  
            } 
            catch(Exception ex)
            {  
                ex.printStackTrace();  
            }  
        }  
        return null;  
    }
    
    /**  
     * 转换字节数组为十六进制字符串 
     * @param b 字节数组 
     * @return 十六进制字符串 
     */  
    private static String byteArrayToHexString(byte[] b)
    {  
        StringBuffer resultSb = new StringBuffer();  
        for (int i = 0; i < b.length; i++)
        {  
            resultSb.append(byteToHexString(b[i]));  
        }  
        return resultSb.toString();  
    }
    
    /**
     * 将一个字节转化成十六进制形式的字符串
     * @param b 
     * @return ""
     * @see
     */
    private static String byteToHexString(byte b)
    {  
        int n = b;  
        if (n < 0)
        {
            n = 256 + n;
        }
        int d1 = n / 16;  
        int d2 = n % 16;  
        return HEXDIGITS[d1] + HEXDIGITS[d2];  
    }
    
    /**
     * 
     */
    private Encryption()
    {
        
    }
}
