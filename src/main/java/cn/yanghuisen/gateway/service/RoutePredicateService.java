package cn.yanghuisen.gateway.service;

import cn.yanghuisen.gateway.base.ResultMessage;
import cn.yanghuisen.gateway.base.TableResult;
import cn.yanghuisen.gateway.dto.RoutePredicateDTO;

import java.util.List;

/**
 * @author Y
 */
public interface RoutePredicateService {

    /**
     * 根据RouteObj查询
     * @param routeObj routeObj
     * @return 查询结果
     */
    List<RoutePredicateDTO> getRoutePredicateByRouteObj(Long routeObj);

    /**
     * 获取所有的路由断言列表
     * @param routeObj dto
     * @return 结果
     */
    TableResult<RoutePredicateDTO> getRoutePredicateList(String routeObj);

    /**
     * 保存断言
     * @param routePredicateDTO 断言
     * @return 结果
     */
    ResultMessage saveRoutePredicate(RoutePredicateDTO routePredicateDTO);

    /**
     * 更新断言
     * @param routePredicateDTO 断言
     * @return 结果
     */
    ResultMessage updateRoutePredicate(RoutePredicateDTO routePredicateDTO);


    /**
     * 删除路由
     * @param routePredicateDTO dto
     * @return 结果
     */
    boolean deleteRoutePredicate(RoutePredicateDTO routePredicateDTO);


}
