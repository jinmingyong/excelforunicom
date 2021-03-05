package com.unicom.excelforunicom.business.common.dao;

import com.unicom.excelforunicom.business.common.model.Guangxian;
import com.unicom.excelforunicom.business.common.need.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author JMY
* @date 2021/02/23
*/
@Repository
public interface CommonGuangxianDao extends MyMapper<Guangxian> {
    @Select("SELECT\n" +
            "guangxian.id,\n" +
            "guangxian.guangLan,\n" +
            "guangxian.xianXinCount,\n" +
            "guangxian.xianXinFirst,\n" +
            "guangxian.xianXinLast,\n" +
            "guangxian.ODF_Name,\n" +
            "guangxian.pan,\n" +
            "guangxian.panFirst,\n" +
            "guangxian.panLast,\n" +
            "guangxian.ruJiaoXinxi,\n" +
            "guangxian.ru_Mian,\n" +
            "guangxian.ru_Pan,\n" +
            "guangxian.ru_PanFirst,\n" +
            "guangxian.ru_PanLast\n" +
            "FROM\n" +
            "guangxian\n" +
            "WHERE\n" +
            "guangxian.ODF_Name = #{ODF_Name} AND\n" +
            "guangxian.pan = #{pan}\n" +
            "ORDER BY\n" +
            "guangxian.id ASC")
    List<Guangxian> selectGuangXianByODF_NameAndPan(@Param("ODF_Name") String ODF_Name,@Param("pan") String pan);
    @Select("SELECT\n" +
            "guangxian.id,\n" +
            "guangxian.guangLan,\n" +
            "guangxian.xianXinCount,\n" +
            "guangxian.xianXinFirst,\n" +
            "guangxian.xianXinLast,\n" +
            "guangxian.ODF_Name,\n" +
            "guangxian.pan,\n" +
            "guangxian.panFirst,\n" +
            "guangxian.panLast,\n" +
            "guangxian.ruJiaoXinxi,\n" +
            "guangxian.ru_Mian,\n" +
            "guangxian.ru_Pan,\n" +
            "guangxian.ru_PanFirst,\n" +
            "guangxian.ru_PanLast\n" +
            "FROM\n" +
            "guangxian\n" +
            "ORDER BY\n" +
            "guangxian.id ASC\n" +
            "LIMIT ${firstNum}, ${num}\n")
    List<Guangxian> selectByPage(@Param("firstNum") Integer firstNum,@Param("num") Integer num);
}
