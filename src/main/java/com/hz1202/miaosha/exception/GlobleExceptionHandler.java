package com.hz1202.miaosha.exception;

import com.hz1202.miaosha.result.CodeMsg;
import com.hz1202.miaosha.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 9:15 2018/5/7
 */
@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {

    @ExceptionHandler
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        if(e instanceof BindException){
            BindException be =  (BindException)e;
            List<ObjectError> errorList = be.getAllErrors();
            String msg = "";
            msg = errorList.get(0).getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }

    }
}
