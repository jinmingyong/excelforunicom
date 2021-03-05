package ${basePackage}.${commonPackage}.service.impl;

import ${basePackage}.${commonPackage}.dao.Common${tableNameUpperCamel}Dao;
import ${basePackage}.${commonPackage}.model.${tableNameUpperCamel};
import ${basePackage}.${commonPackage}.service.Common${tableNameUpperCamel}Service;
import ${basePackage}.${commonPackage}.need.AbstractMyService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
* @author ${author}
* @date ${date}
*/
@Service
public class Common${tableNameUpperCamel}ServiceImpl extends AbstractMyService<${tableNameUpperCamel}> implements Common${tableNameUpperCamel}Service {

    @Autowired
    private Common${tableNameUpperCamel}Dao common${tableNameUpperCamel}Dao;

    @Override
    public PageInfo selectAllForPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(common${tableNameUpperCamel}Dao.selectAll());
    }

}
