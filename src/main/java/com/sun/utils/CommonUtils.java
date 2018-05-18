package com.sun.utils;

import java.util.UUID;

/**
 * @ClassName CommonUtils
 * @Description TODO
 * @Author sunkai
 * @Date 2018/5/5 14:36
 * @Version 1.0
 **/
public class CommonUtils {
    //生成uid方法
    public static String getUid(){
        return UUID.randomUUID().toString();
    }
}
