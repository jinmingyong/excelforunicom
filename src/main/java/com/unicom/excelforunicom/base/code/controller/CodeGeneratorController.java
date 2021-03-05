package com.unicom.excelforunicom.base.code.controller;
;
import com.unicom.excelforunicom.base.code.service.CodeGeneratorManager;
import com.unicom.excelforunicom.base.code.util.ConnectionUtil;
import com.unicom.excelforunicom.base.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author GaoLiwei
 * @date 2019/4/18
 */
@RestController
@RequestMapping("/codeGenerator/")
public class CodeGeneratorController {

    @Autowired
    private CodeGeneratorManager codeGeneratorManager;
    @Autowired
    private ConnectionUtil connectionUtil;




    @GetMapping("getTables")
    public Result getTables(){
        List<String> tableNameByCon = connectionUtil.getTableNameByCon();
        return Result.result(tableNameByCon);
    }

    @PostMapping("codeGenerators")
    public String codeGenerators(@RequestBody String[] tables){
        codeGeneratorManager.genCodeWithSimpleName(tables);
        return "生成成功";
    }

/*    @GetMapping("columns")
    public Result getColumnsName(){
        Map<String, String> tableColumnsByCon = connectionUtil.getColumnsNameByCon();
        return Result.result(tableColumnsByCon);
    }*/

}
