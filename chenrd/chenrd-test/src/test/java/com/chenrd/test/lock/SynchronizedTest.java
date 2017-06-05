/*
 * 文件名：SynchronizedTest.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年4月24日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.test.lock;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedTest implements Runnable {
	
	private Lock lock;
	
	public static void main(String[] args) {
		
		Lock lock = new Lock();
		
		SynchronizedTest test1 = new SynchronizedTest();
		test1.lock = lock;
		SynchronizedTest test2 = new SynchronizedTest();
		test2.lock = lock;
		
		Thread thread1 = new Thread(test1, "线程1");
		Thread thread2 = new Thread(test2, "线程2");
		
		thread1.start();
		thread2.start();
	}

	@Override
	public void run() {
		System.out.println("进入：" + Thread.currentThread().getName());
		try {
			lock.add("111");
			lock.remove("111");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

class Lock {
	
	private Object object = new Object();
	
	public List<String> list = new ArrayList<String>();
	
	public void add(String str) throws InterruptedException {
		synchronized (object) {
			System.out.println(Thread.currentThread().getName() + " 进入添加方法");
			Thread.sleep(5000);
			list.add(str);
		}
	}
	
	public void remove(String str) {
		synchronized (object) {
			System.out.println(Thread.currentThread().getName() + " 进入删除方法");
			list.remove(str);
		}
	}
}
