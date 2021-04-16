package com.lyl.controller;

import com.lyl.entity.DeptBean;
import com.lyl.service.DeptService;
import com.lyl.utils.Page;
import com.lyl.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-08 10:59
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    @RequestMapping("/getDeptListConn")
    public Page<DeptBean> getDeptListConn(@RequestBody DeptBean deptBean,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize){
        return deptService.getDeptListConn(deptBean,pageNum,pageSize);
    }

    @RequestMapping("/getDeptByDeptid")
    public DeptBean getDeptByDeptid(Long deptid){
        return deptService.getDeptByDeptid(deptid);
    }

    /**
     * 保存部门分配职位
     */
    @RequestMapping("/saveDeptPost")
    public ResultInfo saveDeptPost(Long deptid, @RequestBody Long[] postids){
        try {
            deptService.saveDeptPost(deptid,postids);
            return new ResultInfo(true, "编辑成功");
        }catch (Exception e){
            return  new ResultInfo(false, "编辑失败");
        }
    }
}
