package com.unicom.excelforunicom.base.code.service;

/**
 * 配置信息变量
 * @author GaoLiWei
 * @date 2019/04/18
 */
public class CodeGeneratorConfig {
	/**
	 * JDBC 相关配置信息
	 */
	protected static String JDBC_URL;
	protected static String JDBC_USERNAME;
	protected static String JDBC_PASSWORD;
	protected static String JDBC_DRIVER_CLASS_NAME;


	/**
	 * 项目在硬盘上的基础路径
	 */
	protected static String PROJECT_PATH = System.getProperty("user.dir");

	/**
	 * 要生成到的指定项目在硬盘上的基础路径
	 */
    protected static String TO_PROJECT_PATH;

	/**
	 * java文件路径
	 */
	protected static String JAVA_PATH;

	/**
	 * 资源文件路径
	 */
	protected static String RESOURCES_PATH;

	/**
	 * 模板存放位置
	 */
	protected static String TEMPLATE_FILE_PATH;

	/**
	 * 项目基础包
	 */
	protected static String BASE_PACKAGE;
	/**
	 * 项目基础包
	 */
	protected static String COMMON_BUSINESS_PACKAGE;

	/**
	 *
	 */
	protected static String RESULT_PACKAGE;
	/**
	 * 项目 Model 所在包
	 */
	protected static String MODEL_PACKAGE;

	/**
	 * 项目 Mapper 所在包
	 */
	protected static String MAPPER_PACKAGE;

	/**
	 * 项目 MyService 所在包
	 */
	protected static String SERVICE_PACKAGE;

	/**
	 * 项目 MyService 实现类所在包
	 */
	protected static String SERVICE_IMPL_PACKAGE;

	/**
	 * 项目 Controller 所在包
	 */
	protected static String CONTROLLER_PACKAGE;

	/**
	 * 生成的 MyService 存放路径
	 */
	protected static String PACKAGE_PATH_SERVICE;
	/**
	 * 生成的 MyService 实现存放路径
	 */
	protected static String PACKAGE_PATH_SERVICE_IMPL;
	/**
	 * 生成的 Controller 存放路径
	 */
	protected static String PACKAGE_PATH_CONTROLLER;
	/**
	 * 项目 DAO 存放路径
	 */
	protected static String PACKAGE_PATH_MAPPER;

	/**
	 * MyMapper 插件基础接口的完全限定名
	 */
	protected static String MAPPER_INTERFACE_REFERENCE;

	/**
	 * 通用 MyService 层 基础接口完全限定名
	 */
	protected static String SERVICE_INTERFACE_REFERENCE;

	/**
	 * 基于通用 MyBatis Mapper 插件的 MyService 接口的实现
	 */
	protected static String ABSTRACT_SERVICE_CLASS_REFERENCE;

	/**
	 * 模板注释中 @author
	 */
	protected static String AUTHOR;
	/**
	 * 模板注释中 @date
	 */
	protected static String DATE;

}
