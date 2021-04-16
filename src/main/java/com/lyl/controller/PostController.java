package com.lyl.controller;

import com.lyl.entity.MeunBean;
import com.lyl.entity.PostBean;
import com.lyl.mapper.PostMapper;
import com.lyl.service.PostService;
import com.lyl.utils.Page;
import com.lyl.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-08 15:10
 */
@RestController
@RequestMapping("/posts")
public class PostController {
    @Resource
    private PostService postService;
    @RequestMapping("/getPostListConn")
    public Page<PostBean> getPostListConn(@RequestBody PostBean postBean, @RequestParam(defaultValue = "1") Integer pageNum,@RequestParam (defaultValue = "3") Integer pageSize){

        return  postService.getPostListConn(postBean,pageNum,pageSize);
    }
    @RequestMapping("/getMeunListById")
    public List<MeunBean> getMeunListById(Long id){
        return postService.getMeunListById(id);
    }

    @RequestMapping("/savePostMeun")
    public ResultInfo savePostMeun(Long postid, @RequestBody Long[] ids){
        try {
            postService.savePostMeun(postid,ids);
            return new ResultInfo(true, "保存成功");
        }catch (Exception e){
            return new ResultInfo(false, "保存失败");
        }
    }
}
