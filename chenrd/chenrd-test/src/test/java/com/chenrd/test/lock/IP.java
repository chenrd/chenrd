package com.chenrd.test.lock;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class IP {
	
    public IP(String addr) {
    	load(addr);
	}

    public static String randomIp() {
        Random r = new Random();
        StringBuffer str = new StringBuffer();
        str.append(r.nextInt(1000000) % 255);
        str.append(".");
        str.append(r.nextInt(1000000) % 255);
        str.append(".");
        str.append(r.nextInt(1000000) % 255);
        str.append(".");
        str.append(0);

        return str.toString();
    }

    public static void main(String[] args){
    	/**
    	 * 整个文件分4块：
    	 * 第一块4个字节：存第一块到第4块之前的长度
    	 * 第二块1024个字节：存0-255对应的在第三块的坐标
    	 * 第三块：每8个字节一个整体【ip+长度】
    	 * 第四块：地理位置
    	 * 
    	 * 加dat文件，解读load方法之后文件字节的意义
    	 * 文件最开始的4个字节存的是整个文件的字节长度，接下来的1024个字节 刚好就是每4个字节代表一个@Index
    	 * 解析这1024个字节就是一个长度为256数组，对应的刚才是ip第一个小数点最大长度0-255，数组里面存的内容就是告诉一个坐标，
    	 * 这个坐标对应的就是要查询的ip的第一个.号前面的数字，根据这个index坐标，直接从对应的位置开始查找
    	 * 
    	 * 1024个字节后面就是真正的数据了，每8个字节代表一个整体【ip+长度】,长度就是地理位置字符串的字节长度了。
    	 * 前面3块解析了 直接到第四块找内容
    	 * 
    	 */
        IP.load("E:\\腾讯\\QQ\\tencent\\聊天记录\\531891890\\FileRecv\\17monipdb\\17monipdb.dat");
//    	IP.load("/Users/wyj/Downloads/17monipdb.dat");

        /*Long st = System.nanoTime();
        for (int i = 0; i < 1; i++)
        {
            String[] ar = IP.find(randomIp());
            System.out.println(Arrays.toString(ar));
        }
        Long et = System.nanoTime();
        System.out.println((et - st) / 1000 / 1000);

        System.out.println(Arrays.toString(IP.find("192.169.176.207")));*/
        
        int start = 0;
        int max_comp_len = offset - 1028;
        long index_offset = -1;
        int index_length = -1;
        byte b = 0;
        for (start = start * 8 + 1024; start < max_comp_len; start += 8) {
        	index_offset = bytesToLong(b, indexBuffer.get(start + 6), indexBuffer.get(start + 5), indexBuffer.get(start + 4));
            index_length = 0xFF & indexBuffer.get(start + 7);
            
            byte[] areaBytes;

            lock.lock();
            try {
                dataBuffer.position(offset + (int) index_offset - 1024);
                areaBytes = new byte[index_length];
                dataBuffer.get(areaBytes, 0, index_length);
                System.out.print("ip:" + ipToStr(int2long(indexBuffer.getInt(start))) + "   ");
                System.out.print(Arrays.toString(new String(areaBytes, Charset.forName("UTF-8")).split("\t", -1)));
                System.out.println();
            } finally {
                lock.unlock();
            }
        }
        
        //System.out.println(ipToStr(str2Ip("255.255.255.255")));
        
        //2147483647 4294967296
    }

    public static boolean enableFileWatch = false;

    private static int offset;
    private static int[] index = new int[256];
    private static ByteBuffer dataBuffer;
    private static ByteBuffer indexBuffer;
    private static Long lastModifyTime = 0L;
    private static File ipFile ;
    private static ReentrantLock lock = new ReentrantLock();

    public static void load(String filename) {
        ipFile = new File(filename);
        load();
        if (enableFileWatch) {
            watch();
        }
    }

    public static void load(String filename, boolean strict) throws Exception {
        ipFile = new File(filename);
        if (strict) {
            int contentLength = Long.valueOf(ipFile.length()).intValue();
            if (contentLength < 512 * 1024) {
                throw new Exception("ip data file error.");
            }
        }
        load();
        if (enableFileWatch) {
            watch();
        }
    }

    public static String[] find(String ip) {
        int ip_prefix_value = new Integer(ip.substring(0, ip.indexOf(".")));
        long ip2long_value  = ip2long(ip);
        int start = index[ip_prefix_value];
        int max_comp_len = offset - 1028;
        long index_offset = -1;
        int index_length = -1;
        byte b = 0;
        for (start = start * 8 + 1024; start < max_comp_len; start += 8) {
            if (int2long(indexBuffer.getInt(start)) >= ip2long_value) {
                index_offset = bytesToLong(b, indexBuffer.get(start + 6), indexBuffer.get(start + 5), indexBuffer.get(start + 4));
                index_length = 0xFF & indexBuffer.get(start + 7);
                break;
            }
        }

        byte[] areaBytes;

        lock.lock();
        try {
            dataBuffer.position(offset + (int) index_offset - 1024);
            areaBytes = new byte[index_length];
            dataBuffer.get(areaBytes, 0, index_length);
        } finally {
            lock.unlock();
        }

        return new String(areaBytes, Charset.forName("UTF-8")).split("\t", -1);
    }

    private static void watch() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long time = ipFile.lastModified();
                if (time > lastModifyTime) {
                    lastModifyTime = time;
                    load();
                }
            }
        }, 1000L, 5000L, TimeUnit.MILLISECONDS);
    }

    private static void load() {
        lastModifyTime = ipFile.lastModified();
        FileInputStream fin = null;
        lock.lock();
        try {
            dataBuffer = ByteBuffer.allocate(Long.valueOf(ipFile.length()).intValue());
            fin = new FileInputStream(ipFile);
            int readBytesLength;
            byte[] chunk = new byte[4096];
            while (fin.available() > 0) {
                readBytesLength = fin.read(chunk);
                dataBuffer.put(chunk, 0, readBytesLength);
            }
            dataBuffer.position(0);
            int indexLength = dataBuffer.getInt();
            byte[] indexBytes = new byte[indexLength];
            dataBuffer.get(indexBytes, 0, indexLength - 4);
            indexBuffer = ByteBuffer.wrap(indexBytes);
            indexBuffer.order(ByteOrder.LITTLE_ENDIAN);
            offset = indexLength;
            
            int loop = 0;
            while (loop++ < 256) {
                index[loop - 1] = indexBuffer.getInt();
            }
            indexBuffer.order(ByteOrder.BIG_ENDIAN);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            lock.unlock();
        }
    }

    private static long bytesToLong(byte a, byte b, byte c, byte d) {
        return int2long((((a & 0xff) << 24) | ((b & 0xff) << 16) | ((c & 0xff) << 8) | (d & 0xff)));
    }

    private static int str2Ip(String ip)  {
        String[] ss = ip.split("\\.");
        int a, b, c, d;
        a = Integer.parseInt(ss[0]);
        b = Integer.parseInt(ss[1]);
        c = Integer.parseInt(ss[2]);
        d = Integer.parseInt(ss[3]);
        return (a << 24) | (b << 16) | (c << 8) | d;
    }
    
    public static String ipToStr(long ip) {
    	int a = (int) (ip << 32 >> 56 & 0xff), b = (int) (ip << 40 >> 56 & 0xff), c = (int) (ip << 48 >> 56 & 0xff), d = (int) (ip << 56 >> 56 & 0xff);
        return "".concat(a + ".").concat(b + ".").concat(c + ".").concat(d + "");
    }

    private static long ip2long(String ip)  {
        return int2long(str2Ip(ip));
    }

    private static long int2long(int i) {
        long l = i & 0x7fffffffL;
        if (i < 0) {
            l |= 0x080000000L;
        }
        return l;
    }
    
}