/*
 * 文件名：AtomicReferenceTest.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年5月26日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.test.lock;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
	
	private AtomicReference<Test> reference = new AtomicReference<Test>();

	public static void main(String[] args) {
		AtomicReferenceTest atomicReferenceTest = new AtomicReferenceTest();
		atomicReferenceTest.reference.set(new Test(1));
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				atomicReferenceTest.reference.get().test();
				atomicReferenceTest.reference.set(new Test(2));
			}
		};
		Thread thread1 = new Thread(runnable, "thread1");
		Thread thread2 = new Thread(runnable, "thread2");
		Thread thread3 = new Thread(runnable, "thread3");
		Thread thread4 = new Thread(runnable, "thread4");
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
	}
}

class Test {
	
	public int index;
	
	public Test(int index) {
		this.index = index;
	}
	
	public void test() {
		System.out.println(Thread.currentThread().getName() + " into test method index=" + index);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
