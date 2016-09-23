/*
 * 文件名：XlsExPort.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.xls;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chenrd.common.ocp.DateFormat;

/**
 * Xls导出，自动递归父类的所有需要导出的字段
 * 
 * @author chenrd
 * @version 2016年6月8日
 * @see XlsExPort
 * @since
 */
public class XlsExPort<T> implements Export
{
    
    private final static Logger LOG = LoggerFactory.getLogger(XlsExPort.class);
    
    private static final String[] methodPrefix = new String[]{"get", "is"};
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private XSSFWorkbook workbook = new XSSFWorkbook();
    
    private CellStyle titleStyle = workbook.createCellStyle();
    
    private CellStyle baseStyle = workbook.createCellStyle();
    
    private List<T> list;
    
    private Class<T> clazz;
    
    private XSSFSheet sheet;
    
    private int columnIndex = 0;
    
    private Method method;
    
    private Field field;
    
    public XlsExPort(Class<T> clazz, List<T> list, String sheetTitle)
    {
        titleStyle = workbook.createCellStyle();
        titleStyle.setBorderBottom((short) 1);
        titleStyle.setBorderRight((short) 1);
        titleStyle.setBorderLeft((short) 1);
        titleStyle.setBorderTop((short) 1);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        titleStyle.setFont(font);
        
        
        baseStyle = workbook.createCellStyle();
        baseStyle.setBorderBottom((short) 1);
        baseStyle.setBorderRight((short) 1);
        baseStyle.setBorderLeft((short) 1);
        baseStyle.setBorderTop((short) 1);
        baseStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        
        this.list = list;
        this.clazz = clazz;
        
        sheet = workbook.createSheet(sheetTitle);
        sheet.setDefaultRowHeightInPoints(20);  
        exportWorkbook();
    }
    
    public XSSFWorkbook export()
    {
        return workbook;
    }
    
    private void exportWorkbook()
    {
        XSSFRow titleRow = sheet.createRow(0);
        for (Field field : clazz.getDeclaredFields())
        {
            XlsPortAnnotate annotate = field.getAnnotation(XlsPortAnnotate.class);
            if (annotate == null || !annotate.isExport())
            {
                continue;
            }
            Cell cell = titleRow.createCell(columnIndex);
            sheet.setColumnWidth(columnIndex, 20 * annotate.width());
            cell.setCellStyle(titleStyle);
            cell.setCellValue(annotate.value());
            
            try
            {
                if (field.getType().isAssignableFrom(boolean.class) || field.getType().isAssignableFrom(Boolean.class))
                    method = clazz.getDeclaredMethod(methodPrefix[1] + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
                else
                    method = clazz.getDeclaredMethod(methodPrefix[0] + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
                
                this.field = field;
                iterationRow();
            }
            catch (NoSuchMethodException | SecurityException e)
            {
                LOG.warn("导出字段：{}失败，没有找到字段的getset方法", field.getName());
                continue;
            }
            catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
            {
                LOG.warn("导出字段：{}失败，调用字段的getset方法的时候错误", field.getName(), e);
                continue;
            }
            columnIndex++;
        }
        if (clazz.getGenericSuperclass() != null)
            exportGenericSuperclass((Class<?>) clazz.getGenericSuperclass());
    }
    
    private void iterationRow() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        DateFormat format = null;
        int rowIndex = 1;
        for (T t : list)
        {
            XSSFRow row = null;
            if ((row = sheet.getRow(rowIndex)) == null)
            {
                row = sheet.createRow(rowIndex);
            }
            Cell cell = row.createCell(columnIndex);
            cell.setCellStyle(baseStyle);
            Object value = method.invoke(t);
            if (value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float)
            {
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                cell.setCellValue(new Double(value + ""));
            }
            else if (value instanceof Date)
            { 
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                if ((format = field.getAnnotation(DateFormat.class)) != null)
                    value = format.value().format.equals("yyyy-MM-dd") ? dateFormat.format((Date) value) : dateTimeFormat.format((Date) value);
                else
                    value = (method.getName().lastIndexOf("Time") == method.getName().length() - 5 ? dateTimeFormat.format((Date) value) : dateFormat.format((Date) value));
                cell.setCellValue((String) value);
            }
            else if (value instanceof String)
            {
                cell.setCellValue((String) value);
            }
            rowIndex++;
        }
    }
    
    private void exportGenericSuperclass(Class<?> clazz)
    {
        for (Field field : clazz.getDeclaredFields())
        {
            XlsPortAnnotate annotate = field.getAnnotation(XlsPortAnnotate.class);
            if (annotate == null || !annotate.isExport()) continue;
            try
            {
                if (field.getType().isAssignableFrom(boolean.class) || field.getType().isAssignableFrom(Boolean.class))
                    method = clazz.getDeclaredMethod(methodPrefix[1] + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
                else
                    method = clazz.getDeclaredMethod(methodPrefix[0] + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
                
                this.field = field;
                iterationRow();
            }
            catch (NoSuchMethodException | SecurityException e)
            {
                LOG.warn("导出字段：{}失败，没有找到字段的getset方法", field.getName());
                continue;
            }
            catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
            {
                LOG.warn("导出字段：{}失败，调用字段的getset方法的时候错误", field.getName(), e);
                continue;
            }
        }
        if (clazz.getGenericSuperclass() != null)
            exportGenericSuperclass((Class<?>) clazz.getGenericSuperclass());
    }
}
