package com.unicom.excelforunicom.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.text.DecimalFormat;

/**
 * Poi工具类
 * @author Hukaihan
 * @date 2019/6/14
 */
public class PoiUtil {
    private static FormulaEvaluator evaluator;
    /**
     * 设置Excel数据有效性 让某一范围的单元格可以下拉选择固定值，且会自动进行校验
     * 如果输入的不是下拉列表的值，则会出错
     *
     * @param sheet
     * @param textlist
     * @param firstRow
     * @param endRow
     * @param firstCol
     * @param endCol
     * @return
     */
    public static HSSFSheet setHSSFValidation(HSSFSheet sheet,
                                              String[] textlist, int firstRow, int endRow, int firstCol,
                                              int endCol) {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint
                .createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_list = new HSSFDataValidation(
                regions, constraint);
        sheet.addValidationData(data_validation_list);
        return sheet;
    }

    /**
     * 设置单元格提示信息
     * @param sheet
     * @param promptTitle
     * @param promptContent
     * @param firstRow
     * @param endRow
     * @param firstCol
     * @param endCol
     * @return
     */
    public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,
                                          String promptContent, int firstRow, int endRow, int firstCol,
                                          int endCol) {
        // 构造constraint对象
        DVConstraint constraint = DVConstraint
                .createCustomFormulaConstraint("BB1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_view = new HSSFDataValidation(
                regions, constraint);
        data_validation_view.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(data_validation_view);
        return sheet;
    }

    /**
     *  根据单元格数据是否为空，判断该行数据是否有效
     *  具体需要哪些单元格不为空 需要查询数据库哪些字段 not null
     * @param row  行对象
     * @param indexs  不为空的单元格下标
     * @param colCount  一共有多少字段
     * @return
     */
    public static boolean isRowEffectiveness(Row row, int[] indexs, int colCount){
        boolean flag = true;
        for(int i = 0; i < colCount; i++){
            Cell cell = row.getCell(i);
            for (int index : indexs) {
                //如果这个单元格是需要不为空的,则判断它是否不为空
                if (i == index) {
                    if (cell == null || cell.equals("") || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        flag = false;
                        break;
                    }
                }
            }
            if(!flag) {
                break;
            }
        }
        return flag;
    }

    /**
     * 获取单元格各类型值，返回字符串类型
     * @param cell
     * @return
     */
    public static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        int cellType=cell.getCellType();
        if(cellType== Cell.CELL_TYPE_FORMULA){ //表达式类型
            cellType=evaluator.evaluate(cell).getCellType();
        }

        switch (cellType) {
            case Cell.CELL_TYPE_STRING: //字符串类型
                cellValue= cell.getStringCellValue().trim();
                cellValue=StringUtils.isNullOrEmpty(cellValue) ? "" : cellValue;
                break;
            case Cell.CELL_TYPE_BOOLEAN:  //布尔类型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC: //数值类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                    cellValue =    cell.getDateCellValue().toString();
                } else {  //否
                    cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                }
                break;
            default: //其它类型，取空串
                cellValue = "";
                break;
        }
        return cellValue;
    }

    /**
     * @desc  设置excel文本格式
     * @param targetWorkbook
     * @param targetSheet
     * @param startRow
     * @param startColumn
     * @param endRow
     * @param endColumn
     */
    public static void setColumnToTextFormat(HSSFWorkbook targetWorkbook, HSSFSheet targetSheet, int startRow, int startColumn, int endRow, int endColumn) throws Exception {
        HSSFCellStyle cellStyle = targetWorkbook.createCellStyle();
        HSSFDataFormat format = targetWorkbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        if(startRow <0 || endRow <0 || startColumn <0 || endColumn <0 || (startRow > endRow) || (startColumn > endColumn)){
            throw new Exception("生成Excel格式参数错误!");
        }
        for(int rowIndex = startRow ; rowIndex <= endRow ; rowIndex ++ ){
            HSSFRow row = targetSheet.getRow(rowIndex);
            if(row == null ) {
                row = targetSheet.createRow(rowIndex);
            }
            for (int columnIndex = startColumn ; columnIndex <= endColumn ; columnIndex ++ ){
                HSSFCell cell = row.getCell(columnIndex);
                if (cell == null){
                    cell = row.createCell(columnIndex);
                }
                String rawValue1 = cell.getStringCellValue();
                cell.setCellStyle(cellStyle);
                if(!StringUtils.isNullOrEmpty(rawValue1)){
                    cell.setCellValue(rawValue1);
                }
            }
        }
    }

    /**
     * 判断excel有效行数(有效数据大于等于2可用，并且不能出现间断的全空行)
     * @return
     */
    public static int queryRowEffectiveness(HSSFWorkbook wb){
        Sheet sheet = wb.getSheetAt(0);
        CellReference cellReference = new CellReference("A4");
        boolean flag = false;
        for (int i = cellReference.getRow(); i <= sheet.getLastRowNum();) {
            Row r = sheet.getRow(i);
            if(r == null){
                // 如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
                sheet.shiftRows(i+1, sheet.getLastRowNum(),-1);
                continue;
            }
            flag = false;
            for(Cell c:r){
                if(c.getCellType() != Cell.CELL_TYPE_BLANK){
                    flag = true;
                    break;
                }
            }
            if(flag){
                i++;
                continue;
            }
            else{
                //如果是空白行（即可能没有数据，但是有一定格式）
                if(i == sheet.getLastRowNum()) {
                    //如果到了最后一行，直接将那一行remove掉
                    sheet.removeRow(r);
                } else {
                    //如果还没到最后一行，则数据往上移一行
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                }
            }
        }
        return sheet.getLastRowNum();
    }

    /**
     *  根据单元格数据是否为空，判断该行数据是否有效
     *  具体需要哪些单元格不为空 需要查询数据库哪些字段 not null
     * @param wb  表格对象
     * @param indexs  不为空的单元格下标
     * @param colCount  一共有多少字段
     * @return
     */
    public static int getEffectiveRowNum(HSSFWorkbook wb, int[] indexs, int colCount){
        boolean flag = true;
        Sheet sheet = wb.getSheetAt(0);
        //有效行数
        int rowNum = 0;
        for (Row row : sheet) {
            int effectiveValue = 0;
            for(int i = 0; i < colCount; i++){
                Cell cell = row.getCell(i);
                for (int index : indexs) {
                    //如果这个单元格是需要不为空的,则判断它是否不为空
                    if (i == index) {
                        if (!StringUtils.isNullOrEmpty(cell)) {
                            effectiveValue++;
                            break;
                        }
                        if (i == colCount - 1) {
                            flag = false;
                        }
                    }
                }
                if (effectiveValue > 0) {
                    break;
                }
            }
            if (effectiveValue > 0) {
                rowNum++;
            }
            if(!flag) {
                break;
            }
        }
        //减去表头那一行
        return rowNum - 1;
    }

}
