package com.unicom.excelforunicom.business.common.service;

import com.unicom.excelforunicom.business.common.model.StatisticsZyfx;
import com.unicom.excelforunicom.business.common.model.Zyfx;
import com.unicom.excelforunicom.business.common.model.ZyfxTree;
import com.unicom.excelforunicom.business.common.need.MyService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
* @author JMY
* @date 2021/02/27
*/
public interface CommonZyfxService extends MyService<Zyfx> {

    /**
    *  分页查询
    * @param pageNum
    * @param pageSize
    * @return PageInfo
    */
    PageInfo selectAllForPage(Integer pageNum, Integer pageSize);

    PageInfo<Zyfx> selectAllZyfx(String zyfxName, Integer pageNum, Integer pageSize);

    PageInfo<Zyfx> selectZyfxByJuZhan(String juZhan, Integer pageNum, Integer pageSize);

    Integer updateZyfxByExcel(MultipartFile file) throws IOException;

    void downloadAllZyfxExcel(Zyfx zyfx, HttpServletRequest request, HttpServletResponse response) throws IOException;

    ZyfxTree selectZyfxTreeByJf(Zyfx zyfx);

    Integer updateStatisticsZyfx();

    Integer updateZyfxByExcel2(MultipartFile file) throws IOException;
}
