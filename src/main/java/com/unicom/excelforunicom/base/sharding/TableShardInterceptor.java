package com.unicom.excelforunicom.base.sharding;

import com.unicom.excelforunicom.business.common.model.Zyfx;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author jinmingyong
 * @date 2021/3/3 11:06
 */
@Component
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = { Connection.class, Integer.class }
        )
})
public class TableShardInterceptor implements Interceptor {

    private static final ReflectorFactory defaultReflectorFactory = new DefaultReflectorFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler,
                SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                defaultReflectorFactory
        );
        MappedStatement mappedStatement = (MappedStatement)
                metaObject.getValue("delegate.mappedStatement");
        if (mappedStatement.getSqlCommandType().name().equals("UPDATE")){
            String id = mappedStatement.getId();
            id = id.substring(0, id.lastIndexOf('.'));
            Class clazz = Class.forName(id);

            // 获取TableShard注解
            TableShard tableShard = (TableShard)clazz.getAnnotation(TableShard.class);
            if ( tableShard != null ) {
                String res = ((String)metaObject.getValue("delegate.boundSql.sql"));
                List<String> sqls = Arrays.asList(res.split(";"));
                Map a = (Map) statementHandler.getParameterHandler().getParameterObject();
                List<Zyfx> zyfxList= (List<Zyfx>) a.get("param1");
                String resSql = "";
                for (int i = 0; i < sqls.size(); i++) {
                    String str = "";
                    if (zyfxList.get(i).getJuZhan().equals("TJ天津市南开区西姜井局站")) {
                        str = "zyfx_xjj";
                        resSql += sqls.get(i).replace("zyfx",str)+";";
                    } else if (zyfxList.get(i).getJuZhan().equals("TJ天津市南开区澄江路设备间")) {
                        str = "zyfx_cjl";
                        resSql += sqls.get(i).replace("zyfx",str)+";";
                    } else if (zyfxList.get(i).getJuZhan().equals("TJ天津市南开区五金城设备间")){
                        str = "zyfx_wjc";
                        resSql += sqls.get(i).replace("zyfx",str)+";";
                    }else if (zyfxList.get(i).getJuZhan().equals("TJ天津市南开区北草坝设备间")){
                        str = "zyfx_bcb";
                        resSql += sqls.get(i).replace("zyfx",str)+";";
                    }else if (zyfxList.get(i).getJuZhan().equals("TJ天津市南开区鸿运小区设备间")){
                        str = "zyfx_hy";
                        resSql += sqls.get(i).replace("zyfx",str)+";";
                    }else if (zyfxList.get(i).getJuZhan().equals("TJ天津市南开区大园新居设备间")){
                        str = "zyfx_dyxj";
                        resSql += sqls.get(i).replace("zyfx",str)+";";
                    }else if (zyfxList.get(i).getJuZhan().equals("TJ天津市南开区美丽心殿设备间")){
                        str = "zyfx_mlxd";
                        resSql += sqls.get(i).replace("zyfx",str)+";";
                    }
                }
                // 用新sql代替旧sql, 完成所谓的sql rewrite
                metaObject.setValue("delegate.boundSql.sql", resSql);
            }
        }

        // 传递给下一个拦截器处理
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身, 减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
