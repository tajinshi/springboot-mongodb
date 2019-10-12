package com.springboot.springtest.common.excel;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author tuojinshi
 * @ClassName: easyExcelUtils
 * @Description：easyExcel工具类 效率高于poi,jxl
 * @Date 2019/10/12 15:44
 */
public class EasyExcelUtils {
    /**
     * 下载excel
     * @param response
     * @param fileName  文件名 ,如:20110808.xls
     * @param cls     对象的Class
     * @param sheetName 表头名称
     * @param data   List<T> 需要导出的对象
     * @author
     */
    public static void exportExcle(HttpServletResponse response, String fileName, String sheetName, Class cls, List data) {
        try {
            //调用上面的方法、生成Excel文件
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName,"UTF-8") + ".xls");
            EasyExcel.write(response.getOutputStream(), cls).sheet(sheetName).doWrite(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
