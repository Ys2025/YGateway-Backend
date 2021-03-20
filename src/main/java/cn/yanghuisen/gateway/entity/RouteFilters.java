package cn.yanghuisen.gateway.entity;

import cn.yanghuisen.gateway.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Y
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RouteFilters extends BaseEntity {

    /**
     * RouteObj
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long routeObj;

    /**
     * Key
     */
    private String filtersKey;

    /**
     * Value
     */
    private String filtersValue;
}
