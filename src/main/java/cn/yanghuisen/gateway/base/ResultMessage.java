package cn.yanghuisen.gateway.base;

import lombok.Data;

/**
 * @author Y
 */
@Data
public class ResultMessage {
    private Integer code = 100;
    private String message = "操作成功";


    public ResultMessage() {
    }

    public ResultMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultMessage(String message) {
        this.message = message;
    }

    public static ResultMessage success(){
        return new ResultMessage();
    }

    public static ResultMessage success(String message){
        return new ResultMessage(message);
    }

    public static ResultMessage fail(){
        return new ResultMessage(101,"操作失败");
    }

    public static ResultMessage fail(String message){
        return new ResultMessage(101,message);
    }

    public static ResultMessage builder(boolean isSuccess){
        return isSuccess?success():fail();
    }

    public static ResultMessage builder(boolean isSuccess,String str1,String str2){
        return isSuccess?success(str1):fail(str2);
    }
}
