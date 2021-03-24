package com.unicom.excelforunicom.business.common.controller;

import com.unicom.excelforunicom.business.common.model.StatisticsZyfx;
import com.unicom.excelforunicom.business.common.service.CommonStatisticsZyfxService;
import com.unicom.excelforunicom.utils.PageUtils;
import com.unicom.excelforunicom.base.result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.pagehelper.PageInfo;


/**
* @author JMY
* @date 2021/03/19
*/
@Api(tags = "StatisticsZyfx")
@RestController
@RequestMapping("/commonStatisticsZyfxController/")
public class CommonStatisticsZyfxController {

    @Autowired
    CommonStatisticsZyfxService commonStatisticsZyfxService;


    @ApiOperation(value = "新增")
    @PostMapping("insert")
    public Result insert(@RequestBody StatisticsZyfx statisticszyfx) {
        return Result.result(commonStatisticsZyfxService.insert(statisticszyfx),"新增成功","新增失败");
    }

    @ApiOperation(value = "根据实体中的属性删除")
    @DeleteMapping("delete")
    public Result delete(@RequestBody StatisticsZyfx statisticszyfx) {
        return Result.result(commonStatisticsZyfxService.delete(statisticszyfx),"删除成功","删除失败");
    }

    @ApiOperation(value = "根据主键删除")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable String id) {
        return Result.result(commonStatisticsZyfxService.deleteById(id),"删除成功","删除失败");
    }

    @ApiOperation(value = "根据主键更新实体中存在的值")
    @PutMapping("updateById")
    public Result updateById(@RequestBody StatisticsZyfx statisticszyfx) {
        return Result.result(commonStatisticsZyfxService.updateById(statisticszyfx),"更新成功","更新失败");
    }

    @ApiOperation(value = "根据主键查找")
    @GetMapping("selectById/{id}")
    public Result selectById(@PathVariable String id) {
        return Result.result(commonStatisticsZyfxService.selectById(id));
    }

    @ApiOperation(value = "查询所有")
    @GetMapping("selectAll")
    public Result selectAll() {
        return Result.result(commonStatisticsZyfxService.selectAll());
    }

    @ApiOperation(value = "分页查询所有")
    @GetMapping("selectAllForPage")
    public Result selectAllForPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageUtils pageUtil = new PageUtils();
        pageUtil.setDataList(commonStatisticsZyfxService.selectAll());
        pageUtil.setCurrentPage(pageNum);
        pageUtil.setPageSizes(pageSize);
        return Result.result(pageUtil.paging());
    }

    @ApiOperation(value = "统计在用")
    @GetMapping("selectStatisticsZyfx")
    public Result selectStatisticsZyfx() {
        return Result.result(commonStatisticsZyfxService.selectStatisticsZyfx());
    }

    @ApiOperation(value = "两天比较")
    @GetMapping("selectStatisticsZyfxTwoDay")
    public Result selectStatisticsZyfxTwoDay() {
        return Result.result(commonStatisticsZyfxService.selectStatisticsZyfxTwoDay());
    }
}
