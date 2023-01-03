package com.example.system.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 接口统一返回的包装类
 * */

/*统一的包装类，用来表示返回的信息，统一起来，保证前端不需要处理太多的返回类型*/
@Data
/*无参构造*/
@NoArgsConstructor
/*有参构造*/
@AllArgsConstructor
public class Result {
    /*成功与否的编码*/
    private String code;
    /*打印错误信息 如果请求失败，原因是什么*/
    private String msg;
    /*携带的信息 Object,可以存放任何类型的数据*/
    private Object data;

    /*没有数据的成功*/
    public static Result success(){
        return new Result(Constants.CODE_200,"",null);
    }
    /*有数据的成功*/
    public static Result success(Object data){
        return new Result(Constants.CODE_200,"",data);
    }
    /*失败的标识*/
    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }
    /*一个普通的系统错误*/
    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }


}
