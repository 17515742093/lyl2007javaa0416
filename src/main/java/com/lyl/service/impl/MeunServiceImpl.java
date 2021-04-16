package com.lyl.service.impl;

import com.lyl.entity.MeunBean;
import com.lyl.entity.MeunBeanExample;
import com.lyl.mapper.MeunMapper;
import com.lyl.service.MeunService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-08 15:56
 */
@Service
public class MeunServiceImpl implements MeunService {
    @Resource
    private MeunMapper meunMapper;


    public List<MeunBean> getMeunListByPid(Long pid) {
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        return meunMapper.selectByExample(example);
    }

    public void saveMeun(MeunBean meunBean) {
        if(meunBean!=null){
            if(meunBean.getId()!=null){
                /**
                 * 这个修改是遇到实体类中字段值为空的时候，不修改，一般密码等
                 */
                meunMapper.updateByPrimaryKeySelective(meunBean);
                /**
                 * 实体类传递过来是什么，把数据库中全部修改为实体类中的值，即便实体类中为空，也把数据库中修改为空
                 * meunMapper.updateByPrimaryKey(meunBean);
                 */
            }else{
                meunMapper.insertSelective(meunBean);
            }
        }
    }


    Set<Long> ids = new HashSet<Long>();
    public void deleteMeunById(Long id) {

        getMeunListToDeleById(id);

       // System.out.println(ids.size()+"====");

       for (Long id2 : ids){

           System.out.println(ids+"=====-----");

           meunMapper.deleteByPrimaryKey(id2);

           //System.out.println(meunMapper.deleteByPrimaryKey(id2));
       }
    }

    private void getMeunListToDeleById(Long pid){
        ids.add(pid);
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);

        List<MeunBean> list = meunMapper.selectByExample(example);
        if(list!=null&&list.size()>=1){
            for (MeunBean meunBean : list){
                getMeunListToDeleById(meunBean.getId());
            }
        }
    }
}
