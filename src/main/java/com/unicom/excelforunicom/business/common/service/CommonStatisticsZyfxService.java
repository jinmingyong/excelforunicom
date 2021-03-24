package com.unicom.excelforunicom.business.common.service;

import com.unicom.excelforunicom.business.common.model.StatisticsZyfx;
import com.unicom.excelforunicom.business.common.model.StatisticsZyfx;
import com.unicom.excelforunicom.business.common.need.MyService;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
* @author JMY
* @date 2021/03/19
*/
public interface CommonStatisticsZyfxService extends MyService<StatisticsZyfx> {
    int batchInsertStatisticsZyfx(List<StatisticsZyfx> statisticsZyfxes);
    List<StatisticsZyfx> selectStatisticsZyfx();
    List<StatisticsZyfx> selectStatisticsZyfxTwoDay();
}
