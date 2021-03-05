package com.unicom.excelforunicom.business.common.service.impl;

import com.unicom.excelforunicom.business.common.dao.CommonZyfxDao;
import com.unicom.excelforunicom.business.common.model.Zyfx;
import com.unicom.excelforunicom.business.common.model.ZyfxExcelTitle;
import com.unicom.excelforunicom.business.common.service.CommonZyfxService;
import com.unicom.excelforunicom.business.common.need.AbstractMyService;
import com.unicom.excelforunicom.utils.ExcelH_For_P;
import com.unicom.excelforunicom.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author JMY
* @date 2021/02/27
*/
@Service
public class CommonZyfxServiceImpl extends AbstractMyService<Zyfx> implements CommonZyfxService {

    @Autowired
    private CommonZyfxDao commonZyfxDao;
    @Autowired
    private ZyfxExcelTitle zyfxExcelTitle;

    @Override
    public PageInfo selectAllForPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(commonZyfxDao.selectAll());
    }

    @Override
    public PageInfo<Zyfx> selectAllZyfx(String zyfxName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Zyfx.class);
        example.createCriteria().andLike("duanZiMingCheng","%"+zyfxName+"%");
        return new PageInfo(commonZyfxDao.selectByExample(example));
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateZyfxByExcel(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputStream in =null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<Object>> listob = null;
        List<Object> headers = null;
        List<Zyfx> addList = new ArrayList<>();
        List<Zyfx> updateList = new ArrayList<>();
        try {
            listob = new ExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < listob.size(); i++) {
            if (i==0){
                headers = listob.get(0);
            }else {
                List<Object> lo = listob.get(i);
                Example example = new Example(Zyfx.class);
                Zyfx z = ExcelH_For_P.zyfxAddParams(headers,lo);
                example.createCriteria().andEqualTo("duanZiMingCheng",z.getDuanZiMingCheng()).andEqualTo("juZhan",z.getJuZhan());
                Zyfx zyfx = commonZyfxDao.selectOneByExample(example);
                if (zyfx==null) {
                    addList.add(z);
                }else {
                    updateList.add(z);
                }
            }
        }
        if (addList.size()>0){
            if(commonZyfxDao.batchInsertZyfx(addList)<=0){
                return 0;
            }
        }
        if (updateList.size()>0){
            if(commonZyfxDao.batchUpdateZyfx(updateList)<=0) {
                return 0;
            }
        }
        return 1;
    }


    @Override
    public void downloadAllZyfxExcel(String juZhan,HttpServletRequest request, HttpServletResponse response) throws IOException {

        /*解决乱码问题，并打开输出流*/
        String fileName = "Zyfx";
        String userAgent = request.getHeader("User-Agent");
        boolean isMSIE = userAgent != null && (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("like Gecko") > -1);
        if (isMSIE) {
            fileName = URLEncoder.encode(fileName, "UTF8");
        } else {
            fileName = new String(fileName.getBytes("gbk"), "ISO8859-1");
        }
        // 设置强制下载不打开
        response.setContentType("application/octet-stream");
        // 设置文件名
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xls");
        ServletOutputStream out = response.getOutputStream();
        //从配置文件中获取表头信息
        List<String> list = zyfxExcelTitle.getExcelTitle();
        //绘制表格
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheetlist = wb.createSheet("资源分析");

        //设置列宽
        for (int i = 0; i < list.size(); i++) {
            if(i == 4 || i == 5 || i == 7) {
                sheetlist.setColumnWidth(i, 6050);
            }else{
                sheetlist.setColumnWidth(i, 3766);
            }
        }
        //表头格式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //表格格式（只加边框）
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //准备数据
        //先将报名表里所有活动id为eventId的记录的学生id和报名时间查出
        Example example = new Example(Zyfx.class);
        example.createCriteria().andEqualTo("juZhan",juZhan);
        List<Zyfx> zyfxList = commonZyfxDao.selectByExample(example);
        if (zyfxList.size() > 0) {
            //将数据装填进表格中
            for (int i = 0; i < zyfxList.size()+1; i++) {
                HSSFRow row = sheetlist.createRow(i);
                if (i == 0) {
                    for (int j = 0; j < list.size(); j++) {
                        HSSFCell cell = row.createCell(j);
                        cell.setCellValue(list.get(j));
                        cell.setCellStyle(cellStyle);
                    }
                } else {
                    row.createCell(0).setCellValue(zyfxList.get(i-1).getGuangLan());
                    row.createCell(1).setCellValue(zyfxList.get(i-1).getXianXin());
                    row.createCell(2).setCellValue(zyfxList.get(i-1).getDuanZiMingCheng());
                    row.createCell(3).setCellValue(zyfxList.get(i-1).getZhuangTai());
                    row.createCell(4).setCellValue(zyfxList.get(i-1).getZhuanYe());
                    row.createCell(5).setCellValue(zyfxList.get(i-1).getYeWuLeiBie());
                    row.createCell(6).setCellValue(zyfxList.get(i-1).getYeWuHao());
                    row.createCell(7).setCellValue(zyfxList.get(i-1).getYeWuMing());
                    row.createCell(8).setCellValue(zyfxList.get(i-1).getBiaoQian());
                    row.createCell(9).setCellValue(zyfxList.get(i-1).getDuiYingWeizhi());
                    row.createCell(10).setCellValue(zyfxList.get(i-1).getTiaoJieWeizhi());
                    row.createCell(11).setCellValue(zyfxList.get(i-1).getGuangJiaoWeizhi());
                    row.createCell(12).setCellValue(zyfxList.get(i-1).getGuangJiaoTiaojie());
                    row.createCell(13).setCellValue(zyfxList.get(i-1).getPeiXianGuangLanMingCheng());
                    row.createCell(17).setCellValue(zyfxList.get(i-1).getSheBeiMingCheng());
                    row.createCell(18).setCellValue(zyfxList.get(i-1).getMoKuaiMing());
                    row.createCell(19).setCellValue(zyfxList.get(i-1).getPan());
                    row.createCell(21).setCellValue(zyfxList.get(i-1).getDzmc());
                    row.createCell(23).setCellValue(zyfxList.get(i-1).getJuZhan());
                    row.createCell(24).setCellValue(zyfxList.get(i-1).getJiFang());
                    row.createCell(25).setCellValue(zyfxList.get(i-1).getSheiBeiId());
                    row.createCell(26).setCellValue(zyfxList.get(i-1).getDuanZiId());
                    Date createTime = zyfxList.get(i - 1).getBianGengRiQi();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
                    if(createTime != null){
                        String time = dateFormat.format(createTime);
                        row.createCell(14).setCellValue(time);
                    }else{
                        row.createCell(14).setCellValue("");
                    }
                }
            }
            //写出excel文件
            wb.write(out);
            out.close();
        }
    }


}
