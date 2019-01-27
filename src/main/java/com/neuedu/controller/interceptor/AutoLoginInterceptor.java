package com.neuedu.interceptor;

import com.google.gson.Gson;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AutoLoginInterceptor implements HandlerInterceptor {

    @Autowired
    IUserService userService;
    /**
     * 调用之前，返回值为false拦截器被拦截
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        System.out.println("===========preHandler===");

        Cookie[] cookies = request.getCookies();
        //登录的情况
        if (cookies!=null)
        {
            for (Cookie cookie:cookies)
            {
                String name = cookie.getName();
                if(name.equals("token"))
                {
                    String value = cookie.getValue();
                    //根据token查询用户信息
                    UserInfo userInfo = userService.getUserInfoByToken(value);
                    if (userInfo!=null)
                    {
                        request.getSession().setAttribute("currentuser",userInfo);
                        return true;
                    }
                }
            }
        }
        //没有登录
        //重置响应
        response.reset();
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        //new一个response给定一个状态码给前端，执行页面跳转
        ServerResponse serverResponse = ServerResponse.createServerResponseByError(100,"请重新登录");
        //Java类转成字符串
        Gson gson = new Gson();
        String json = gson.toJson(serverResponse);
        printWriter.write(json);
        printWriter.flush();
        printWriter.close();
        return false;
    }

    /**
     * 通过拦截器的时候会被调用
     * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

        System.out.println("===========postHandler===");
    }
    /**
     * 整个相应请求完成后调用
     * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        System.out.println("===========afterCompletion===");
    }
}
