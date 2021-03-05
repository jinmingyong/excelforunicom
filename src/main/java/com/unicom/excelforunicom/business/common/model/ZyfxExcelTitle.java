package com.unicom.excelforunicom.business.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jinmingyong
 * @date 2021/3/4 16:42
 */
@Data
@AllArgsConstructor
@Component
@ConfigurationProperties("titles.zyfx")
public class ZyfxExcelTitle {
    private List<String> excelTitle = new ArrayList<>();
}
