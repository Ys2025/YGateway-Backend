package cn.yanghuisen.gateway.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.yanghuisen.gateway.dto.GatewayRouteDTO;
import cn.yanghuisen.gateway.dto.RouteFiltersDTO;
import cn.yanghuisen.gateway.dto.RoutePredicateDTO;
import cn.yanghuisen.gateway.entity.GatewayRoute;
import cn.yanghuisen.gateway.entity.RoutePredicate;
import cn.yanghuisen.gateway.service.RouteFiltersService;
import cn.yanghuisen.gateway.service.RoutePredicateService;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.handler.predicate.QueryRoutePredicateFactory;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.support.NameUtils;

import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * @author Y
 */
public class GatewayRouteUtils {

    /**
     * 转为GatewayRouteDTO
     * @param gatewayRoute source
     * @return target
     */
    public static GatewayRouteDTO togGatewayRouteDTO(GatewayRoute gatewayRoute){
        GatewayRouteDTO routeDTO = new GatewayRouteDTO();
        BeanUtils.copyProperties(gatewayRoute,routeDTO);
        return routeDTO;
    }



    public static RouteDefinition handleData(GatewayRoute gatewayRoute){
        RouteDefinition routeDefinition = new RouteDefinition();

        URI uri = null;
        // 判断Uri是不是http地址
        if (gatewayRoute.getType() == 0){
            // 微服务服务名
            uri = URI.create("lb://"+gatewayRoute.getUri());
        }else {
            // http地址
            uri = URI.create(gatewayRoute.getUri());
        }

        // 设置路由ID
        routeDefinition.setId(gatewayRoute.getRouteId());
        // 设置uri
        routeDefinition.setUri(uri);
        // 断言(路由转发条件)
        List<RoutePredicateDTO> routePredicateDTOList = SpringUtil.getBean(RoutePredicateService.class).getRoutePredicateByRouteObj(gatewayRoute.getId());
        if (CollUtil.isNotEmpty(routePredicateDTOList)){
            // 解析断言
            List<PredicateDefinition> predicate = RoutePredicateUtils.getPredicate(routePredicateDTOList);
            routeDefinition.setPredicates(predicate);
        }
        // 过滤器
        List<RouteFiltersDTO> routeFiltersDTOList = SpringUtil.getBean(RouteFiltersService.class).getRouteFiltersByRouteObj(gatewayRoute.getId());
        if (CollUtil.isNotEmpty(routeFiltersDTOList)){
            List<FilterDefinition> filterDefinitions = RouteFiltersUtils.getFilterDefinitions(routeFiltersDTOList);
            routeDefinition.setFilters(filterDefinitions);
        }
        // 设置排序
        routeDefinition.setOrder(gatewayRoute.getOrd());
        return routeDefinition;
    }

}
