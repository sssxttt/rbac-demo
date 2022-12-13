package top.xiaomingxing.constant;

public enum ResultCode {
    /**
     * 通用状态码
     */
    SUCCESS(20000,"OK"),
    FAILED(-1,"FAIL"),


    /**
     * 自定义状态码
     */

    /**
     * 认证和授权 5000~5999
     */
    LOGIN_SUCCESS(5000, "登陆成功"),
    LOGIN_FAILED(5001, "用户名或密码错误"),






    UNKNOWN_CODE(9999, "unknown");

    //返回状态码
    private Integer code;

    //返回消息
    private String msg;

    private ResultCode() {
    
    }
    
    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}