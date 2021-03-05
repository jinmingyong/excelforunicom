package ${basePackage}.${commonPackage}.dao;

import ${basePackage}.${commonPackage}.model.${tableNameUpperCamel};
import ${basePackage}.${commonPackage}.need.MyMapper;
import org.springframework.stereotype.Repository;

/**
* @author ${author}
* @date ${date}
*/
@Repository
public interface Common${tableNameUpperCamel}Dao extends MyMapper<${tableNameUpperCamel}> {

}
