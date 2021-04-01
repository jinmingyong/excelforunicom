package com.unicom.excelforunicom.business.common.dao;

import com.unicom.excelforunicom.business.common.model.StatisticsZyfx;
import com.unicom.excelforunicom.business.common.model.Zyfx;
import com.unicom.excelforunicom.business.common.need.MyMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author JMY
* @date 2021/03/19
*/
@Repository
public interface CommonStatisticsZyfxDao extends MyMapper<StatisticsZyfx> {
    @Select("<script>" +
            "SELECT\n" +
            "statistics.juZhan,\n" +
            "statistics.dzNumZaiYong,\n" +
            "statistics.zhengQiEnd,\n" +
            "statistics.wuXianEnd,\n" +
            "statistics.jieRuEnd,\n" +
            "statistics.dongLiEnd,\n" +
            "statistics.zhengQiNotEnd,\n" +
            "statistics.wuXianNotEnd,\n" +
            "statistics.jieRuNotEnd,\n" +
            "statistics.dongLiNotEnd,\n" +
            "statistics.xianLuNotEnd,\n" +
            "statistics.unKonw,\n" +
            "statistics.statisticsDate\n" +
            "FROM\n" +
            "statistics\n" +
            "WHERE statisticsDate = '${date}' or statisticsDate = (select statisticsDate from statistics WHERE statisticsDate != '${date}'\n" +
            "                                      order by statisticsDate DESC limit 1)"+
            "</script>")
    List<StatisticsZyfx> selectStatisticsZyfx(@Param("date")String date);


    @Insert("<script>" +
            "insert into statistics("+
            "juZhan,\n" +
            "dzNumZaiYong,\n" +
            "zhengQiEnd,\n" +
            "wuXianEnd,\n" +
            "jieRuEnd,\n" +
            "dongLiEnd,\n" +
            "zhengQiNotEnd,\n" +
            "wuXianNotEnd,\n" +
            "jieRuNotEnd,\n" +
            "dongLiNotEnd,\n" +
            "xianLuNotEnd,\n" +
            "unKonw," +
            "statisticsDate)" +
            "values" +
            "<foreach collection=\"list\" item=\"item\" separator=\",\">" +
            "(" +
            "#{item.juZhan}," +
            "#{item.dzNumZaiYong}," +
            "#{item.zhengQiEnd}," +
            "#{item.wuXianEnd}," +
            "#{item.jieRuEnd}," +
            "#{item.dongLiEnd}," +
            "#{item.zhengQiNotEnd}," +
            "#{item.wuXianNotEnd}," +
            "#{item.jieRuNotEnd}," +
            "#{item.dongLiNotEnd}," +
            "#{item.xianLuNotEnd}," +
            "#{item.unKonw}," +
            "#{item.statisticsDate})" +
            "</foreach>" +
            "</script>")
    int batchInsertStatisticsZyfx(@Param("list")List<StatisticsZyfx> list);

    @Update("<script>"  +
            "<foreach item='item' collection=\"zlist\" index='index' open='' close='' separator=';'>"+
            "update statistics "+
            "<set >"+
            "<if test=\"item.dzNumZaiYong!=null\" >"+
            "dzNumZaiYong = #{item.dzNumZaiYong}," +
            "</if>"+
            "<if test=\"item.zhengQiEnd!=null\" >"+
            "zhengQiEnd = #{item.zhengQiEnd}," +
            "</if>"+
            "<if test=\"item.wuXianEnd!=null \" >"+
            "wuXianEnd = #{item.wuXianEnd}," +
            "</if>"+
            "<if test=\"item.jieRuEnd!=null \" >"+
            "jieRuEnd = #{item.jieRuEnd}," +
            "</if>"+
            "<if test=\"item.dongLiEnd!=null \" >"+
            "dongLiEnd = #{item.dongLiEnd}," +
            "</if>"+
            "<if test=\"item.zhengQiNotEnd!=null \" >"+
            "zhengQiNotEnd = #{item.zhengQiNotEnd}," +
            "</if>"+
            "<if test=\"item.wuXianNotEnd!=null \" >"+
            "wuXianNotEnd = #{item.wuXianNotEnd}," +
            "</if>"+
            "<if test=\" item.jieRuNotEnd!=null \" >"+
            "jieRuNotEnd = #{item.jieRuNotEnd}," +
            "</if>"+
            "<if test=\" item.dongLiNotEnd!=null \" >"+
            "dongLiNotEnd = #{item.dongLiNotEnd}," +
            "</if>"+
            "<if test=\" item.xianLuNotEnd!=null \" >"+
            "xianLuNotEnd = #{item.xianLuNotEnd}," +
            "</if>"+
            "<if test=\" item.unKonw!=null \" >"+
            "unKonw = #{item.unKonw}," +
            "</if>"+
            "</set>"+
            "where juZhan = #{item.juZhan} And statisticsDate = #{item.statisticsDate}" +
            "</foreach>" +
            "</script>")
    int batchUpdateStatisticsZyfx(@Param("zlist")List<StatisticsZyfx> zlist);
}
