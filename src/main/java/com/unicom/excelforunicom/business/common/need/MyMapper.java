package com.unicom.excelforunicom.business.common.need;


import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * 通用 Mapper, 如果被扫描到会报异常
 *
 * @author GaoLiWei
 * @date 2019/04/10
 */
@Component
public interface MyMapper<T> extends Mapper<T> {
}
