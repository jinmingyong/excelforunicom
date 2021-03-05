package com.unicom.excelforunicom.base.code.service.impl;


import com.unicom.excelforunicom.base.code.service.CodeGenerator;
import com.unicom.excelforunicom.base.code.service.CodeGeneratorManager;
import com.unicom.excelforunicom.utils.StringUtils;
import freemarker.template.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *  DAO代码生成
 * @author GaoLiwei
 * @date 2019/4/11
 */
public class DaoGenerator extends CodeGeneratorManager implements CodeGenerator {


    @Override
    public void genCode(String tableName) {
        Configuration cfg = getFreemarkerConfiguration();
        String tableNameUpperCamel = tableNameConvertUpperCamel(tableName);

        Map<String, Object> data = getDataMapInit(tableNameUpperCamel);
        try {
            // 创建 DAO 接口
            File daoFile = new File(TO_PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_MAPPER
                    +"Common"+ tableNameUpperCamel + "Dao.java");
            // 查看父级目录是否存在, 不存在则创建
            if (!daoFile.getParentFile().exists()) {
                daoFile.getParentFile().mkdirs();
            }
            cfg.getTemplate("dao.ftl").process(data, new FileWriter(daoFile));
            System.out.println(tableNameUpperCamel + "Dao.java 生成成功!");
        } catch (Exception e) {
            throw new RuntimeException("Dao 生成失败!", e);
        }
    }

    /**
     * 预置页面所需数据
     * @param tableNameUpperCamel 首字为大写的实体类名
     * @return
     */
    private Map<String, Object> getDataMapInit(String tableNameUpperCamel) {
        Map<String, Object> data = new HashMap<String, Object>(6);
        data.put("date", DATE);
        data.put("author", AUTHOR);
        data.put("tableNameUpperCamel", tableNameUpperCamel);
        data.put("tableNameLowerCamel", StringUtils.toLowerCaseFirstOne(tableNameUpperCamel));
        data.put("commonPackage", COMMON_BUSINESS_PACKAGE);
        data.put("basePackage", BASE_PACKAGE);
        return data;
    }
}
