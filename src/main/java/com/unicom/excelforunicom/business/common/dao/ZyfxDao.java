package com.unicom.excelforunicom.business.common.dao;

import com.unicom.excelforunicom.base.sharding.TableShard;
import com.unicom.excelforunicom.business.common.model.Zyfx;
import com.unicom.excelforunicom.business.common.model.ZyfxTree;
import com.unicom.excelforunicom.business.common.need.MyMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jinmingyong
 * @date 2021/3/5 15:10
 */
@Repository
@TableShard
public interface ZyfxDao extends MyMapper<Zyfx> {
    @Select("<script>"  +
            "SELECT\n" +
            "moKuaiMing\n" +
            "FROM\n" +
            "zyfx\n" +
            "WHERE\n" +
            "juZhan = #{juZhan} AND\n" +
            "sheBeiMingCheng = #{sheBeiMingCheng}\n" +
            "GROUP BY\n" +
            "moKuaiMing\n" +
            "</script>")
    List<String> selectZyfxMK(Zyfx zyfx);
}
