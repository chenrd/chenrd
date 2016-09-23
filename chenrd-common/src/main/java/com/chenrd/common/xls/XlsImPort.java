/*
 * 文件名：XlsPort.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.xls;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author chenrd
 * @version 2016年6月8日
 * @see XlsImPort
 * @since
 */
public class XlsImPort<T>
{
    
    private final static Logger log = LoggerFactory.getLogger(XlsImPort.class);
    
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @SuppressWarnings("unused")
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private Class<T> clazz;
    
    private List<T> list = new ArrayList<T>();
    
    private int sheetIndex;
    
    
    private int index;
    
    private int titleIndex;
    
    //文件流的关闭在loadWorkbook方法的时候已经操作
    private InputStream stream;
    
    //迭代到第几列
    private int columnIndex;
    
    private Sheet sheet;
    
    private JSONObject jsonObject;
    
    /**
     * 
     * @param t
     * @param sheetIndex 第几个表格
     * @param index 从第几行开始遍历
     * @param titleIndex 表头所在行从0开始
     */
    public XlsImPort(Class<T> t, InputStream stream, int sheetIndex, int index, int titleIndex)
    {
        this.clazz = t;
        this.sheetIndex = sheetIndex;
        this.titleIndex = titleIndex;
        this.index = index;
        this.stream = stream;
    }
    
    public List<T> getResult()
    {
        return list;
    }
    
    public void excuteImport()
    {
        Workbook workbook = loadWorkbook(stream);
        if (workbook == null) {
            getLog().error("导入失败：没有正确加载文件");
            return;
        }
        
        this.sheet = workbook.getSheetAt(sheetIndex);
        
        jsonObject = loadjsonProperty(clazz, new JSONObject(), null);
        try
        {
            iterativeColumn();
        } catch (Exception e) {
            list.clear();
            getLog().error("导入失败：", e);
        }
    }
    
    /**
     * 迭代列外层循环
     * @param columnIndex 第几列
     * @param rowIndex 第几行
     * @param titleIndex 表头所在行
     * @param sheet 表格
     * @param jsonObject 属性列
     */
    private void iterativeColumn() throws Exception {
        Cell cell = sheet.getRow(titleIndex).getCell(columnIndex);
        if (cell == null) {
            return;
        }
        String title = cell.getStringCellValue().trim();
        getLog().debug("开始迭代列：" + title);
        if (jsonObject.get(title) != null)
        {
            iterativeRow(title, index);
        }
        ++columnIndex;
        iterativeColumn();
    }
    
    /**
     * 迭代行
     * @param columnIndex 第几列
     * @param rowIndex 第几行
     * @param sheet 表格
     * @param method 方法
     */
    @SuppressWarnings({"static-access", "unchecked"})
    private void iterativeRow(String title, int rowIndex) throws Exception {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return;
        }
        Object value = null, person = null;
        Cell cell = row.getCell(columnIndex);
        if (cell != null) {
            if (list.size() > rowIndex - index) {
                person = list.get(rowIndex - index);
            } else {
                person = clazz.newInstance();
                list.add((T) person);
            }
            String propertyName = (String) jsonObject.get(title);
            if (propertyName.indexOf(".") != -1)
            {
                person = getPropertyBean(person, propertyName);
            }
            
            Method method = getMethodByProperty(person, propertyName.substring(propertyName.lastIndexOf(".") + 1));
            
            if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = cell.getDateCellValue();
                } else {
                    value = new BigDecimal(cell.getNumericCellValue());
                    if (method.getParameterTypes()[0].isAssignableFrom(int.class) || method.getParameterTypes()[0].isAssignableFrom(Integer.class)) {
                        value = ((BigDecimal) value).intValue();
                    } else if (method.getParameterTypes()[0].isAssignableFrom(long.class) || method.getParameterTypes()[0].isAssignableFrom(Long.class)) {
                        value = ((BigDecimal) value).longValue();
                    }
                    else if (method.getParameterTypes()[0].isAssignableFrom(double.class) || method.getParameterTypes()[0].isAssignableFrom(Double.class)) {
                        value = ((BigDecimal) value).doubleValue();
                    }
                }
            } else {
                value = cell.getStringCellValue().trim();
            }
            if (value != null)
            {
                if (method.getParameterTypes()[0].isAssignableFrom(Date.class) && value.getClass().isAssignableFrom(String.class)) {
                    if (checkDateFormat((String) value)) {
                        value = dateFormat.parse(((String) value));
                    } else {
                        value = dateFormat.parse(((String) value).replace(".", "-") + "-01");
                    }
                }
                if (value.getClass().isAssignableFrom(String.class))
                {
                    if (method.getParameterTypes()[0].isAssignableFrom(int.class)) {
                        value = !StringUtils.isNotBlank((String) value) ? 0 : Integer.parseInt((String) value);
                    } else if (method.getParameterTypes()[0].isAssignableFrom(long.class)) {
                        value = !StringUtils.isNotBlank((String) value) ? 0 : Long.parseLong((String) value);
                    } else if (method.getParameterTypes()[0].isAssignableFrom(float.class)) {
                        value = !StringUtils.isNotBlank((String) value) ? 0.0f : Float.parseFloat((String) value);
                    } else if (method.getParameterTypes()[0].isAssignableFrom(double.class)) {
                        value = !StringUtils.isNotBlank((String) value) ? 0.0 : Double.parseDouble((String) value);
                    } else if (method.getParameterTypes()[0].isAssignableFrom(boolean.class)) {
                        value = !StringUtils.isNotBlank((String) value) ? true : (Integer.parseInt((String) value) == 0 ? false : true);
                    } else if (method.getParameterTypes()[0].isAssignableFrom(Integer.class)) {
                        value = !StringUtils.isNotBlank((String) value) ? null : Integer.parseInt((String) value);
                    } else if (method.getParameterTypes()[0].isAssignableFrom(Long.class)) {
                        value = !StringUtils.isNotBlank((String) value) ? null : Long.parseLong((String) value);
                    } else if (method.getParameterTypes()[0].isAssignableFrom(Float.class)) {
                        value = !StringUtils.isNotBlank((String) value) ? null : Float.parseFloat((String) value);
                    } else if (method.getParameterTypes()[0].isAssignableFrom(Double.class)) {
                        value = !StringUtils.isNotBlank((String) value) ? null : Double.parseDouble((String) value);
                    } else if (method.getParameterTypes()[0].isAssignableFrom(Boolean.class)) {
                        value = !StringUtils.isNotBlank((String) value) ? null : (Integer.parseInt((String) value) == 0 ? false : true);
                    }
                } else
                if (value instanceof Date && method.getParameterTypes()[0].isAssignableFrom(String.class))
                {
                    value = dateFormat.format((Date) value);
                } else
                if (!value.getClass().isAssignableFrom(String.class) && method.getParameterTypes()[0].isAssignableFrom(String.class))
                {
                    value = value + "";
                }
                method.invoke(person, value);
            }
        }
        else if (rowIndex - index == list.size())
        {
            person = clazz.newInstance();
            list.add((T) person);
        }
        iterativeRow(title, ++rowIndex);
    }
    
    /**
     * 获取属性对应的方法
     * @param jsonObject 属性对象
     * @param title 头
     * @return 方法
     */
    private Object getPropertyBean(Object o, String propertyName) throws Exception {
        String beanName = propertyName.substring(0, propertyName.indexOf("."));
        propertyName = propertyName.substring(propertyName.indexOf(".") + 1);
        Object bean = o.getClass().getMethod("get" + beanName.substring(0, 1).toUpperCase() + beanName.substring(1)).invoke(o);
        if (bean == null)
        {
            Class<?> c = o.getClass().getMethod("get" + beanName.substring(0, 1).toUpperCase() + beanName.substring(1)).getReturnType();
            bean = c.newInstance();
            o.getClass().getMethod("set" + beanName.substring(0, 1).toUpperCase() + beanName.substring(1), c).invoke(o, bean);
        }
        if (propertyName.indexOf(".") != -1)
        {
            return getPropertyBean(bean, propertyName);
        }
        else
        {
            return bean;
        }
    }
    
    /**
     * 获取属性对应的方法
     * @param jsonObject 属性对象
     * @param title 头
     * @return 方法
     */
    private Method getMethodByProperty(Object o, String propertyName) throws Exception {
        Class<?> type = getReturnType(o.getClass(), propertyName);
        return o.getClass().getMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), type);
    }
    
    /**
     * 
     * @param propertyName 属性名称
     * @return 属性类型
     */
    private Class<?> getReturnType(Class<?> c, String propertyName) throws Exception {
        Method method = null;
        try {
            method = c.getMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
        } catch (NoSuchMethodException e) {
            method = c.getMethod("is" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
        }
        return method.getReturnType();
    }
    
    /**
     * 验证时间格式是否为yyyy-mm-dd
     * @param value 日期
     * @return true or false
     */
    private static boolean checkDateFormat(String value) {
        Pattern p = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}$");
        Matcher m = p.matcher(value);
        return m.matches();
    }
    
    private static Workbook loadWorkbook(InputStream stream) {
        Workbook workbook = null;
        PushbackInputStream fileInputStream = null;
        try {
            fileInputStream = new PushbackInputStream(stream, 8);
            if (POIFSFileSystem.hasPOIFSHeader(fileInputStream)) {
                workbook = new HSSFWorkbook(fileInputStream);
                getLog().info("上传的xls文件版本是2003及以下版本");
            } else if (POIXMLDocument.hasOOXMLHeader(fileInputStream)) {
                workbook = new XSSFWorkbook(fileInputStream);
                getLog().info("上传的xls文件版本是2007及以上版本");
            }
            if (workbook == null) {
                throw new RuntimeException("导入的文件格式不正确！");
            }
            return workbook;
        } catch (IOException e) {
            getLog().error("加载文件失败",  e);
            return null;
        } finally {
            try
            {
                if (fileInputStream != null) fileInputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 加载json格式的属性配置文件
     * @return JSONObject
     */
    private JSONObject loadjsonProperty(Class<?> t, JSONObject object, String name) {
        Field[] fields = t.getDeclaredFields();
        for (Field field : fields)
        {
            XlsPortAnnotate anno = field.getAnnotation(XlsPortAnnotate.class);
            if (anno != null && anno.isImport())
            {
                if (StringUtils.isNotBlank(anno.value()))
                {
                    object.put(anno.value(), (StringUtils.isNotBlank(name) ? name + "." : "") + field.getName());
                }
                else
                {
                    loadjsonProperty(field.getType(), object, (StringUtils.isNotBlank(name) ? name + "." : "") + field.getName());
                }
            }
        }
        return object;
    }
    
    /**
     * @return Returns the log.
     */
    public static Logger getLog()
    {
        return log;
    }
}
