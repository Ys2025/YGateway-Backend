package cn.yanghuisen.gateway.controller;

import cn.hutool.core.util.StrUtil;
import cn.yanghuisen.gateway.base.ResultMessage;
import cn.yanghuisen.gateway.base.TableResult;
import cn.yanghuisen.gateway.dto.GatewayRouteDTO;
import cn.yanghuisen.gateway.dto.RoutePredicateDTO;
import cn.yanghuisen.gateway.entity.RoutePredicate;
import cn.yanghuisen.gateway.service.RoutePredicateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Y
 * 断言配置
 */
@RestController
@Slf4j
@RequestMapping("/y-gateway/api/")
public class RoutePredicateController {

    @Autowired
    private RoutePredicateService routePredicateService;

    /**
     * 获取路由断言列表
     * @return 路由集合
     */
    @RequestMapping("/getRoutePredicateList")
    public TableResult<RoutePredicateDTO> getRoutePredicateList(String routeObj){
        if (StrUtil.isBlank(routeObj)){
            return null;
        }
        List<RoutePredicateDTO> routePredicateDTOList = routePredicateService.getRoutePredicateByRouteObj(Long.valueOf(routeObj));
        return new TableResult<>(routePredicateDTOList);
    }

    /**
     * 保存断言
     * @param routePredicateDTO 断言
     * @return 结果
     */
    @PostMapping("/saveRoutePredicate")
    public ResultMessage saveRoutePredicate(@RequestBody RoutePredicateDTO routePredicateDTO){
        return routePredicateService.saveRoutePredicate(routePredicateDTO);
    }


    /**
     * 更新断言
     * @param routePredicateDTO 断言
     * @return 结果
     */
    @PostMapping("/updateRoutePredicate")
    public ResultMessage updateRoutePredicate(@RequestBody RoutePredicateDTO routePredicateDTO){
        if (null == routePredicateDTO.getId() || StrUtil.isBlank(routePredicateDTO.getPredicateKey()) || StrUtil.isBlank(routePredicateDTO.getPredicateValue())){
            return ResultMessage.fail("缺少必要参数");
        }
        return routePredicateService.updateRoutePredicate(routePredicateDTO);
    }


    /**
     * 删除断言
     * @param routePredicateDTO 断言
     * @return 结果
     */
    @PostMapping("/deleteRoutePredicate")
    public ResultMessage deleteRoutePredicate(@RequestBody RoutePredicateDTO routePredicateDTO){
        boolean b = routePredicateService.deleteRoutePredicate(routePredicateDTO);
        return ResultMessage.builder(b,"删除成功","删除失败");
    }
}
