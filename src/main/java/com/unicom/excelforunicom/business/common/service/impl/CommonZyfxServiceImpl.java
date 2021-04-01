package com.unicom.excelforunicom.business.common.service.impl;

import com.unicom.excelforunicom.business.common.dao.CommonStatisticsZyfxDao;
import com.unicom.excelforunicom.business.common.dao.CommonZyfxDao;
import com.unicom.excelforunicom.business.common.dao.ZyfxDao;
import com.unicom.excelforunicom.business.common.model.*;
import com.unicom.excelforunicom.business.common.service.CommonZyfxService;
import com.unicom.excelforunicom.business.common.need.AbstractMyService;
import com.unicom.excelforunicom.utils.ExcelH_For_P;
import com.unicom.excelforunicom.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author JMY
* @date 2021/02/27
*/
@Service
public class CommonZyfxServiceImpl extends AbstractMyService<Zyfx> implements CommonZyfxService {

    @Resource
    private CommonZyfxDao commonZyfxDao;
    @Resource
    private ZyfxDao zyfxDao;
    @Autowired
    private ZyfxExcelTitle zyfxExcelTitle;
    @Autowired
    private ZyfxJhExcelTitle zyfxJhExcelTitle;
    @Autowired
    private CommonStatisticsZyfxDao commonStatisticsZyfxDao;

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

    @Override
    public PageInfo<Zyfx> selectZyfxByJuZhan(String juZhan, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Zyfx.class);
        example.createCriteria().andEqualTo("juZhan",juZhan);
        return new PageInfo(zyfxDao.selectByExample(example));
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
        int addNum = 1;
        int updateNum = 1;
        if (addList.size()>0){
            int batchSize = 4000;
            for(int i=0;i<100000;i+=batchSize){
                if(i+batchSize>=addList.size()){
                    addNum = commonZyfxDao.batchInsertZyfx(addList.subList(i,addList.size()));
                    break;
                }else {
                    addNum = commonZyfxDao.batchInsertZyfx(addList.subList(i, i+batchSize));
                }
            }
        }
        if (updateList.size()>0){
            int batchSize = 4000;
            for(int i=0;i<100000;i+=batchSize){
                if(i+batchSize>=updateList.size()){
                    updateNum = commonZyfxDao.batchUpdateZyfx(updateList.subList(i,updateList.size()));
                    break;
                }else {
                    updateNum = commonZyfxDao.batchUpdateZyfx(updateList.subList(i, i+batchSize));
                }
            }
        }
        return 1*addNum*updateNum;
    }


    @Override
    public void downloadAllZyfxExcel(Zyfx zyfx,HttpServletRequest request, HttpServletResponse response) throws IOException {

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
        example.createCriteria().andEqualTo("juZhan",zyfx.getJuZhan());
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
            DateFormat format = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
            //输出Excel文件1
            FileOutputStream output=new FileOutputStream("E:\\work\\excelUnicom\\"+zyfx.getJuZhan().substring(8)+format.format(new Date())+".xls");
            wb.write(output);//写入磁盘
            output.close();
            //写出excel文件
            wb.write(out);
            out.close();
        }
    }

    @Override
    public ZyfxTree selectZyfxTreeByJf(Zyfx zyfx) {
        ZyfxTree zyfxTree = new ZyfxTree();
        Example example = new Example(Zyfx.class);
        example.createCriteria().andEqualTo("juZhan",zyfx.getJuZhan()).andEqualTo("sheBeiMingCheng",zyfx.getSheBeiMingCheng());
        List<Zyfx> zyfxList = zyfxDao.selectByExample(example);
        zyfxTree.setSbmc(zyfx.getSheBeiMingCheng());
        zyfxTree.setJuZhan(zyfx.getJuZhan());
        List<MoKuai> moKuais = new ArrayList<>();
        // list 分组
        Map<String, List<Zyfx>> groupByMk = zyfxList.stream().collect(Collectors.groupingBy(Zyfx::getMoKuaiMing));
        for (String mk:groupByMk.keySet()
        ) {
            moKuais.add(new MoKuai(mk,groupByMk.get(mk)));
        }
        zyfxTree.setChildren(moKuais);
        return zyfxTree;
    }
    @Override
    public void downloadNotEndZyfx(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
        List<String> list = zyfxJhExcelTitle.getExcelTitle();
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
        List<Zyfx> notEndZyfxList = updateStatisticsZyfx();
        //先将报名表里所有活动id为eventId的记录的学生id和报名时间查出
        if (notEndZyfxList.size() > 0) {
            //将数据装填进表格中
            for (int i = 0; i < notEndZyfxList.size()+1; i++) {
                HSSFRow row = sheetlist.createRow(i);
                if (i == 0) {
                    for (int j = 0; j < list.size(); j++) {
                        HSSFCell cell = row.createCell(j);
                        cell.setCellValue(list.get(j));
                        cell.setCellStyle(cellStyle);
                    }
                } else {
                    row.createCell(0).setCellValue(notEndZyfxList.get(i-1).getDuanZiMingCheng());
                    row.createCell(1).setCellValue(notEndZyfxList.get(i-1).getZhuangTai());
                    row.createCell(2).setCellValue(notEndZyfxList.get(i-1).getZhuanYe());
                    row.createCell(3).setCellValue(notEndZyfxList.get(i-1).getYeWuLeiBie());
                    row.createCell(4).setCellValue(notEndZyfxList.get(i-1).getYeWuHao());
                    row.createCell(5).setCellValue(notEndZyfxList.get(i-1).getYeWuMing());
                    row.createCell(6).setCellValue(notEndZyfxList.get(i-1).getBiaoQian());
                    row.createCell(7).setCellValue(notEndZyfxList.get(i-1).getDuiYingWeizhi());
                    row.createCell(8).setCellValue(notEndZyfxList.get(i-1).getTiaoJieWeizhi());
                    row.createCell(10).setCellValue(notEndZyfxList.get(i-1).getJuZhan());
                    Date createTime = notEndZyfxList.get(i - 1).getBianGengRiQi();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
                    if(createTime != null){
                        String time = dateFormat.format(createTime);
                        row.createCell(9).setCellValue(time);
                    }else{
                        row.createCell(9).setCellValue("");
                    }
                }
            }
            DateFormat format = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
            //输出Excel文件1
            FileOutputStream output=new FileOutputStream("E:\\work\\excelUnicom2\\"+"资源计划"+format.format(new Date())+".xls");
            wb.write(output);//写入磁盘
            output.close();
            //写出excel文件
            wb.write(out);
            out.close();
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Zyfx> updateStatisticsZyfx(){
        Example example = new Example(Zyfx.class);
        example.createCriteria().andEqualTo("zhuangTai","在用");
        List<Zyfx> zaiYongZyfx = zyfxDao.selectByExample(example);
        List<StatisticsZyfx> statisticsZyfxes = new ArrayList<>();
        Map<String, List<Zyfx>> groupByJz = zaiYongZyfx.stream().collect(Collectors.groupingBy(Zyfx::getJuZhan));
        List<Zyfx> notEndZyfxList = new ArrayList<>();
        for (String jz:groupByJz.keySet()
        ) {
            Map<String, List<Zyfx>> groupByZy = groupByJz.get(jz).stream().collect(Collectors.groupingBy(Zyfx::getZhuanYe));
            StatisticsZyfx statisticsZyfx =new StatisticsZyfx();
            statisticsZyfx.setJuZhan(jz);
            statisticsZyfx.setDzNumZaiYong(groupByJz.get(jz).size());
            int unKonw = 0;
            for (String zy:groupByZy.keySet()){
                List<Zyfx> templist = groupByZy.get(zy);
                if (zy.contains("政企")){
                    List<Zyfx> notEndZyfxs = templist.stream().filter(z -> (z.getYeWuHao() + z.getYeWuMing()).equals("")).collect(Collectors.toList());
                    int notEndNum = notEndZyfxs.size();
                    statisticsZyfx.setZhengQiNotEnd(notEndNum);
                    statisticsZyfx.setZhengQiEnd(templist.size()-notEndNum);
                    notEndZyfxList.addAll(notEndZyfxs);
                }else if (zy.contains("无线")){
                    List<Zyfx> notEndZyfxs = templist.stream().filter(z -> (z.getYeWuMing() + z.getYeWuHao()).equals("")).collect(Collectors.toList());
                    int notEndNum = notEndZyfxs.size();
                    statisticsZyfx.setWuXianNotEnd(notEndNum);
                    statisticsZyfx.setWuXianEnd(templist.size()-notEndNum);
                    notEndZyfxList.addAll(notEndZyfxs);
                }else if (zy.contains("接入")) {
                    List<Zyfx> notEndZyfxs = templist.stream().filter(z -> (z.getYeWuMing()).equals("")).collect(Collectors.toList());
                    int notEndNum = notEndZyfxs.size();
                    statisticsZyfx.setJieRuNotEnd(notEndNum);
                    statisticsZyfx.setJieRuEnd(templist.size()-notEndNum);
                    notEndZyfxList.addAll(notEndZyfxs);
                }else if (zy.contains("动力")) {
                    List<Zyfx> notEndZyfxs = templist.stream().filter(z -> (z.getBiaoQian()).equals("")).collect(Collectors.toList());
                    int notEndNum = notEndZyfxs.size();
                    statisticsZyfx.setDongLiNotEnd(notEndNum);
                    statisticsZyfx.setDongLiEnd(templist.size()-notEndNum);
                    notEndZyfxList.addAll(notEndZyfxs);
                }else if (zy.equals("")){
                    notEndZyfxList.addAll(templist);
                    unKonw=templist.size();
                }else if (zy.contains("线路")) {
                    notEndZyfxList.addAll(templist);
                    statisticsZyfx.setXianLuNotEnd(templist.size());
                }
            }
            statisticsZyfx.setUnKonw(unKonw);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
            statisticsZyfx.setStatisticsDate(dateFormat.format(new Date()));
            statisticsZyfxes.add(statisticsZyfx);
        }
        List<StatisticsZyfx> addList = new ArrayList<>();
        List<StatisticsZyfx> updateList = new ArrayList<>();
        for (StatisticsZyfx s:statisticsZyfxes
             ) {
            Example example2 = new Example(StatisticsZyfx.class);
            example2.createCriteria().andEqualTo("juZhan",s.getJuZhan()).andEqualTo("statisticsDate",s.getStatisticsDate());
            if(commonStatisticsZyfxDao.selectCountByExample(example2)>0){
                updateList.add(s);
            }else {
                addList.add(s);
            }
        }
        if (addList.size()>0){
           commonStatisticsZyfxDao.batchInsertStatisticsZyfx(addList);
        }
        if (updateList.size()>0){
            commonStatisticsZyfxDao.batchUpdateStatisticsZyfx(updateList);
        }
        return notEndZyfxList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateZyfxByExcel2(MultipartFile file) throws IOException {
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
                Zyfx z = ExcelH_For_P.zyfxAddParams(headers,lo);
                addList.add(z);
            }
        }
        int addNum = 1;
        if (addList.size()>0){
            int batchSize = 4000;
            for(int i=0;i<100000;i+=batchSize){
                if(i+batchSize>=addList.size()){
                    addNum = commonZyfxDao.batchInsertZyfx2(addList.subList(i,addList.size()));
                    break;
                }else {
                    addNum = commonZyfxDao.batchInsertZyfx2(addList.subList(i, i+batchSize));
                }
            }
        }
        return 1*addNum;
    }

}
