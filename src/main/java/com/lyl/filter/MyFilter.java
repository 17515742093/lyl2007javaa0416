package com.lyl.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @version 1.0
 * @author： 刘云龙
 * @date： 2021-04-14 11:03
 */
public class MyFilter implements Filter {
    Set<String> notCheckUrl = new HashSet<String>();
    //过滤器要是需要在web.xml中配置参数，读取的话，在init中读取

    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("notCheckUrl");
        for (String url : urls.split(",")) {
            notCheckUrl.add(url.trim());
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /**
         * 要是这个请求过来的url是不需要过滤的，不管怎么样都直接放行
         */
        HttpServletRequest req =(HttpServletRequest) servletRequest;
        String uri = req.getRequestURI();
        System.out.println(uri);
        //uri和url有什么区别
        if(notCheckUrl.contains(uri)){
            //直接放行
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            Object ub = req.getSession().getAttribute("ub");
            if(ub == null){
                //它不是特殊的，也没有登录，直接让去登录就可以了
                req.getRequestDispatcher("/index.html").forward(servletRequest,servletResponse);
            }else{
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }
    }
    public void destroy() {

    }
}
