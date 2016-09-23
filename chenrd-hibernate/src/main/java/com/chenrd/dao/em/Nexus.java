/*
 * 文件名：Nexus.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年5月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao.em;

/**
 * 查询属性关系
 * 
 * @author chenrd
 * @version 2016年5月18日
 * @see Nexus
 * @since
 */
public enum Nexus {
    
    EQUAL("="), GT(">"), LT("<"), LIKE("like"), BETWEEN("between"), GTEQUAL(">="), LTEQUAL("<="), IN("in");
    /**
     * 符号
     */
    public String sign;
    
    Nexus(String sign)
    {
        this.sign = sign;
    }
}
