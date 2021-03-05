package com.unicom.excelforunicom.business.common.need;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 * @author GaoLiWei
 * @date 2019/04/10
 */
public abstract class AbstractMyService<T> {

    @Autowired
    protected MyMapper<T> mapper;

    /**
     *  当前泛型真实类型的Class
     */
    private Class<T> modelClass;

    public AbstractMyService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public Integer insert(T model) {
        return mapper.insertSelective(model);
    }



    public Integer delete(T model) {
        return mapper.delete(model);
    }

    public Integer deleteById(Object id) {
        return mapper.deleteByPrimaryKey(id);
    }


    public Integer updateById(T model) {
        return mapper.updateByPrimaryKeySelective(model);
    }

    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }



    public List<T> selectAll() {
        return mapper.selectAll();
    }
}
