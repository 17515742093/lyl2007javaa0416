package com.lyl.service;

import com.lyl.entity.MeunBean;

import java.util.List;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-08 15:56
 */
public interface MeunService {
    List<MeunBean> getMeunListByPid(Long pid);

    void saveMeun(MeunBean meunBean);

    void deleteMeunById(Long id);

}
