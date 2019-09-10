package com.lastlysly.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-09 15:27
 **/
public class MyExcelUtil {

    //log日志
    private static final Logger LOGGER = LoggerFactory.getLogger(MyExcelUtil.class);


    /**
     * 设置表头样式
     * @param wb
     * @return
     */
    private static XSSFCellStyle setHeadStyle(XSSFWorkbook wb) {
        XSSFCellStyle headStyle = wb.createCellStyle();
        // 设置背景颜色白色
        headStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        // 设置填充颜色
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置上下左右边框
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setBorderTop(BorderStyle.THIN);
        // 设置水平居中
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置标题字体
        XSSFFont headFont = wb.createFont();
        // 设置字体大小
        headFont.setFontHeightInPoints((short) 14);
        // 设置字体
        headFont.setFontName("宋体");
        // 设置字体粗体
        headFont.setBold(true);
        // 把字体应用到当前的样式
        headStyle.setFont(headFont);
        return headStyle;
    }

    /**
     * 设置单元格内容样式
     * @param wb
     * @return
     */
    private static XSSFCellStyle setCellStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置上下左右边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        //设置左对齐
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        // 设置标题字体
        XSSFFont cellFont = wb.createFont();
        // 设置字体大小
        cellFont.setFontHeightInPoints((short) 11);
        // 设置字体
        cellFont.setFontName("等线");
        // 把字体应用到当前的样式
        cellStyle.setFont(cellFont);
        return cellStyle;
    }


    /**
     * 获取表头的方法名
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return "get" + s;
        } else {
            return "get" + (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1))
                    .toString();
        }
    }

    /**
     * 自适应宽度(中文支持)
     * @param sheet
     * @param size
     */
    private static void setSizeColumn(XSSFSheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                XSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(columnNum) != null) {
                    XSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }

    /**
     * 导出响应
     * @param response
     * @param name
     * @param wb
     */
    public static void setReportNameAndLoad(HttpServletResponse response, String name, XSSFWorkbook wb) {
        BufferedOutputStream fos = null;
        try {
            // 设置响应输出的头类型
            //response.setContentType("application/vnd.ms-excel;charset=GBK");//导出xls格式
            response.setContentType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=GBK");//导出xlsx格式
            // 设置下载文件名称(注意中文乱码)
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String((name).getBytes("GB2312"), "ISO8859-1") + ".xlsx");
            response.setHeader("Pragma", "No-cache");
            fos = new BufferedOutputStream(response.getOutputStream());
            wb.write(fos);
        } catch (Exception e) {
            LOGGER.error("ExcelUtil->setReportNameAndLoad exception:", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    LOGGER.error("ExcelUtil->setReportNameAndLoad close outputStream exception:", e);
                }
            }
        }
    }
}

