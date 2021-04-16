package com.lyl.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-14 15:59
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    private List<String> exceptUrls;
    //exceptUrls:除了...之外
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String uri = httpServletRequest.getRequestURI();
        System.out.println(uri);
        //uri和url有什么区别
        if(exceptUrls.contains(uri)){
            //直接放行
            return true;
        }else{
            Object ub = httpServletRequest.getSession().getAttribute("ub");
            if(ub == null){
                //它不是特殊的，也没有登录，直接让去登录就可以了
                httpServletRequest.getRequestDispatcher("/index.html").forward(httpServletRequest,httpServletResponse);
            }else{
                return true;
            }
        }
        return false;
    }
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public List getExceptUrls() {
        return exceptUrls;
    }

    public void setExceptUrls(List exceptUrls) {
        this.exceptUrls = exceptUrls;
    }
}
