package com.unicom.excelforunicom.business.common.service;

import com.unicom.excelforunicom.business.common.model.Guangxian;
import com.unicom.excelforunicom.business.common.need.MyService;
import com.github.pagehelper.PageInfo;


/**
* @author JMY
* @date 2021/02/23
*/
public interface CommonGuangxianService extends MyService<Guangxian> {

    /**
    *  分页查询
    * @param pageNum
    * @param pageSize
    * @return PageInfo
    */
    PageInfo selectAllForPage(Integer pageNum, Integer pageSize);

}
