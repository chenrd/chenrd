/*
 * 文件名：Tree.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年5月19日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author chenrd
 * @version 2015年5月19日
 * @see Tree
 * @since
 */
public class Tree implements Serializable
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -4367771705650103244L;
    
    /**
     * 
     */
    private String id;
    
    /**
     * 现实内容
     */
    private String text;
    
    /**
     * 样式图标
     */
    private String icon;
    
    /**
     * 标识
     */
    private String key;
    
    /**
     * 
     */
    private String url;
    
    /**
     * 父节点
     */
    private Tree parent;
    
    /**
     * 子节点集合
     */
    private List<Tree> childs;
    
    

    /**
     * 
     */
    public Tree()
    {
        super();
    }

    /**
     * @param id
     * @param text
     * @param key
     */
    public Tree(String id, String text, String key)
    {
        super();
        this.id = id;
        this.text = text;
        this.key = key;
    }

    /**
     * 
     * @param id {@link #id}
     * @param text {@link #text}
     * @param icon {@link #icon}
     * @param key {@link #key}
     */
    public Tree(String id, String text, String icon, String key, String url)
    {
        super();
        this.id = id;
        this.text = text;
        this.icon = icon;
        this.key = key;
        this.url = url;
    }
    
    

    /**
     * @return Returns the url.
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url The url to set.
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return Returns the id.
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return Returns the text.
     */
    public String getText()
    {
        return text;
    }

    /**
     * @param text The text to set.
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * @return Returns the icon.
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * @param icon The icon to set.
     */
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    /**
     * @return Returns the key.
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @param key The key to set.
     */
    public void setKey(String key)
    {
        this.key = key;
    }

    /**
     * @return Returns the parent.
     */
    public Tree getParent()
    {
        return parent;
    }

    /**
     * @param parent The parent to set.
     */
    public void setParent(Tree parent)
    {
        this.parent = parent;
    }

    /**
     * @return Returns the childs.
     */
    public List<Tree> getChilds()
    {
        return childs;
    }

    /**
     * @param childs The childs to set.
     */
    public void setChilds(List<Tree> childs)
    {
        this.childs = childs;
    }

    /**
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
    

}
