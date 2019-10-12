package com.springboot.springtest.common.excel;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;


public class PoiExcelUtil {

    /**
     * 读取xls文件
     * @param startrow //开始行号
     * @param startcol //开始列号
     * @param sheetnum //sheet
     */
    public static List<Map<String,Object>> importExcelByFileForXls(int startrow, int startcol, int sheetnum,String filepath) {
        List<Map<String,Object>> varList = new ArrayList<>();

        try {
            File target = new File(filepath);
            FileInputStream fi = new FileInputStream(target);
            HSSFWorkbook wb = new HSSFWorkbook(fi);//xsl
            HSSFSheet sheet= wb.getSheetAt(sheetnum); //sheet 从0开始
            int rowNum = sheet.getLastRowNum() + 1;                     //取得最后一行的行号
            for (int i = startrow; i < rowNum; i++) {                    //行循环开始
                Map<String,Object> varpd = new HashMap<>();
                HSSFRow row = sheet.getRow(i);                             //行
                int cellNum = row.getLastCellNum();                     //每行的第一个单元格位置
                for (int j = startcol; j < cellNum; j++) {                //列循环开始
                    HSSFCell cell = row.getCell(Short.parseShort(j + ""));
                    String cellValue = null;
                    if (null != cell) {
                        switch (cell.getCellTypeEnum()) {  //判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                            case NUMERIC:
                                cellValue = String.valueOf((int) cell.getNumericCellValue());
                                break;
                            case STRING:
                                cellValue = cell.getStringCellValue();
                                break;
                            case FORMULA:
                                cellValue = cell.getNumericCellValue() + "";
                                break;
                            case BLANK:
                                cellValue = "";
                                break;
                            case BOOLEAN:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case ERROR:
                                cellValue = String.valueOf(cell.getErrorCellValue());
                                break;
                        }
                    } else {
                        cellValue = "";
                    }
                    varpd.put("var"+j, cellValue);
                }
                varList.add(varpd);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return varList;
    }

    /**
     * 读取xlsx文件
     * @param startrow
     * @param startcol
     * @param sheetnum
     * @param filePath
     * @return
     */
    public static List<Map<String,Object>> importExcelByFileForXlsx(int startrow, int startcol, int sheetnum,String filePath) {
        List<Map<String,Object>> varList = new ArrayList<>();
        try {
            File target = new File(filePath);
            FileInputStream fi = new FileInputStream(target);
            XSSFWorkbook wb = new XSSFWorkbook(fi);//xslx
            XSSFSheet sheet= wb.getSheetAt(sheetnum); //sheet 从0开始
            int rowNum = sheet.getLastRowNum() + 1;                     //取得最后一行的行号
            for (int i = startrow; i < rowNum; i++) {                    //行循环开始

                Map<String,Object> varpd = new HashMap<>();
                XSSFRow row = sheet.getRow(i);                             //行
                int cellNum = row.getLastCellNum();                     //每行的最后一个单元格位置
                for (int j = startcol; j < cellNum; j++) {                //列循环开始

                    XSSFCell cell = row.getCell(Short.parseShort(j + ""));
                    String cellValue = null;
                    if (null != cell) {
                        switch (cell.getCellTypeEnum()) {                     // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                            case NUMERIC:
                                cellValue = String.valueOf((int) cell.getNumericCellValue());
                                break;
                            case STRING:
                                cellValue = cell.getStringCellValue();
                                break;
                            case FORMULA:
                                cellValue = cell.getNumericCellValue() + "";
                                break;
                            case BLANK:
                                cellValue = "";
                                break;
                            case BOOLEAN:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case ERROR:
                                cellValue = String.valueOf(cell.getErrorCellValue());
                                break;
                        }
                    } else {
                        cellValue = "";
                    }
                    varpd.put("var"+j, cellValue);
                }
                varList.add(varpd);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return varList;
    }

    public static void main(String[] args) {
        String filePath = "C:/Users/Administrator/Desktop/现金贷产品字段说明.xlsx";
        String suf = filePath.substring(filePath.lastIndexOf(".")+1);
        List<Map<String, Object>> maps = null;
        if (Objects.equals("xlsx",suf)) {
           maps = importExcelByFileForXlsx(1, 0, 0, "C:/Users/Administrator/Desktop/现金贷产品字段说明.xlsx");
        }else if (Objects.equals("xls",suf)){
            maps = importExcelByFileForXls(1, 0, 0, "C:/Users/Administrator/Desktop/现金贷产品字段说明.xls");
        }else {
            System.out.println("请上传excel格式为：xlsx或xls");
        }
        for (Map<String, Object> map : maps) {
            System.out.println(map.toString());
        }
    }
}
