/*
 * 文件名：ExportExcelColumnsOption.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年4月21日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.xls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chenrd.common.columnOption.ColumnOption;
import com.chenrd.example.Example;

public class ExportExcelColumnsOption implements Export
{
    
    private CellStyle titleStyle;
    
    private CellStyle style;
    
    private List<ColumnOption> options;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ExportExcelColumnsOption.class);

    /**
     * @param list
     * @param clazz
     * @param options
     */
    public ExportExcelColumnsOption(List<ColumnOption> options)
    {
        super();
        this.options = options;
    }
    
    /**
     * @param list
     * @param clazz
     * @param options
     */
    public ExportExcelColumnsOption(String[] options)
    {
        super();
        this.options = new ArrayList<ColumnOption>();
        for (String option : options)
            this.options.add(new ColumnOption(option, "", true));
    }
    
    public void setColumnsOption(String[] options) {
    	this.options = new ArrayList<ColumnOption>();
        for (String option : options)
            this.options.add(new ColumnOption(option, "", true));
    }
    
    /**
     * 
     * 
     * @return 
     * @see
     */
    @SuppressWarnings("unchecked")
	public <T> XSSFWorkbook export(String sheetTitle, List<T> list, Class<T> clazz)
    {
        XSSFWorkbook workbook = new XSSFWorkbook();
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
        
        style = workbook.createCellStyle();
        style.setBorderBottom((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderTop((short) 1);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        
        XSSFSheet sheet = workbook.createSheet(sheetTitle);
        sheet.setDefaultRowHeightInPoints(20);  
        XSSFRow titleRow = sheet.createRow(0);
        int i = 0;
        for (ColumnOption option : options)
        {
            if (!option.isChecked()) continue;
            
            XlsPortAnnotate ann = null;
            try
            {
                ann = clazz.getDeclaredField(option.getFieldName()).getAnnotation(XlsPortAnnotate.class);
            }
            catch (NoSuchFieldException | SecurityException e)
            {
                LOGGER.warn("导出属性：{}失败，class:{}类中不存在该属性", option.getFieldName(), clazz.getName(), e);
                continue;
            }
            if (ann == null)
            {
                continue;
            }
            Cell cell = titleRow.createCell(i);
            sheet.setColumnWidth(i, 20 * ann.width());
            cell.setCellStyle(titleStyle);
            cell.setCellValue(ann.value());
            iterationRow(sheet, (List<? extends Example>) list, option.getFieldName(), i++);
        }
        return workbook;
    }
    
    public <T extends Example> void iterationRow(XSSFSheet sheet, List<T> list, String fieldName, int columnIndex)
    {
        int rowIndex = 1;
        for (T t : list)
        {
            XSSFRow row = null;
            if ((row = sheet.getRow(rowIndex)) == null)
            {
                row = sheet.createRow(rowIndex);
            }
            Cell cell = row.createCell(columnIndex);
            cell.setCellStyle(style);
            Object value = t.get(fieldName);
            if (value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float)
            {
                cell.setCellValue(new Double(value + ""));
            }
            else if (value instanceof Date)
            { 
                cell.setCellValue((Date) value);
            }
            else if (value instanceof String)
            {
                cell.setCellValue((String) value);
            }
            rowIndex++;
        }
    }
    
}
