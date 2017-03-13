/*
 * 文件名：ReentrantReadWriteLockTest.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年2月15日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.test.lock;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {
	
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	private boolean isT = false;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		/*final ReentrantReadWriteLockTest lockTest = new ReentrantReadWriteLockTest();
		lockTest.test("线程1", 1000);*/
		/*Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lockTest.test("线程1", 2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lockTest.test("线程2", 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread1.start();
		thread2.start();*/
	}
	
	public void test(String name, int time) throws InterruptedException {
		System.out.println(11);
		lock.readLock().lock();
		System.out.println(lock.isFair());
		System.out.println(name + "进入读锁定");
		if (!isT) {
			Thread.sleep(time);
			lock.readLock().unlock();
			System.out.println(name + "释放读锁");
			
			lock.writeLock().lock();
			System.out.println(name + "进入写锁定");
			Thread.sleep(time);
			isT = true;
			System.out.println(lock.isWriteLockedByCurrentThread());
			lock.writeLock().unlock();
			System.out.println(name + "释放写锁定");
		}
	}
}
