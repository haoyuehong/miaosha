package com.hz1202.miaosha.exception;

import com.hz1202.miaosha.result.CodeMsg;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 9:32 2018/5/7
 */
public class GlobleException extends RuntimeException {
    private CodeMsg codeMsg;
    public GlobleException (CodeMsg codeMsg){
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }


    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
