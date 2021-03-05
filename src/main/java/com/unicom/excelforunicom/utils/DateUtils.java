package com.unicom.excelforunicom.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: JiangSen
 * @create: 2019-12-24 10:40
 **/
public class DateUtils {

    /**
     * 判断输入是否是合格的日期形式
     * @param str
     * @return
     */
    public static boolean isNotDate(String str){
        boolean istrue = false;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证        日期，比如2007/02/29会被接受，
            //并转换成2007/03/01
            format.setLenient(false);
            Date date=format.parse(str);

        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            istrue = true;
        }
        return istrue;
    }
}
