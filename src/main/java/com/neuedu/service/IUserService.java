package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;

import javax.servlet.http.HttpSession;

public interface IUserService {
    /**
     * 登录接口
     */
    ServerResponse login(String username,String password);

    /**
     * 注册接口
     */
    ServerResponse register(UserInfo userInfo);

    /**
     *根据用户名返回密保问题接口
     */
    ServerResponse forget_get_question(String username);

    /**
     * 提交问题答案
     */
    ServerResponse forget_check_answer(String username,String question,String answer);
    /**
     * 忘记密码的重置密码接口
     */
    ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken);
    /**
     * 检查用户名或者邮箱是否有效
     */
    ServerResponse check_valid(String str,String type);
    /**
     * 登录状态下重置密码
     */
    ServerResponse reset_password(String username,String passwordOld, String passwordNew);
    /**
     * 登录状态更新个人信息
     */
    ServerResponse update_infomation(UserInfo user);
    /**
     * 根据userid查询用户信息
     */
     UserInfo findUserInfoByUserid(Integer userId);

}
