package com.unicom.excelforunicom.business.common.service;

import com.unicom.excelforunicom.business.common.model.Test;
import com.unicom.excelforunicom.business.common.need.MyService;
import com.github.pagehelper.PageInfo;


/**
* @author JMY
* @date 2020/08/15
*/
public interface CommonTestService extends MyService<Test> {

    /**
    *  分页查询
    * @param pageNum
    * @param pageSize
    * @return PageInfo
    */
    PageInfo selectAllForPage(Integer pageNum, Integer pageSize);

}
