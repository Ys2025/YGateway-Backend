package cn.yanghuisen.gateway.controller;

import cn.hutool.core.util.StrUtil;
import cn.yanghuisen.gateway.base.ResultMessage;
import cn.yanghuisen.gateway.base.TableResult;
import cn.yanghuisen.gateway.dto.GatewayRouteDTO;
import cn.yanghuisen.gateway.handler.GatewayServiceHandler;
import cn.yanghuisen.gateway.service.GatewayRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Y
 */
@RestController
@Slf4j
@RequestMapping("/y-gateway/api/")
public class RouteController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private GatewayRouteService gatewayRouteService;

    @Autowired
    private GatewayServiceHandler gatewayServiceHandler;

    /**
     * 获取路由列表
     * @return 路由集合
     */
    @RequestMapping("/getRouteList")
    public TableResult<GatewayRouteDTO> getRouteList(GatewayRouteDTO gatewayRouteDTO){
        return gatewayRouteService.getRouteList(gatewayRouteDTO);
    }

    /**
     * 修改状态
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    @PostMapping("/updateStatus")
    public ResultMessage updateStatus(@RequestBody GatewayRouteDTO gatewayRouteDTO){
        // 修改数据
        boolean result = gatewayRouteService.updateStatus(gatewayRouteDTO);
        // 触发修改路由
        if (result){
            gatewayServiceHandler.updateRoute(gatewayRouteDTO);
        }
        return ResultMessage.builder(result);
    }

    /**
     * 保存路由
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    @PostMapping("/saveRoute")
    public ResultMessage saveRoute(@RequestBody GatewayRouteDTO gatewayRouteDTO){
        if (StrUtil.isBlank(gatewayRouteDTO.getRouteId()) || StrUtil.isBlank(gatewayRouteDTO.getUri())){
            return ResultMessage.fail("缺少必要参数");
        }
        return gatewayRouteService.saveRoute(gatewayRouteDTO);
    }

    /**
     * 删除路哟
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    @PostMapping("/deleteRoute")
    public ResultMessage deleteRoute(@RequestBody GatewayRouteDTO gatewayRouteDTO){
        return gatewayRouteService.deleteRoute(gatewayRouteDTO);
    }

    /**
     * 更新路由
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    @PostMapping("/updateRoute")
    public ResultMessage updateRoute(@RequestBody GatewayRouteDTO gatewayRouteDTO){
        if (null == gatewayRouteDTO.getId() || StrUtil.isBlank(gatewayRouteDTO.getUri()) || StrUtil.isBlank(gatewayRouteDTO.getRouteId())){
            return ResultMessage.fail("缺少必要参数");
        }
        return gatewayRouteService.updateRoute(gatewayRouteDTO);
    }

    /**
     * 获取所有的服务名
     * @return 结果
     */
        @GetMapping("/getServiceList")
    public List<String> getServiceList(){
        return discoveryClient.getServices();
    }



}
