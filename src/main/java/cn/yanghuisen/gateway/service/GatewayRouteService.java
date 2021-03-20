package cn.yanghuisen.gateway.service;

import cn.yanghuisen.gateway.base.ResultMessage;
import cn.yanghuisen.gateway.base.TableResult;
import cn.yanghuisen.gateway.dto.GatewayRouteDTO;

import java.util.List;

/**
 * @author Y
 */
public interface GatewayRouteService {

    /**
     * 获取RouteList
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    TableResult<GatewayRouteDTO> getRouteList(GatewayRouteDTO gatewayRouteDTO);


    /**
     * 修改状态
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    boolean updateStatus(GatewayRouteDTO gatewayRouteDTO);


    /**
     * 保存路由
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    ResultMessage saveRoute(GatewayRouteDTO gatewayRouteDTO);


    /**
     * 删除路由
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    ResultMessage deleteRoute(GatewayRouteDTO gatewayRouteDTO);

    /**
     * 更新路由
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    ResultMessage updateRoute(GatewayRouteDTO gatewayRouteDTO);
}
