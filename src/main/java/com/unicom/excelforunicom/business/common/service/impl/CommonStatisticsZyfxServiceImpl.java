package com.unicom.excelforunicom.business.common.service.impl;

import com.unicom.excelforunicom.business.common.dao.CommonStatisticsZyfxDao;
import com.unicom.excelforunicom.business.common.model.StatisticsZyfx;
import com.unicom.excelforunicom.business.common.model.StatisticsZyfx;
import com.unicom.excelforunicom.business.common.model.Zyfx;
import com.unicom.excelforunicom.business.common.service.CommonStatisticsZyfxService;
import com.unicom.excelforunicom.business.common.need.AbstractMyService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* @author JMY
* @date 2021/03/19
*/
@Service
public class CommonStatisticsZyfxServiceImpl extends AbstractMyService<StatisticsZyfx> implements CommonStatisticsZyfxService {

    @Autowired
    private CommonStatisticsZyfxDao commonStatisticsZyfxDao;


    @Override
    public int batchInsertStatisticsZyfx(List<StatisticsZyfx> statisticsZyfxes) {
        return commonStatisticsZyfxDao.batchInsertStatisticsZyfx(statisticsZyfxes);
    }

    @Override
    public List<StatisticsZyfx> selectStatisticsZyfx() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        Example example = new Example(StatisticsZyfx.class);
        example.createCriteria().andEqualTo("statisticsDate",dateFormat.format(new Date()));
        return commonStatisticsZyfxDao.selectByExample(example);
    }

    @Override
    public List<StatisticsZyfx> selectStatisticsZyfxTwoDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        return commonStatisticsZyfxDao.selectStatisticsZyfx(dateFormat.format(new Date()));
    }
}
