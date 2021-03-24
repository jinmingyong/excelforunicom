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
            switch (preciseShardingValue.getValue()) {
                case "TJ天津市南开区西姜井局站":
                    str = "xjj";
                    break;
                case "TJ天津市南开区澄江路设备间":
                    str = "cjl";
                    break;
                case "TJ天津市南开区五金城设备间":
                    str = "wjc";
                    break;
                case "TJ天津市南开区北草坝设备间":
                    str = "bcb";
                    break;
                case "TJ天津市南开区鸿运小区设备间":
                    str = "hy";
                    break;
                case "TJ天津市南开区大园新居设备间":
                    str = "dyxj";
                    break;
                case "TJ天津市南开区美丽心殿设备间":
                    str = "mlxd";
                    break;
                case "TJ天津市南开区西营门局站":
                    str = "xym";
                    break;
                case "TJ天津市南开区王顶堤局站":
                    str = "wdd";
                    break;
                case "TJ天津市南开区南丰桥局站":
                    str = "nfq";
                    break;
            }
            if (each.endsWith(str)) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }
}