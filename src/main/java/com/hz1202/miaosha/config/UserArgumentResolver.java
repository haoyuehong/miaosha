package com.hz1202.miaosha.config;

import com.hz1202.miaosha.model.MiaoShaUser;
import com.hz1202.miaosha.service.MiaoShaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 8:47 2018/5/8
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

    @Autowired
    private MiaoShaUserService userService;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == MiaoShaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //1.获取request和response
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        //获取token,cookieToken为从请求中获取的，paramToken为从参数中得到的
        String paramToken = request.getParameter(MiaoShaUserService.COOKIE_TOKEN_NAME);
        String cookieToken = getCookieValue(request,MiaoShaUserService.COOKIE_TOKEN_NAME);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return userService.getByToken(token,response);

    }

    private String getCookieValue(HttpServletRequest request, String cookieTokenName) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieTokenName)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
