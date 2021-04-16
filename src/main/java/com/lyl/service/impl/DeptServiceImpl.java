package com.lyl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.entity.DeptBean;
import com.lyl.entity.DeptBeanExample;
import com.lyl.entity.PostBean;
import com.lyl.mapper.DeptMapper;
import com.lyl.mapper.PostMapper;
import com.lyl.service.DeptService;
import com.lyl.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-08 11:08
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Resource
    private DeptMapper deptMapper;

    @Resource
    private PostMapper postMapper;

    public Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        //条件查询
        DeptBeanExample example = new DeptBeanExample();
        DeptBeanExample.Criteria criteria = example.createCriteria();
        //判断条件是否满足
        if(deptBean!=null){
            if(deptBean.getDeptname()!=null&&deptBean.getDeptname().length()>=1){
                criteria.andDeptnameLike("%"+deptBean.getDeptname()+"%");
            }
        }
        List<DeptBean> list = deptMapper.selectByExample(example);

        //分页
        PageInfo pageInfo = new PageInfo(list);
        Long total = pageInfo.getTotal();
        //自己的分页工具类
        Page<DeptBean> page = new Page(pageInfo.getPageNum()+"", total.intValue(), pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }

    public DeptBean getDeptByDeptid(Long deptid) {
        DeptBean deptBean = deptMapper.selectByPrimaryKey(deptid);
        /**
         * 查询这个部门原来的职位（需要在中间表）
         * 还要查询职位列表（直接使用职位的mapper去查询就OK啦）
         */
        List<PostBean> plist = postMapper.selectByExample(null);
        deptBean.setPlist(plist);

        Long[] postids = deptMapper.getDeptPostIds(deptid);
        deptBean.setPostids(postids);
        return deptBean;
    }

    public void saveDeptPost(Long deptid, Long[] postids) {
        deptMapper.depeteDeptPost(deptid);
        if(postids!=null&&postids.length>=1){
            for (Long postid : postids) {
                deptMapper.saveDeptPost(deptid,postid);
            }
        }
    }
}
