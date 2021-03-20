package cn.yanghuisen.gateway.controller;

import cn.yanghuisen.gateway.base.ResultMessage;
import cn.yanghuisen.gateway.base.TableResult;
import cn.yanghuisen.gateway.dto.RouteFiltersDTO;
import cn.yanghuisen.gateway.service.RouteFiltersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Y
 * 过滤器配置
 */
@RestController
@Slf4j
@RequestMapping("/y-gateway/api/")
public class RouteFiltersController {

    @Autowired
    private RouteFiltersService routeFiltersService;

    /**
     * 获取路由过滤器列表
     * @param routeObj 路由Obj
     * @return 结果
     */
    @RequestMapping("/getRouteFiltersByRouteObj")
    public TableResult<RouteFiltersDTO> getRouteFiltersByRouteObj(Long routeObj){
        List<RouteFiltersDTO> routeFiltersList = routeFiltersService.getRouteFiltersByRouteObj(routeObj);
        return new TableResult<>(routeFiltersList);
    }

    /**
     * 保存路由过滤器
     * @param routeFiltersDTO dto
     * @return 结果
     */
    @PostMapping("/saveRouteFilters")
    public ResultMessage saveRouteFilters(@RequestBody RouteFiltersDTO routeFiltersDTO){
        return routeFiltersService.saveRouteFilters(routeFiltersDTO);
    }

    /**
     * 删除过滤器
     * @param routeFiltersDTO dto
     * @return 结果
     */
    @PostMapping("/deleteRouteFilters")
    public ResultMessage deleteRouteFilters(@RequestBody RouteFiltersDTO routeFiltersDTO){
        boolean b = routeFiltersService.deleteRouteFilters(routeFiltersDTO);
        return ResultMessage.builder(b,"删除成功","删除失败");
    }
}
