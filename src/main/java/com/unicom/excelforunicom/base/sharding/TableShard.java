package com.unicom.excelforunicom.base.sharding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jinmingyong
 * @date 2021/3/3 11:05
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableShard {

    // 要替换的表名
    String tableName();

    // 对应的分表策略类
    Class<? extends ModuloTableShardingAlgorithm> shardStrategy();

}
