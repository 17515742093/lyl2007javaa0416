package com.lyl.entityvo;

import com.lyl.entity.DeptBean;
import com.lyl.entity.UserBean;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-07 19:36
 */
@Data
public class UserVo extends UserBean implements Serializable {
    private Long[] deptids;
    private List<DeptBean> dlist;
}
