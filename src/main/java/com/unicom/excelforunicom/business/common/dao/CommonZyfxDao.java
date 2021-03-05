package com.unicom.excelforunicom.business.common.dao;

import com.unicom.excelforunicom.base.sharding.ModuloTableShardingAlgorithm;
import com.unicom.excelforunicom.base.sharding.TableShard;
import com.unicom.excelforunicom.business.common.model.Zyfx;
import com.unicom.excelforunicom.business.common.need.MyMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author JMY
* @date 2021/02/27
*/
@Repository
@TableShard(tableName = "zyfx", shardStrategy = ModuloTableShardingAlgorithm.class)
public interface CommonZyfxDao extends MyMapper<Zyfx> {
    @Insert("<script>" +
            "insert into zyfx(duanZiMingCheng,\n" +
            "guangLan,\n" +
            "xianXin,\n" +
            "zhuangTai,\n" +
            "dzmc,\n" +
            "zhuanYe,\n" +
            "yeWuLeiBie,\n" +
            "yeWuHao,\n" +
            "yeWuMing,\n" +
            "biaoQian,\n" +
            "duiYingWeizhi,\n" +
            "tiaoJieWeizhi,\n" +
            "guangJiaoWeizhi,\n" +
            "guangJiaoTiaojie,\n" +
            "peiXianGuangLanMingCheng,\n" +
            "sheBeiMingCheng,\n" +
            "moKuaiMing,\n" +
            "pan,\n" +
            "juZhan,\n" +
            "jiFang,\n" +
            "sheiBeiId,\n" +
            "duanZiId)" +
            "values" +
            "<foreach collection=\"list\" item=\"item\" separator=\",\">" +
            "(" +
            "#{item.duanZiMingCheng}," +
            "#{item.guangLan}," +
            "#{item.xianXin}," +
            "#{item.zhuangTai}," +
            "#{item.dzmc}," +
            "#{item.zhuanYe}," +
            "#{item.yeWuLeiBie}," +
            "#{item.yeWuHao}," +
            "#{item.yeWuMing}," +
            "#{item.biaoQian}," +
            "#{item.duiYingWeizhi}," +
            "#{item.tiaoJieWeizhi}," +
            "#{item.guangJiaoWeizhi}," +
            "#{item.guangJiaoTiaojie}," +
            "#{item.peiXianGuangLanMingCheng}," +
            "#{item.sheBeiMingCheng}," +
            "#{item.moKuaiMing}," +
            "#{item.pan}," +
            "#{item.juZhan}," +
            "#{item.jiFang}," +
            "#{item.sheiBeiId}," +
            "#{item.duanZiId})" +
            "</foreach>" +
            "</script>")
    int batchInsertZyfx(List<Zyfx> list);

    @Update("<script>"  +
            "<foreach item='item' collection=\"zlist\" index='index' open='' close='' separator=';'>"+
            "update zyfx "+
            "<set >"+
            "<if test=\" item.guangLan!=null and item.guangLan!=''\" >"+
            "guangLan = #{item.guangLan}," +
            "</if>"+
            "<if test=\"item.xianXin!=null and item.xianXin!=''\" >"+
            "xianXin = #{item.xianXin}," +
            "</if>"+
            "<if test=\"item.zhuangTai!=null and item.zhuangTai!=''\" >"+
            "zhuangTai = #{item.zhuangTai}," +
            "</if>"+
            "<if test=\"item.zhuanYe!=null and item.zhuanYe!=''\" >"+
            "zhuanYe = #{item.zhuanYe}," +
            "</if>"+
            "<if test=\"item.yeWuLeiBie!=null and item.yeWuLeiBie!=''\" >"+
            "yeWuLeiBie = #{item.yeWuLeiBie}," +
            "</if>"+
            "<if test=\"item.yeWuHao!=null and item.yeWuHao!=''\" >"+
            "yeWuHao = #{item.yeWuHao}," +
            "</if>"+
            "<if test=\"item.yeWuMing!=null and item.yeWuMing!=''\" >"+
            "yeWuMing = #{item.yeWuMing}," +
            "</if>"+"<if test=\"item.biaoQian!=null and item.biaoQian!=''\" >"+
            "biaoQian = #{item.biaoQian}," +
            "</if>"+
            "<if test=\"item.duiYingWeizhi!=null and item.duiYingWeizhi!=''\" >"+
            "duiYingWeizhi = #{item.duiYingWeizhi}," +
            "</if>"+
            "<if test=\" item.tiaoJieWeizhi!=null and item.tiaoJieWeizhi!=''\" >"+
            "tiaoJieWeizhi = #{item.tiaoJieWeizhi}," +
            "</if>"+
            "<if test=\" item.guangJiaoWeizhi!=null and item.guangJiaoWeizhi!=''\" >"+
            "guangJiaoWeizhi = #{item.guangJiaoWeizhi}," +
            "</if>"+
            "<if test=\" item.guangJiaoTiaojie!=null and item.guangJiaoTiaojie!=''\" >"+
            "guangJiaoTiaojie = #{item.guangJiaoTiaojie}," +
            "</if>"+
            "<if test=\" item.peiXianGuangLanMingCheng!=null and item.peiXianGuangLanMingCheng!=''\" >"+
            "peiXianGuangLanMingCheng = #{item.peiXianGuangLanMingCheng}," +
            "</if>"+
            "<if test=\" item.sheBeiMingCheng!=null and item.sheBeiMingCheng!=''\" >"+
            "sheBeiMingCheng = #{item.sheBeiMingCheng}," +
            "</if>"+
            "<if test=\" item.moKuaiMing!=null and item.moKuaiMing!=''\" >"+
            "moKuaiMing = #{item.moKuaiMing}," +
            "</if>"+
            "<if test=\" item.pan!=null and item.pan!=''\" >"+
            "pan = #{item.pan}," +
            "</if>"+
            "<if test=\" item.juZhan!=null and item.juZhan!=''\" >"+
            "juZhan = #{item.juZhan}," +
            "</if>"+
            "<if test=\" item.jiFang!=null and item.jiFang!=''\" >"+
            "jiFang = #{item.jiFang}," +
            "</if>"+
            "<if test=\" item.sheiBeiId!=null and item.sheiBeiId!=''\" >"+
            "sheiBeiId = #{item.sheiBeiId}," +
            "</if>"+
            "<if test=\" item.duanZiId!=null and item.duanZiId!=''\" >"+
            "duanZiId = #{item.duanZiId}," +
            "</if>"+
            "</set>"+
            "where duanZiMingCheng = #{item.duanZiMingCheng} " +
            "</foreach>" +
            "</script>")
    int batchUpdateZyfx(@Param("zlist")List<Zyfx> zlist);
}