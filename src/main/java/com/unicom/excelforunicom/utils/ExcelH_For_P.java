package com.unicom.excelforunicom.utils;

import com.unicom.excelforunicom.business.common.model.Zyfx;
import redis.clients.jedis.params.ZAddParams;

import java.util.Arrays;
import java.util.List;

/**
 * @author jinmingyong
 * @date 2021/3/1 10:44
 */
public class ExcelH_For_P {
    public static Zyfx zyfxAddParams(List<Object> headers, List<Object> params){
        Zyfx zyfx =new Zyfx();
        for (int i = 0; i < headers.size(); i++) {
            if ("端子名称".equals(headers.get(i))) {
                zyfx.setDuanZiMingCheng(dzmcFm((String) params.get(i)));
            } else if ("光缆".equals(headers.get(i))) {
                zyfx.setGuangLan((String) params.get(i));
            } else if ("纤芯".equals(headers.get(i))) {
                zyfx.setXianXin((String) params.get(i));
            } else if ("端子业务状态".equals(headers.get(i))) {
                zyfx.setZhuangTai((String) params.get(i));
            } else if ("端子名称1".equals(headers.get(i))) {
                zyfx.setDzmc((String) params.get(i));
            } else if ("专业类别".equals(headers.get(i))) {
                zyfx.setZhuanYe((String) params.get(i));
            } else if ("业务类别".equals(headers.get(i))) {
                zyfx.setYeWuLeiBie((String) params.get(i));
            } else if ("业务号码".equals(headers.get(i))) {
                zyfx.setYeWuHao((String) params.get(i));
            } else if ("业务名称".equals(headers.get(i))) {
                zyfx.setYeWuMing((String) params.get(i));
            } else if ("现场标签名称".equals(headers.get(i))) {
                zyfx.setBiaoQian((String) params.get(i));
            } else if ("对应位置".equals(headers.get(i))) {
                zyfx.setDuiYingWeizhi((String) params.get(i));
            } else if ("设备间内跳接位置".equals(headers.get(i))) {
                zyfx.setTiaoJieWeizhi(dzmcFm((String) params.get(i)));
            } else if ("光交位置".equals(headers.get(i))) {
                zyfx.setGuangJiaoWeizhi((String) params.get(i));
            } else if ("光交跳接".equals(headers.get(i))) {
                zyfx.setGuangJiaoTiaojie((String) params.get(i));
            } else if ("配线光缆名称".equals(headers.get(i))) {
                zyfx.setPeiXianGuangLanMingCheng((String) params.get(i));
            } else if ("变更日期".equals(headers.get(i))) {
                zyfx.setBianGengRiQi(null);
            } else if ("设备名称".equals(headers.get(i))) {
                zyfx.setSheBeiMingCheng((String) params.get(i));
            } else if ("模块名称".equals(headers.get(i))) {
                zyfx.setMoKuaiMing((String) params.get(i));
            } else if ("盘".equals(headers.get(i))) {
                zyfx.setPan((String) params.get(i));
            } else if ("局站".equals(headers.get(i))) {
                zyfx.setJuZhan((String) params.get(i));
            } else if ("机房".equals(headers.get(i))) {
                zyfx.setJiFang((String) params.get(i));
            } else if ("设备ID".equals(headers.get(i))) {
                zyfx.setSheiBeiId((String) params.get(i));
            } else if ("端子ID".equals(headers.get(i))) {
                zyfx.setDuanZiId((String) params.get(i));
            }
        }
        return zyfx;
    }
    public static String dzmcFm(String dzmc){
        if (dzmc.matches("^[0-9]{1,}-[0-9]{1,}-M[0-9]{1,}-[0-9]{1,}-[0-9]{1,}$")){
            if (!dzmc.matches("^[0-9]{2}-[0-9]{2}-M[0-9]{2}-[0-9]{2}-[0-9]{2}$")){
                String res = "";
                List<String> d = Arrays.asList(dzmc.split("-"));
                for (String s:d
                ) {
                    String r="";
                    if (s.indexOf("M")>=0){
                        s = s.substring(1);
                        r="M"+StringUtils.add0(s);
                        res+=r+"-";
                        continue;
                    }
                    r=StringUtils.add0(s);
                    res+=r+"-";
                }
                return res.substring(0,15);
            }
            }
        return dzmc;
    }
}
