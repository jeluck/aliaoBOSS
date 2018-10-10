package com.ssctrl.interface4;


import org.apache.poi.hssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ExcelUtils {

    /**
     * 创建一个excel文件流，输出下载 ，限制导出数据量为5000条<br/>
     * 表头列名放在list的第一个元素中，也是以list形式存放，前后顺序要注意（先进先出原则） <br/>
     *
     * @param theName                                      ：文件名称
     * @param list：元素为List类型，List中存放字段值,以excel列展示的前后顺序先后存放
     * @param response
     * @author xiangyongqiang
     */
    public void export(String theName, List<List<String>> list, HttpServletResponse response) {
        String fileName = theName + ".xls";//theName + TimeUtil.getCurrentDate() + ".xlsx";
        try {
            fileName = new String(fileName.getBytes("UTF-8"), "iso8859-1");
            // 设置响应的编码方式;
            response.setCharacterEncoding("gb2312");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/msexcel;charset=UTF-8");

            ServletOutputStream out = response.getOutputStream();   //响应输出流对象
            HSSFWorkbook wb = new HSSFWorkbook();                   //创建Excel表格
            HSSFSheet sheet = wb.createSheet("用户注册信息");       //创建工作薄
            sheet.setColumnWidth(4, 5000);                          //设置列宽
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
            HSSFRow row;
            HSSFCell cell;
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(i);
                for (int j = 0; j < list.get(i).size(); j++) {
                    cell = row.createCell(j);
                    cell.setCellStyle(style);
                    cell.setCellValue(new HSSFRichTextString(list.get(i).get(j)));
                }
            }
            wb.write(out);                                   //将响应流输入到Excel表格中
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HSSFCellStyle getContentStyle(HSSFWorkbook workbook) {
        HSSFCellStyle contentStyle = workbook.createCellStyle(); //设置内容行格式
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        HSSFDataFormat df = workbook.createDataFormat();  //此处设置数据格式
        contentStyle.setDataFormat(df.getFormat("#,#0")); //数据格式只显示整数，如果是小数点后保留两位，可以写contentStyle.setDataFormat(df.getFormat("#,#0.00"));
        return contentStyle;
    }

}

