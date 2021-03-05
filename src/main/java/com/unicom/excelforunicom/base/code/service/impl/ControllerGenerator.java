package com.unicom.excelforunicom.base.code.service.impl;


import com.google.common.base.CaseFormat;
import com.unicom.excelforunicom.base.code.service.CodeGenerator;
import com.unicom.excelforunicom.base.code.service.CodeGeneratorManager;
import com.unicom.excelforunicom.utils.StringUtils;
import freemarker.template.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller层 代码生成器
 *
 * @author GaoLiWei
 * @date 2017/09/20
 */
public class ControllerGenerator extends CodeGeneratorManager implements CodeGenerator {


	@Override
	public void genCode(String tableName) {
		Configuration cfg = getFreemarkerConfiguration();

		String tableNameUpperCamel = tableNameConvertUpperCamel(tableName);

		Map<String, Object> data = getDataMapInit(tableNameUpperCamel);
		try {
			File controllerFile = new File(TO_PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER
						 +"Common"+ tableNameUpperCamel + "Controller.java");
	        if (!controllerFile.getParentFile().exists()) {
	        	controllerFile.getParentFile().mkdirs();
	        }
	        cfg.getTemplate("controller.ftl").process(data, new FileWriter(controllerFile));
			System.out.println(tableNameUpperCamel + "Controller.java 生成成功!");
		} catch (Exception e) {
			throw new RuntimeException("Controller 生成失败!", e);
		}
	}

	/**
	 * 预置页面所需数据
	 * @param tableNameUpperCamel 首字为大写的实体类名
	 * @return
	 */
	private Map<String, Object> getDataMapInit(String tableNameUpperCamel) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("date", DATE);
        data.put("author", AUTHOR);
        data.put("baseRequestMapping", StringUtils.toLowerCaseFirstOne(tableNameUpperCamel));
        data.put("tableNameUpperCamel", tableNameUpperCamel);
        data.put("tableNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, tableNameUpperCamel));
        data.put("basePackage", BASE_PACKAGE);
        data.put("commonPackage", COMMON_BUSINESS_PACKAGE);
        data.put("resultPackage", RESULT_PACKAGE);

		return data;
	}
}
