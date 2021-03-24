package com.unicom.excelforunicom.utils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jinmingyong
 * @date 2021/2/28 22:14
 */
public class ExcelUtil {
    private final static String excel2003L =".xls";    //2003- 版本的excel
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     * @param in,fileName
     * @return
     */
    public  List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception{
        List<List<Object>> list = null;

        //创建Excel工作薄
        Workbook work = this.getWorkbook(in,fileName);
        if(null == work){
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;  //页数
        Row row = null;  //行数
        Cell cell = null;  //列数

        list = new ArrayList<List<Object>>();
        List headerList = new ArrayList<List<Object>>();
        //遍历Excel中所有的sheet
        sheet = work.getSheetAt(0);
        if(sheet==null){return list;}
        row = sheet.getRow(0);
        int dzmcNum = 0;
        int jzNum = 0;
        List<Integer> excelNumList =new ArrayList<>();
        for (int f = row.getFirstCellNum(); f<row.getLastCellNum(); f++) {
            cell = row.getCell(f);
            if (this.getValue(cell).trim().equals("")){
                continue;
            }else if (this.getValue(cell).trim().equals("局站")){
                jzNum = f;
            }else if (this.getValue(cell).trim().equals("端子名称")){
                dzmcNum = f;
            }
            headerList.add(this.getValue(cell));
            excelNumList.add(f);
        }
        list.add(headerList);
        //遍历当前sheet中的所有行
        for (int j = sheet.getFirstRowNum()+1; j <= sheet.getLastRowNum(); j++) {
            row = sheet.getRow(j);
            if(row==null||this.getValue(row.getCell(dzmcNum)).equals("")||this.getValue(row.getCell(jzNum)).equals("")){continue;}
            //遍历所有的列
            List<Object> li = new ArrayList<Object>();
            for (Integer cellNum:excelNumList
                 ) {
                    cell = row.getCell(cellNum);
                    li.add(this.getValue(cell));
                }
                list.add(li);
            }
        return list;

    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(excel2003L.equals(fileType)){
            wb = new HSSFWorkbook(inStr);  //2003-
        }else if(excel2007U.equals(fileType)){
            wb = new XSSFWorkbook(inStr);  //2007+
        }else{
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     * @param cell
     * @return
     */
    //解决excel类型问题，获得数值
    public  String getValue(Cell cell) {
        String value = "";
        if(null==cell){
            return value;
        }
        switch (cell.getCellType()) {
            //数值型
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //如果是date类型则 ，获取该cell的date值
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    value = format.format(date);;
                }else {// 纯数字
                    BigDecimal big=new BigDecimal(cell.getNumericCellValue());
                    value = big.toString();
                    //解决1234.0  去掉后面的.0
                    if(null!=value&&!"".equals(value.trim())){
                        String[] item = value.split("[.]");
                        if(1<item.length&&"0".equals(item[1])){
                            value=item[0];
                        }
                    }
                }
                break;
            //字符串类型
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue().toString();
                break;
            // 公式类型
            case Cell.CELL_TYPE_FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case 0:
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setGroupingUsed(false);
                        value = String.valueOf(nf.format(cell.getNumericCellValue()));
                        break;
                    case 1:
                        value = cell.getStringCellValue();
                        break;
                    case 4:
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case 5:
                        value = "";
                        break;
                    default:
                        value = cell.getCellFormula();
                }
                break;
            // 布尔类型
            case Cell.CELL_TYPE_BOOLEAN:
                value = " "+ cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_ERROR:
                value = "";
                break;
            default:
                value = cell.getStringCellValue().toString();
        }
        if("null".endsWith(value.trim())){
            value="";
        }
        return value.trim();
    }
}
