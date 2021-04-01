package com.unicom.excelforunicom.business.common.controller;

import com.unicom.excelforunicom.business.common.model.Zyfx;
import com.unicom.excelforunicom.business.common.service.CommonZyfxService;
import com.unicom.excelforunicom.utils.PageUtils;
import com.unicom.excelforunicom.base.result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
* @author JMY
* @date 2021/02/27
*/
@Api(tags = "Zyfx")
@RestController
@RequestMapping("/commonZyfxController/")
public class CommonZyfxController {

    @Autowired
    CommonZyfxService commonZyfxService;


    @ApiOperation(value = "新增")
    @PostMapping("insert")
    public Result insert(@RequestBody Zyfx zyfx) {
        return Result.result(commonZyfxService.insert(zyfx),"新增成功","新增失败");
    }

    @ApiOperation(value = "根据实体中的属性删除")
    @DeleteMapping("delete")
    public Result delete(@RequestBody Zyfx zyfx) {
        return Result.result(commonZyfxService.delete(zyfx),"删除成功","删除失败");
    }

    @ApiOperation(value = "根据主键删除")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable String id) {
        return Result.result(commonZyfxService.deleteById(id),"删除成功","删除失败");
    }

    @ApiOperation(value = "根据主键更新实体中存在的值")
    @PutMapping("updateById")
    public Result updateById(@RequestBody Zyfx zyfx) {
        return Result.result(commonZyfxService.updateById(zyfx),"更新成功","更新失败");
    }

    @ApiOperation(value = "根据主键查找")
    @GetMapping("selectById/{id}")
    public Result selectById(@PathVariable String id) {
        return Result.result(commonZyfxService.selectById(id));
    }

    @ApiOperation(value = "查询所有")
    @GetMapping("selectAll")
    public Result selectAll() {
        return Result.result(commonZyfxService.selectAll());
    }

    @ApiOperation(value = "分页查询所有")
    @GetMapping("selectAllForPage")
    public Result selectAllForPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam(required = false) String zName) {
        PageUtils pageUtil = new PageUtils();
        PageInfo<Zyfx> pageInfo =commonZyfxService.selectAllZyfx(zName,pageNum,pageSize);
        pageUtil.setDataList(pageInfo.getList());
        pageUtil.setCurrentPage(pageNum);
        pageUtil.setPageSizes(pageSize);
        pageUtil.setTotal((int) pageInfo.getTotal());
        pageUtil.setTotalPage(pageInfo.getPages());
        return Result.result(pageUtil);
    }
    @ApiOperation(value = "分页查询根据局站")
    @GetMapping("selectZyfxByJuZhan")
    public Result selectZyfxByJuZhan(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam String juZhan) {
        PageUtils pageUtil = new PageUtils();
        PageInfo<Zyfx> pageInfo =commonZyfxService.selectZyfxByJuZhan(juZhan,pageNum,pageSize);
        pageUtil.setDataList(pageInfo.getList());
        pageUtil.setCurrentPage(pageNum);
        pageUtil.setPageSizes(pageSize);
        pageUtil.setTotal((int) pageInfo.getTotal());
        pageUtil.setTotalPage(pageInfo.getPages());
        return Result.result(pageUtil);
    }

    @ApiOperation(value = "excel更新")
    @PostMapping("updateZyfxByExcel")
    public Result updateZyfxByExcel(@RequestParam("file") MultipartFile file) throws Exception {
        return Result.result(commonZyfxService.updateZyfxByExcel(file),"更新成功","更新失败");
    }

    @ApiOperation(value = "下载报表")
    @PostMapping("downloadAllZyfxExcel")
    public void downloadAllZyfxExcel(@RequestBody Zyfx zyfx, HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonZyfxService.downloadAllZyfxExcel(zyfx,request,response);
    }
    @ApiOperation(value = "下载未完成报表")
    @PostMapping("downloadNotEndZyfx")
    public void downloadNotEndZyfx(HttpServletRequest request, HttpServletResponse response) throws Exception {
        commonZyfxService.downloadNotEndZyfx(request,response);
    }
    @ApiOperation(value = "树")
    @PostMapping("selectZyfxTreeByJf")
    public Result selectZyfxTreeByJf(@RequestBody Zyfx zyfx) {
        return Result.result(commonZyfxService.selectZyfxTreeByJf(zyfx));
    }

    @ApiOperation(value = "统计在用")
    @GetMapping("updateStatisticsZyfx")
    public Result updateStatisticsZyfx() {
        return Result.result(commonZyfxService.updateStatisticsZyfx());
    }

    @ApiOperation(value = "excel更新2")
    @PostMapping("updateZyfxByExcel2")
    public Result updateZyfxByExcel2(@RequestParam("file") MultipartFile file) throws Exception {
        return Result.result(commonZyfxService.updateZyfxByExcel2(file),"更新成功","更新失败");
    }


}
