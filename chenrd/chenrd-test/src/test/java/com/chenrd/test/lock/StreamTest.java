/*
 * 文件名：StreamTest.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年5月3日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.test.lock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class StreamTest {

	public static void main(String[] args) throws Exception {
		String book = "{\"coverUrl\":\"http://www.baidu.com/cc\",\"wordNumber\":1000000,\"author\":\"牛语者\",\"introduce\":\"22222222222\",\"sex\":1,\"name\":\"仙剑是神曲\",\"indexUrl\":\"http://www.baidu.com/ss\",\"id\":10,\"type\":1}";
		byte[] bytes = book.getBytes();
		Book b = JSONObject.parseObject(book, Book.class);
		long curr = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			b = JSONObject.parseObject(new String(bytes, "UTF-8"), Book.class);
		}
		System.out.println(System.currentTimeMillis() - curr);
		
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream = new ObjectOutputStream(arrayOutputStream);
		outputStream.writeObject(b);
		
		bytes = arrayOutputStream.toByteArray();
		outputStream.close();
		arrayOutputStream = null;
		
		
		curr = System.currentTimeMillis();
		ObjectInputStream inputStream = null;
		for (int i = 0; i < 1000000; i++) {
			inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			b = (Book) inputStream.readObject();
			inputStream.close();
		}
		System.out.println(System.currentTimeMillis() - curr);
		/*Book book = new Book();
		long curr = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			Book.class.getDeclaredMethod("getId").invoke(book);
		}
		System.out.println(System.currentTimeMillis() - curr);
		
		curr = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			book.getId();
		}
		System.out.println(System.currentTimeMillis() - curr);*/
	}
	
	public static class Book implements Serializable {
		/**
		 * 意义，目的和功能，以及被用到的地方<br>
		 */
		private static final long serialVersionUID = 7333007241747325435L;
		
		int id;
	    String name; //书本名称
	    String author; //作者
	    int type; //类别
	   	int sex; //男生 女生
	    String indexUrl; //主页URL
	    String coverUrl; //封面地址
	    String introduce; //内容介绍
	    Integer wordNumber; //文字数量
		/**
		 * @return Returns the id.
		 */
		public int getId() {
			return id;
		}
		/**
		 * @param id The id to set.
		 */
		public void setId(int id) {
			this.id = id;
		}
		/**
		 * @return Returns the name.
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name The name to set.
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return Returns the author.
		 */
		public String getAuthor() {
			return author;
		}
		/**
		 * @param author The author to set.
		 */
		public void setAuthor(String author) {
			this.author = author;
		}
		/**
		 * @return Returns the type.
		 */
		public int getType() {
			return type;
		}
		/**
		 * @param type The type to set.
		 */
		public void setType(int type) {
			this.type = type;
		}
		/**
		 * @return Returns the sex.
		 */
		public int getSex() {
			return sex;
		}
		/**
		 * @param sex The sex to set.
		 */
		public void setSex(int sex) {
			this.sex = sex;
		}
		/**
		 * @return Returns the indexUrl.
		 */
		public String getIndexUrl() {
			return indexUrl;
		}
		/**
		 * @param indexUrl The indexUrl to set.
		 */
		public void setIndexUrl(String indexUrl) {
			this.indexUrl = indexUrl;
		}
		/**
		 * @return Returns the coverUrl.
		 */
		public String getCoverUrl() {
			return coverUrl;
		}
		/**
		 * @param coverUrl The coverUrl to set.
		 */
		public void setCoverUrl(String coverUrl) {
			this.coverUrl = coverUrl;
		}
		/**
		 * @return Returns the introduce.
		 */
		public String getIntroduce() {
			return introduce;
		}
		/**
		 * @param introduce The introduce to set.
		 */
		public void setIntroduce(String introduce) {
			this.introduce = introduce;
		}
		/**
		 * @return Returns the wordNumber.
		 */
		public Integer getWordNumber() {
			return wordNumber;
		}
		/**
		 * @param wordNumber The wordNumber to set.
		 */
		public void setWordNumber(Integer wordNumber) {
			this.wordNumber = wordNumber;
		}
	    
	}
}
