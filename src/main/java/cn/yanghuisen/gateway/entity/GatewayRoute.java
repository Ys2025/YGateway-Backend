package cn.yanghuisen.gateway.entity;

import cn.yanghuisen.gateway.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GatewayRoute extends BaseEntity {

    /**
     * 路由Id
     */
    private String routeId;

    /**
     * URL
     */
    private String uri;

    /**
     * 类型：0 服务名，1 URL
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer ord;

    /**
     * 状态,0关闭 1 开启
     */
    private Integer status;

    /**
     * 描述
     */
    private String remarks;
}
