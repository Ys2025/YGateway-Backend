package cn.yanghuisen.gateway.service;

import cn.yanghuisen.gateway.base.ResultMessage;
import cn.yanghuisen.gateway.dto.RouteFiltersDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Y
 */
public interface RouteFiltersService {

    /**
     * 获取路由过滤器列表
     * @param routeObj 路由Obj
     * @return 结果
     */
    List<RouteFiltersDTO> getRouteFiltersByRouteObj(Long routeObj);

    /**
     * 保存路由过滤器
     * @param routeFiltersDTO dto
     * @return 结果
     */
    ResultMessage saveRouteFilters(RouteFiltersDTO routeFiltersDTO);

    /**
     * 删除过滤器
     * @param routeFiltersDTO dto
     * @return 结果
     */
    boolean deleteRouteFilters(RouteFiltersDTO routeFiltersDTO);
}
