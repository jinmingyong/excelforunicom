package com.unicom.excelforunicom.business.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jinmingyong
 * @date 2021/3/14 23:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ZyfxTree {
    private String juZhan;
    private String sbmc;
    private List<MoKuai> children;
}
