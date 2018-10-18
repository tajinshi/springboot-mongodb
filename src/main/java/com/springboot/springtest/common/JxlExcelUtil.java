package com.springboot.springtest.common;

import jxl.Cell;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * jxl导出excel
 * excel导入
 */
public class JxlExcelUtil {

    public static List<List<String>> importExcel(File file) {
        Workbook book = null;
        List<List<String>> data;
        try {
            book = Workbook.getWorkbook(file);
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            int rows = sheet.getRows();
            int columns = sheet.getColumns();
            // 遍历每行每列的单元格
            data = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                List<String> rowData = new ArrayList<>();
                for (int j = 0; j < columns; j++) {
                    Cell cell = sheet.getCell(j, i);
                    String result = cell.getContents();
                    rowData.add(result);
                }
                data.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (book != null) {
                book.close();
            }
        }
        return data;
    }


    /**
     * @param objData   导出内容数组
     * @param sheetName 导出工作表的名称
     * @param fieldMap  导出Excel的表头数组
     * @return
     * @author
     */
    public static int exportToExcel(HttpServletResponse response, List<Map<String, Object>> objData, String sheetName, Map<String, String> fieldMap) {
        int flag = 0;
        //声明工作簿jxl.write.WritableWorkbook
        WritableWorkbook wwb;
        try {
            //根据传进来的file对象创建可写入的Excel工作薄
            OutputStream os = response.getOutputStream();

            wwb = Workbook.createWorkbook(os);

            /*
             * 创建一个工作表、sheetName为工作表的名称、"0"为第一个工作表
             * 打开Excel的时候会看到左下角默认有3个sheet、"sheet1、sheet2、sheet3"这样
             * 代码中的"0"就是sheet1、其它的一一对应。
             * createSheet(sheetName, 0)一个是工作表的名称，另一个是工作表在工作薄中的位置
             */
            WritableSheet ws = wwb.createSheet(sheetName, 0);

            SheetSettings ss = ws.getSettings();
            ss.setVerticalFreeze(1);//冻结表头

            WritableFont font1 = new WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.BOLD);
            WritableFont font2 = new WritableFont(WritableFont.createFont("微软雅黑"), 9, WritableFont.NO_BOLD);
            WritableCellFormat wcf = new WritableCellFormat(font1);
            WritableCellFormat wcf2 = new WritableCellFormat(font2);
            WritableCellFormat wcf3 = new WritableCellFormat(font2);//设置样式，字体

            //创建单元格样式
            //WritableCellFormat wcf = new WritableCellFormat();

            //背景颜色
            wcf.setBackground(jxl.format.Colour.YELLOW);
            wcf.setAlignment(Alignment.CENTRE);  //平行居中
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
            wcf3.setAlignment(Alignment.CENTRE);  //平行居中
            wcf3.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
            wcf3.setBackground(Colour.LIGHT_ORANGE);
            wcf2.setAlignment(Alignment.CENTRE);  //平行居中
            wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中

            /*
             * 这个是单元格内容居中显示
             * 还有很多很多样式
             */
            wcf.setAlignment(Alignment.CENTRE);

            //判断一下表头数组是否有数据
            if (fieldMap != null && fieldMap.keySet() != null && fieldMap.keySet().size() > 0) {


                //定义存放英文字段名和中文字段名的数组
                String[] enFields = new String[fieldMap.size()];
                String[] cnFields = new String[fieldMap.size()];

                //填充数组
                int count = 0;
                for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
                    enFields[count] = entry.getKey();
                    cnFields[count] = entry.getValue();
                    count++;
                }
                //填充表头
                for (int i = 0; i < cnFields.length; i++) {
                    Label label = new Label(i, 0, cnFields[i], wcf);
                    ws.addCell(label);
                }

                //判断表中是否有数据
                if (objData != null && objData.size() > 0) {
                    //循环写入表中数据
                    for (int i = 0; i < objData.size(); i++) {

                        //转换成map集合{activityName:测试功能,count:2}
                        Map<String, Object> map = objData.get(i);

                        //循环输出map中的子集：既列值
                        int j = 0;
                        for (String key : fieldMap.keySet()) {
                            //ps：因为要“”通用”“导出功能，所以这里循环的时候不是get("Name"),而是通过map.get(o)
                            Object obj = map.get(key);
                            if (null !=obj) {
                                ws.addCell(new Label(j, i + 1, String.valueOf(obj)));
                            }
                            j++;
                        }
                    }
                } else {
                    flag = -1;
                }
                //写入Excel工作表
                wwb.write();
                //关闭Excel工作薄对象
                wwb.close();
                //关闭流
                os.flush();
                os.close();
                os = null;
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        } catch (Exception ex) {
            flag = 0;
            ex.printStackTrace();
        }
        return flag;
    }


    /**
     * 下载excel
     *
     * @param response
     * @param fileName  文件名 ,如:20110808.xls
     * @param listData  数据源
     * @param sheetName 表头名称
     * @param fieldMap  列名称集合,如：{物品名称，数量，单价}
     * @author
     */
    public static void exportExcle(HttpServletResponse response, String fileName, List<Map<String, Object>> listData, String sheetName, Map<String, String> fieldMap) {
        try {
        //调用上面的方法、生成Excel文件
        response.reset();
        response.setContentType("application/vnd.ms-excel");        //改成输出excel文件
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName,"UTF-8") + ".xls");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        exportToExcel(response, listData, sheetName, fieldMap);
    }

    public static void main(String[] args) {
        File file = new File("C:/Users/Administrator/Desktop/现金贷产品字段说明.xls");
        List<List<String>> lists = importExcel(file);
        for (List<String> list : lists) {
            System.out.println(list.toString());
        }
    }
}
