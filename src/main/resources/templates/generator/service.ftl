package ${basePackage}.${commonPackage}.service;

import ${basePackage}.${commonPackage}.model.${tableNameUpperCamel};
import ${basePackage}.${commonPackage}.need.MyService;
import com.github.pagehelper.PageInfo;


/**
* @author ${author}
* @date ${date}
*/
public interface Common${tableNameUpperCamel}Service extends MyService<${tableNameUpperCamel}> {

    /**
    *  分页查询
    * @param pageNum
    * @param pageSize
    * @return PageInfo
    */
    PageInfo selectAllForPage(Integer pageNum, Integer pageSize);

}
