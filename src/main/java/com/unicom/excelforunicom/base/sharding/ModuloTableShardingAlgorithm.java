package com.unicom.excelforunicom.base.sharding;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by wuwf on 17/4/19.
 */
@Component
public class ModuloTableShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<String> preciseShardingValue) {
        for (String each : tableNames) {
            String str = "";
            if (preciseShardingValue.getValue().equals("TJ天津市南开区西姜井局站")) {
                str = "xjj";
            } else if (preciseShardingValue.getValue().equals("TJ天津市南开区澄江路设备间")) {
                str = "cjl";
            } else if (preciseShardingValue.getValue().equals("TJ天津市南开区五金城设备间")){
                str = "wjc";
            }else if (preciseShardingValue.getValue().equals("TJ天津市南开区北草坝设备间")){
                str = "bcb";
            }else if (preciseShardingValue.getValue().equals("TJ天津市南开区鸿运小区设备间")){
                str = "hy";
            }else if (preciseShardingValue.getValue().equals("TJ天津市南开区大园新居设备间")){
                str = "dyxj";
            }else if (preciseShardingValue.getValue().equals("TJ天津市南开区美丽心殿设备间")){
                str = "mlxd";
            }
            if (each.endsWith(str)) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }
}