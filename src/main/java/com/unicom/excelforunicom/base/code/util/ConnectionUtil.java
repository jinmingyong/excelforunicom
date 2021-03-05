package com.unicom.excelforunicom.base.code.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GaoLiwei
 * @date 2019/4/18
 */
@Component
@Slf4j
public class ConnectionUtil {

    @Value("${spring.shardingsphere.datasource.do1.driverClassName}")
    private String driverClassName;

    @Value("${spring.shardingsphere.datasource.do1.url}")
    private String url;

    @Value("${spring.shardingsphere.datasource.do1.username}")
    private String username;

    @Value("${spring.shardingsphere.datasource.do1.password}")
    private String password;


    /**
     *  获得数据库中的表名
     */
    public List<String> getTableNameByCon () {
        List<String> tables = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            DatabaseMetaData meta = con.getMetaData();
            ResultSet rs = meta.getTables(null, null, null,
                    new String[] { "TABLE" });
            while (rs.next()) {
                tables.add(rs.getString(3));
            }
            con.close();
        } catch (Exception e) {
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return tables;
    }



    /**
     *  获得数据库中的表字段名
     */
    public Map<String,String> getColumnsNameByCon () {
        Map<String,String> columns= new HashMap<>();
        Connection con = null;
        try {
            con = getConnection();
            DatabaseMetaData meta = con.getMetaData();
            ResultSet rs = meta.getTables(null, null, null,
                    new String[] { "TABLE" });
            while (rs.next()) {
                ResultSet rsColumns=meta.getColumns(null,"%", rs.getString(3),"%");
                while (rsColumns.next()){
                columns.put(rs.getString(3),rsColumns.getString("COLUMN_NAME"));
                }
            }
            con.close();
        } catch (Exception e) {
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return columns;
    }
    /**
     * 通过jdbc 获取 connection
     * @return Connection
     */
    public Connection getConnection() {
        Connection con = null;
        try {
            //创建驱动器
            Class.forName(driverClassName);
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return con;
    }


}
