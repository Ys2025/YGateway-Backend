package cn.yanghuisen.gateway.enums;

/**
 * @author Y
 */
public enum RouteFiltersType {

    /**
     * 添加请求头
     */
    AddRequestHeader("addRequestHeader","添加请求头"),

    /**
     * 添加响应头
     */
    AddResponseHeade("addResponseHeade","添加响应头"),

    /**
     * 添加请求参数
     */
    AddRequestParameter("addRequestParameter","添加请求参数"),

    /**
     * 去除重复的请求头
     */
    DedupeResponseHeader("dedupeResponseHeader","去除重复的请求头"),

    /**
     * Map替换请求头
     */
    MapRequestHeader("mapRequestHeader","Map替换请求头"),

    /**
     * 添加请求前缀
     */
    PrefixPath("prefixPath","添加请求前缀"),

    /**
     * 保持客户端Host地址不变
     */
    PreserveHostHeader("preserveHostHeader","保持客户端Host地址不变"),

    /**
     * 移除请求头
     */
    RemoveRequestHeader("removeRequestHeader","移除请求头"),

    /**
     * 移除响应头
     */
    RemoveResponseHeader("removeResponseHeader","移除响应头"),

    /**
     * 移除请求参数
     */
    RemoveRequestParameter("removeRequestParameter","移除请求参数"),

    /**
     * 正则表达式替换请求
     */
    RewritePath("rewritePath","正则表达式替换请求"),

    /**
     * 移除请求前缀
     */
    StripPrefix("stripPrefix","移除请求前缀");

    private String key;

    private String desc;

    RouteFiltersType() {
    }

    RouteFiltersType(String key, String desc) {
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

    public static RouteFiltersType getRouteFiltersType(String key){
        switch (key){
            case "addRequestHeader":
                return AddRequestHeader;
            case "addResponseHeade":
                return AddResponseHeade;
            case "addRequestParameter":
                return AddRequestParameter;
            case "dedupeResponseHeader":
                return DedupeResponseHeader;
            case "mapRequestHeader":
                return MapRequestHeader;
            case "prefixPath":
                return PrefixPath;
            case "preserveHostHeader":
                return PreserveHostHeader;
            case "removeRequestHeader":
                return RemoveRequestHeader;
            case "removeResponseHeader":
                return RemoveResponseHeader;
            case "removeRequestParameter":
                return RemoveRequestParameter;
            case "rewritePath":
                return RewritePath;
            case "stripPrefix":
                return StripPrefix;
            default:return null;
        }
    }
}
