/*
 * 文件名：DateTest.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年3月24日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.test.lock;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTest {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		System.out.println(dateFormat.parse("2017-03").getTime());
		System.out.println(dateFormat.parse("2017-04").getTime());
		System.out.println(dateFormat.parse("2017-05").getTime());
		System.out.println(dateFormat.parse("2017-06").getTime());
		System.out.println(dateFormat.parse("2017-07").getTime());
		System.out.println(dateFormat.parse("2017-08").getTime());
		System.out.println(dateFormat.parse("2017-09").getTime());
		System.out.println(dateFormat.parse("2017-10").getTime());
		System.out.println(dateFormat.parse("2017-11").getTime());
		System.out.println(dateFormat.parse("2017-12").getTime());
		System.out.println(dateFormat.parse("2018-01").getTime());
	}

}
