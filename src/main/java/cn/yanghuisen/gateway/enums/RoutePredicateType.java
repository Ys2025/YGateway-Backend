package cn.yanghuisen.gateway.enums;

import lombok.Data;

/**
 * @author Y
 */
public enum RoutePredicateType {

    /**
     * Path
     */
    Path("path","路径"),
    /**
     * Query
     */
    Query("query","请求参数"),
    /**
     * Cookie
     */
    Cookie("cookie","cookie"),

    /**
     * Header
     */
    Header("header","请求头"),

    /**
     * Host
     */
    Host("host","host"),

    /**
     * Method
     */
    Method("method","请求方式"),

    /**
     * RemoteAddr
     */
    RemoteAddr("remoteAddr","remoteAddr"),

    /**
     * After
     */
    After("after","After"),

    /**
     * Before
     */
    Before("before","Before"),

    /**
     * Between
     */
    Between("between","Between");


    private String key;

    private String desc;

    RoutePredicateType() {
    }

    RoutePredicateType(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static RoutePredicateType getRoutePredicateType(String code){
        switch (code){
            case "path":
                return Path;
            case "query":
                return Query;
            case "host":
                return Host;
            case "method":
                return Method;
            case "after":
                return After;
            case "before":
                return Before;
            case "remoteAddr":
                return RemoteAddr;
            case "cookie":
                return Cookie;
            case "header":
                return Header;
            case "between":
                return Between;
            default:
                return null;
        }
    }
}
