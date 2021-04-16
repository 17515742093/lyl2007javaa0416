package com.lyl.controller;

import com.lyl.entity.MeunBean;
import com.lyl.entity.UserBean;
import com.lyl.service.UserService;
import com.lyl.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-07 11:36
 */
@RestController
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserService userService;

    /**
     * 去给用户选择部门
     */
    @RequestMapping("/getUserVoById")
    public UserBean getUserVoById(Long id){
        return userService.getUserVoById(id);
    }

    /**
     * 用户登录
     */
    @RequestMapping("/getLogin")
    public ResultInfo getLogin(@RequestBody UserBean ub, HttpServletRequest request){
        UserBean userBean = userService.getLogin(ub);
        if(userBean==null){
            return new ResultInfo(false, "登录失败,用户名或者密码错误");
        }else{
            request.getSession().setAttribute("ub", userBean);
            return new ResultInfo(true, "登录成功");
        }
    }

    @RequestMapping("/getMeunList")
    public List<MeunBean> getMeunList(HttpServletRequest request){
        /**
         * 在这里需要知道，是谁登录的
         * 还要查询出不是按钮的菜单
         */
        UserBean ub =(UserBean) request.getSession().getAttribute("ub");
        /**
         * 查询这个用户有哪些url
         */
        Set<String> urls =  userService.getUserMeunUrlById(ub);
        request.getSession().setAttribute("urls",urls);


        return userService.getMeunList(ub);
    }


    @RequestMapping("/getUserList")
    public List<UserBean> getUserList(){
        return userService.getUserList();
    }

    @RequestMapping("/saveUserDept")
    public ResultInfo saveUserDept(Long id,@RequestBody Long[] deptids){
        try {
            userService.saveUserDept(id,deptids);
            return new ResultInfo(true,"报错成功");
        }catch (Exception e){
            return new ResultInfo(false,"报错失败");
        }
    }

    /**
     * 去给用户分配职位
     */
    @RequestMapping("/getUserInfo")
    public UserBean getUserInfo(Long id){
        return userService.getUserInfo(id);
    }
    /**
     * 保存分配职位
     */
    @RequestMapping("/saveUserPost")
    public ResultInfo saveUserPost(@RequestBody UserBean userBean){
        try {
            userService.saveUserPost(userBean);
            return new ResultInfo(true, "编辑成功");
        }catch (Exception e){
            return new ResultInfo(false, "编辑失败");
        }
    }


}
