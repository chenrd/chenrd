/*
 * 文件名：ExecutorTest.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年4月24日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.test.lock;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 线程池测试，
 * 
 * @author chenrd
 * @version 2017年4月24日
 * @see ExecutorTest
 * @since
 */
public class ExecutorTest {

	private ExecutorService executor = Executors.newFixedThreadPool(5);
	
	public static void main(String[] args) {
		ExecutorTest test = new ExecutorTest();
		test.executor.execute(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(2);
			}
		});
		
		try {
			Thread.sleep(1000);
			System.out.println(11);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.executor.shutdown();
		System.out.println(test.executor.isShutdown());

		ThreadGroup group = Thread.currentThread().getThreadGroup();
		System.out.println(group.activeCount());
		ThreadGroup topGroup = group;
	
		        // 遍历线程组树，获取根线程组
		while ( group != null ) {
		topGroup = group;
		group = group.getParent();
		}
		        // 激活的线程数加倍
		int estimatedSize = topGroup.activeCount() * 2;
		Thread[] slackList = new Thread[estimatedSize];
		        //获取根线程组的所有线程
		int actualSize = topGroup.enumerate(slackList);
		// copy into a list that is the exact size
		Thread[] list = new Thread[actualSize];
		System.arraycopy(slackList, 0, list, 0, actualSize);
		System.out.println(Arrays.toString(list));
		for (Thread thread : list) {
			System.out.println(thread.isAlive());
			System.out.println(thread.getName());
			System.out.println(Arrays.toString(thread.getStackTrace()));
		}
	}
}
