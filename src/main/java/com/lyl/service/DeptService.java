package com.lyl.service;

import com.lyl.entity.DeptBean;
import com.lyl.utils.Page;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-08 11:07
 */
public interface DeptService {

    Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize);

    DeptBean getDeptByDeptid(Long deptid);

    void saveDeptPost(Long deptid, Long[] postids);
}
