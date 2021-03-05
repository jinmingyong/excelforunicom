package com.unicom.excelforunicom.business.common.service.impl;

import com.unicom.excelforunicom.business.common.dao.CommonGuangxianDao;
import com.unicom.excelforunicom.business.common.model.Guangxian;
import com.unicom.excelforunicom.business.common.service.CommonGuangxianService;
import com.unicom.excelforunicom.business.common.need.AbstractMyService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
* @author JMY
* @date 2021/02/23
*/
@Service
public class CommonGuangxianServiceImpl extends AbstractMyService<Guangxian> implements CommonGuangxianService {

    @Autowired
    private CommonGuangxianDao commonGuangxianDao;

    @Override
    public PageInfo selectAllForPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(commonGuangxianDao.selectAll());
    }

}
