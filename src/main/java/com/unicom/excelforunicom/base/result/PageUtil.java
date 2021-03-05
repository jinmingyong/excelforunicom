package com.unicom.excelforunicom.base.result;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 *
 * @author GaoLiWei
 * @date 2018/7/26
 */
public class PageUtil {

    public static PageInfo pageResultInfo(List<?> list) {
        return new PageInfo<>(list);
    }

    public static <T> PageVO<T> pageResultVO(List<T> list) {
        return new PageVO<>(list);
    }

}
