/*
 * 文件名：Main.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年4月14日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.test.jdk8;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Hashtable;

public class Main {
	
	private static int a = 0;
	
	private int c = 0;
	
	
	public synchronized static void main(String[] args) throws InterruptedException {
		String a = "aaaaaaaaaaaabbbbbbbbbbaaaaaaaaaa";
		String b = a.substring(10, 20);
		byte e = 127;
		
		/*System.out.println(Runtime.getRuntime().freeMemory());
		int b = 0;Hashtable<K, V>
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1000_000_000; i++) {
			b++;
		}
		"".substring(beginIndex, endIndex)
		System.out.println(System.currentTimeMillis() - startTime);
		
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 1000_000_000; i++) {
			a++;
		}
		System.out.println(System.currentTimeMillis() - startTime);
		
		Main main = new Main();
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 1000_000_000; i++) {
			main.c++;
		}
		System.out.println(System.currentTimeMillis() - startTime);
		
		System.out.println(Runtime.getRuntime().freeMemory());
		
		byte d = 125;
		
		byte f = 0b00001111;*/
		/*MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();   
	    MemoryUsage usage = memorymbean.getHeapMemoryUsage();   
	    System.out.println("INIT HEAP: " + usage.getInit());   
	    System.out.println("MAX HEAP: " + usage.getMax());   
	    System.out.println("USE HEAP: " + usage.getUsed());   
	    System.out.println("\nFull Information:");   
	    System.out.println("Heap Memory Usage: "   
	    + memorymbean.getHeapMemoryUsage());   
	    System.out.println("Non-Heap Memory Usage: "   
	    + memorymbean.getNonHeapMemoryUsage());  */
	}

}
