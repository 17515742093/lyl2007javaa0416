package com.lyl.service;

import com.lyl.entity.MeunBean;
import com.lyl.entity.PostBean;
import com.lyl.utils.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-08 15:11
 */
public interface PostService {
    Page<PostBean> getPostListConn(PostBean postBean, Integer pageNum, Integer pageSize);

    List<MeunBean> getMeunListById(@Param("postid") Long id);

    void savePostMeun(Long postid, Long[] ids);
}
