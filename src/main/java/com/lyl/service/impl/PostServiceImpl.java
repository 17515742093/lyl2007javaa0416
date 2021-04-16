package com.lyl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.entity.MeunBean;
import com.lyl.entity.PostBean;
import com.lyl.entity.PostBeanExample;
import com.lyl.mapper.MeunMapper;
import com.lyl.mapper.PostMapper;
import com.lyl.service.PostService;
import com.lyl.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-08 15:11
 */
@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper postMapper;
    @Resource
    private MeunMapper meunMapper;

    public Page<PostBean> getPostListConn(PostBean postBean, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        PostBeanExample example = new PostBeanExample();
        PostBeanExample.Criteria criteria = example.createCriteria();
        if(postBean!=null){
            if(postBean.getPostname()!=null && postBean.getPostname()!=""){
                criteria.andPostnameLike("%"+postBean.getPostname()+"%");
            }
        }
        List<PostBean> list = postMapper.selectByExample(example);
        System.out.println("==========="+list);
        PageInfo<PostBean> pageInfo = new PageInfo<PostBean>(list);
        Long total = pageInfo.getTotal();
        Page page = new Page(pageInfo.getPageNum()+"",total.intValue(),pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }

   public List<MeunBean> getMeunListById(Long postid) {
        //全查菜单
        List<MeunBean> list = meunMapper.selectByExample(null);
        System.out.println("========="+list);
        //当前职位拥有哪些菜单，去中间表查询，只需要id就好
        List<Long> meunids = postMapper.getPostMeunIds(postid);
        //要是原来有菜单的，需要回显，ztree回显，在后台直接回显就OK

        if(meunids!=null&&meunids.size()>=1){
            for (Long meunid : meunids) {
                for (MeunBean bean : list) {
                    if(meunid.equals(bean.getId())){
                        bean.setChecked(true);
                        break;
                    }

                }
            }
        }
        return list;
    }

    public void savePostMeun(Long postid, Long[] ids) {
        //先去删除，中间表的数据
        postMapper.deletePostMeunByPostid(postid);

        if(ids!=null&&ids.length>=1){
            for (Long meunid : ids) {
                postMapper.savePostMeun(postid,meunid);
            }
        }

    }
}
