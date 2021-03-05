package ${basePackage}.${commonPackage}.controller;

import ${basePackage}.${commonPackage}.model.${tableNameUpperCamel};
import ${basePackage}.${commonPackage}.service.Common${tableNameUpperCamel}Service;
import ${basePackage}.utils.PageUtils;
import ${resultPackage}.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.pagehelper.PageInfo;


/**
* @author ${author}
* @date ${date}
*/
@Api(tags = "${tableNameUpperCamel}")
@RestController
@RequestMapping("/common${tableNameUpperCamel}Controller/")
public class Common${tableNameUpperCamel}Controller {

    @Autowired
    Common${tableNameUpperCamel}Service common${tableNameUpperCamel}Service;


    @ApiOperation(value = "新增")
    @PostMapping("insert")
    public Result insert(@RequestBody ${tableNameUpperCamel} ${tableNameLowerCamel}) {
        return Result.result(common${tableNameUpperCamel}Service.insert(${tableNameLowerCamel}),"新增成功","新增失败");
    }

    @ApiOperation(value = "根据实体中的属性删除")
    @DeleteMapping("delete")
    public Result delete(@RequestBody ${tableNameUpperCamel} ${tableNameLowerCamel}) {
        return Result.result(common${tableNameUpperCamel}Service.delete(${tableNameLowerCamel}),"删除成功","删除失败");
    }

    @ApiOperation(value = "根据主键删除")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable String id) {
        return Result.result(common${tableNameUpperCamel}Service.deleteById(id),"删除成功","删除失败");
    }

    @ApiOperation(value = "根据主键更新实体中存在的值")
    @PutMapping("updateById")
    public Result updateById(@RequestBody ${tableNameUpperCamel} ${tableNameLowerCamel}) {
        return Result.result(common${tableNameUpperCamel}Service.updateById(${tableNameLowerCamel}),"更新成功","更新失败");
    }

    @ApiOperation(value = "根据主键查找")
    @GetMapping("selectById/{id}")
    public Result selectById(@PathVariable String id) {
        return Result.result(common${tableNameUpperCamel}Service.selectById(id));
    }

    @ApiOperation(value = "查询所有")
    @GetMapping("selectAll")
    public Result selectAll() {
        return Result.result(common${tableNameUpperCamel}Service.selectAll());
    }

    @ApiOperation(value = "分页查询所有")
    @GetMapping("selectAllForPage")
    public Result selectAllForPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageUtils pageUtil = new PageUtils();
        pageUtil.setDataList(common${tableNameUpperCamel}Service.selectAll());
        pageUtil.setCurrentPage(pageNum);
        pageUtil.setPageSizes(pageSize);
        return Result.result(pageUtil.paging());
    }
}
