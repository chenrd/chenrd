/*
 * 文件名：XlsImPortResult.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2016年12月14日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.xls;

public class XlsImPortResult {
	
	private int successImport;
	
	private int replaceNum;
	
	private String replaceDetail;
	
	private int failImport;
	
	private String failDetail;

	/**
	 * @return Returns the successImport.
	 */
	public int getSuccessImport() {
		return successImport;
	}

	/**
	 * @param successImport The successImport to set.
	 */
	public void setSuccessImport(int successImport) {
		this.successImport = successImport;
	}

	/**
	 * @return Returns the failImport.
	 */
	public int getFailImport() {
		return failImport;
	}

	/**
	 * @param failImport The failImport to set.
	 */
	public void setFailImport(int failImport) {
		this.failImport = failImport;
	}

	/**
	 * @return Returns the failDetail.
	 */
	public String getFailDetail() {
		return failDetail;
	}

	/**
	 * @param failDetail The failDetail to set.
	 */
	public void setFailDetail(String failDetail) {
		this.failDetail = failDetail;
	}

	/**
	 * @return Returns the replaceNum.
	 */
	public int getReplaceNum() {
		return replaceNum;
	}

	/**
	 * @param replaceNum The replaceNum to set.
	 */
	public void setReplaceNum(int replaceNum) {
		this.replaceNum = replaceNum;
	}

	/**
	 * @return Returns the replaceDetail.
	 */
	public String getReplaceDetail() {
		return replaceDetail;
	}

	/**
	 * @param replaceDetail The replaceDetail to set.
	 */
	public void setReplaceDetail(String replaceDetail) {
		this.replaceDetail = replaceDetail;
	}

}
