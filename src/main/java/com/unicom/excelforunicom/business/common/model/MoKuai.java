package com.unicom.excelforunicom.business.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

/**
 * @author jinmingyong
 * @date 2021/3/14 23:07
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MoKuai {
    private String moKuai;
    private List<Zyfx> children;
}
