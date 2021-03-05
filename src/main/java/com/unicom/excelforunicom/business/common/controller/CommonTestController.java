package com.unicom.excelforunicom.business.common.controller;

import com.unicom.excelforunicom.business.common.model.Test;
import com.unicom.excelforunicom.business.common.service.CommonTestService;
import com.unicom.excelforunicom.utils.PageUtils;
import com.unicom.excelforunicom.base.result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.pagehelper.PageInfo;


/**
* @author JMY
* @date 2020/08/15
*/
@Api(tags = "Test")
@RestController
@RequestMapping("/commonTestController/")
public class CommonTestController {

    @Autowired
    CommonTestService commonTestService;


    @ApiOperation(value = "新增")
    @PostMapping("insert")
    public Result insert(@RequestBody Test test) {
        return Result.result(commonTestService.insert(test),"新增成功","新增失败");
    }

    @ApiOperation(value = "根据实体中的属性删除")
    @DeleteMapping("delete")
    public Result delete(@RequestBody Test test) {
        return Result.result(commonTestService.delete(test),"删除成功","删除失败");
    }

    @ApiOperation(value = "根据主键删除")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable String id) {
        return Result.result(commonTestService.deleteById(id),"删除成功","删除失败");
    }

    @ApiOperation(value = "根据主键更新实体中存在的值")
    @PutMapping("updateById")
    public Result updateById(@RequestBody Test test) {
        return Result.result(commonTestService.updateById(test),"更新成功","更新失败");
    }

    @ApiOperation(value = "根据主键查找")
    @GetMapping("selectById/{id}")
    public Result selectById(@PathVariable String id) {
        return Result.result(commonTestService.selectById(id));
    }

    @ApiOperation(value = "查询所有")
    @GetMapping("selectAll")
    public Result selectAll() {
        return Result.result(commonTestService.selectAll());
    }

    @ApiOperation(value = "分页查询所有")
    @GetMapping("selectAllForPage")
    public Result selectAllForPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageUtils pageUtil = new PageUtils();
        pageUtil.setDataList(commonTestService.selectAll());
        pageUtil.setCurrentPage(pageNum);
        pageUtil.setPageSizes(pageSize);
        return Result.result(pageUtil.paging());
    }
}
