package com.unicom.excelforunicom.business.common.service.impl;

import com.unicom.excelforunicom.business.common.dao.CommonTestDao;
import com.unicom.excelforunicom.business.common.model.Test;
import com.unicom.excelforunicom.business.common.service.CommonTestService;
import com.unicom.excelforunicom.business.common.need.AbstractMyService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
* @author JMY
* @date 2020/08/15
*/
@Service
public class CommonTestServiceImpl extends AbstractMyService<Test> implements CommonTestService {

    @Autowired
    private CommonTestDao commonTestDao;

    @Override
    public PageInfo selectAllForPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(commonTestDao.selectAll());
    }

}
