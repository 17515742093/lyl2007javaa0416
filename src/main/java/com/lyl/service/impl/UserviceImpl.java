package com.lyl.service.impl;

import com.lyl.entity.*;
import com.lyl.mapper.DeptMapper;
import com.lyl.mapper.MeunMapper;
import com.lyl.mapper.UserMapper;
import com.lyl.service.UserService;
import com.lyl.utils.MD5key;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-07 11:38
 */
@Service
public class UserviceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MeunMapper meunMapper;
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<UserBean> getUserList() {
        return userMapper.selectByExample(null);
    }

    @Override
    public List<MeunBean> getMeunList(UserBean ub) {
        if(ub!=null){
            List<MeunBean> list = userMapper.getUserMeunListById(ub.getId());
            return list;
        }
        return null;
    }
    public List<MeunBean> getMeunList2(UserBean ub) {
        if(ub!=null){
            //菜单先展示不是按钮的菜单
            MeunBeanExample example = new MeunBeanExample();
            MeunBeanExample.Criteria criteria = example.createCriteria();
            criteria.andIsbuttonEqualTo(0);
            List<MeunBean> list = meunMapper.selectByExample(example);
            return list;
        }
        return null;
    }
    @Override
    public UserBean getUserVoById(Long id) {

        UserBean userBean = userMapper.selectByPrimaryKey(id);
        /**
         * 去查询这个用户有那些部门，咱们的设计是一对多，所以页面待会儿要不用复选框接收，
         * 要是不用下拉框接收，但是下拉框要支持多选
         */
        Long[] deptids = userMapper.getUserDeptidsById(id);
        System.out.println(deptids);
        userBean.setDeptids(deptids);
        List<DeptBean> deptBeanList = deptMapper.selectByExample(null);
        userBean.setDlist(deptBeanList);
        return userBean;
    }
    @Override
   public void saveUserDept(Long id, Long[] deptids) {
        userMapper.deleteUserDeptByUserId(id);
        userMapper.deleteUserPostByUserId(id);
       if(deptids!=null&&deptids.length>=1){
           for (Long deptid : deptids){
              userMapper.insertUserDept(id,deptid);
           }
       }
    }
    @Override
    public UserBean getUserInfo(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        /**
         * 开始查询这个用户有没有部门
         */
        List<DeptBean> dlist = userMapper.getUserDeptById(id);
        /**
         * 开始循环部门，查询部门里面的职位
         */
        if(dlist!=null&&dlist.size()>=1){
            for (DeptBean deptBean : dlist) {
                List<PostBean> plist = deptMapper.getDeptPostList(deptBean.getId());
                Long[] postids = deptMapper.getUserPostByIdAndDeptid(id,deptBean.getId());
                deptBean.setPostids(postids);
                deptBean.setPlist(plist);
            }
        }
        userBean.setDlist(dlist);
        return userBean;
    }
    @Override
    public void saveUserPost(UserBean userBean) {
        /**
         * 先去删除用户的职位
         */
        if(userBean!=null){
            userMapper.deleteUserPostById(userBean.getId());
            if(userBean.getDlist()!=null&&userBean.getDlist().size()>=1){
                for (DeptBean deptBean : userBean.getDlist()) {
                    if(deptBean.getPostids()!=null&&deptBean.getPostids().length>=1){
                        for (Long postid : deptBean.getPostids()) {
                            userMapper.saveUserPost(userBean.getId(),postid);
                        }
                    }
                }
            }
        }
    }

    /**
     *用户登录
     */
    @Override
    public UserBean getLogin(UserBean ub) {
        //先用用户名或者手机号，都是用用户名来接收，有可能用户输入的手机号，都是string类型的，无所谓
        if(ub!=null){
            List<UserBean> list = userMapper.getLogin(ub);
            if(list!=null&&list.size()==1){
                //到了这里表示用用户名或者手机号码查询到了
                //开始比对密码，比对密码之前需要加盐加密
                //加密算法有很多，常用的md5，bscript等
                UserBean userBean = list.get(0);
                //页面用户输入的密码，进行加密加盐后和数据库里面的密码进行比较，相等正确，不相等就错误
                String pwd = ub.getPwd();
                //也就是这里的加盐加密规则和注册的要一致，就好了，都是一个人负责的
                pwd = userBean.getPwdsalt()+pwd+userBean.getPwdsalt();
                MD5key md5key = new MD5key();
                String newpwd = md5key.getkeyBeanofStr(pwd);

                //相等就返回，其他都是空
                if(newpwd.equals(userBean.getPwd())){
                    return userBean;
                }
            }
        }
        return null;
    }
    @Override
    public Set<String> getUserMeunUrlById(UserBean ub) {
        if(ub!=null){
           Set<String> list = userMapper.getUserMeunUrlsById();
        }
        return null;
    }

    @Test
    public void test(){
        String pwd = "2234";
        pwd = "1234"+pwd+"1234";
        MD5key md5key = new MD5key();
        String newpwd = md5key.getkeyBeanofStr(pwd);
        System.out.println(newpwd);
    }


   /* public List<MeunBean> getMeunList2() {
        Long[] ids = {1L,2L,3L};
        List<MeunBean> list = meunMapper.selectByExample(null);
        for (Long id : ids) {
            for (MeunBean bean : list) {
                if(id.equals(bean.getId())){
                    bean.setChecked(true);
                    break;
                }
            }
        }
        return list;
    }*/

}