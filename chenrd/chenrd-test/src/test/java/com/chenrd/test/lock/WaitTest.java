/*
 * 文件名：WaitTest.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年4月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.test.lock;

public class WaitTest {

	private static Object object = new Object();
    
    public static void main(String[] args) {
          
          Thread thread = new Thread(new Runnable() {
                 
                  @Override
                  public void run() {
                        synchronized (object ) {
                               try {
                                     System.out.println(Thread.currentThread().getName() + "延迟5000去释放" );
                                     Thread.sleep(5000);
                                      object.notifyAll();
                              } catch (InterruptedException e ) {
                                      // TODO Auto-generated catch block
                                      e.printStackTrace();
                              }
                       }
                 }
          });
           thread.start();
          
           try {
                  synchronized (object ) {
                       System.out.println(Thread.currentThread().getName() + "进入等待" );
                       object.wait();
                       System. out.println("释放成功" );
                 }
          } catch (InterruptedException e ) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
          }
   }

}
