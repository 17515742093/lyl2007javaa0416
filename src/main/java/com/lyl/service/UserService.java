package com.lyl.service;

import com.lyl.entity.MeunBean;
import com.lyl.entity.UserBean;

import java.util.List;
import java.util.Set;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-07 11:37
 */
public interface UserService {

    List<UserBean> getUserList();

    List<MeunBean> getMeunList(UserBean ub);

    UserBean getUserVoById(Long id);

    void saveUserDept(Long id, Long[] deptids);

    UserBean getUserInfo(Long id);

    void saveUserPost(UserBean userBean);

    UserBean getLogin(UserBean ub);

    Set<String> getUserMeunUrlById(UserBean ub);
}
