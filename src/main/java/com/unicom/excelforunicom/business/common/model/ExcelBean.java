package com.unicom.excelforunicom.business.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import javax.persistence.Entity;

/**
 * @author jinmingyong
 * @date 2021/2/28 22:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ExcelBean {
    private String headTextName;//列头（标题）名
    private String propertyName;//对应字段名
    private Integer cols;//合并单元格数
    private XSSFCellStyle cellStyle;
    public ExcelBean(String headTextName, String propertyName){
        this.headTextName = headTextName;
        this.propertyName = propertyName;
    }
    public ExcelBean(String headTextName, String propertyName, Integer cols) {
        super();
        this.headTextName = headTextName;
        this.propertyName = propertyName;
        this.cols = cols;
    }
}
