package cn.yanghuisen.gateway.handler;

import cn.yanghuisen.gateway.dto.GatewayRouteDTO;
import cn.yanghuisen.gateway.entity.GatewayRoute;
import cn.yanghuisen.gateway.mapper.GatewayRouteMapper;
import cn.yanghuisen.gateway.util.GatewayRouteUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Y
 */
@Slf4j
@Component
public class GatewayServiceHandler implements ApplicationEventPublisherAware, CommandLineRunner {

    private static final String GATEWAY_ROUTES = "gateway:routes";

    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private GatewayRouteMapper gatewayRouteMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        this.loadRouteConfig();
    }

    public void loadRouteConfig(){
        log.info("开始加载网关路由配置信息");
        Boolean deleted = redisTemplate.delete(GATEWAY_ROUTES);
        log.info("Redis路由缓存删除{}", deleted ?"成功":"失败");
        // 从数据库查询数据
        QueryWrapper<GatewayRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        List<GatewayRoute> gatewayRoutes = gatewayRouteMapper.selectList(queryWrapper);
        log.info("从数据库中获取到{}条路由信息",gatewayRoutes.size());

        gatewayRoutes.forEach(gatewayRoute -> {
            RouteDefinition definition = GatewayRouteUtils.handleData(gatewayRoute);
            // 保存路由
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            log.info("Gateway保存{}路由信息成功",definition.getId());
        });
        // 发布事件，通知更新数据
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


    /**
     * 更新路由
     * @param dto dto
     */
    public void updateRoute(GatewayRouteDTO dto){
        // 查询最新的路由信息
        GatewayRoute route = gatewayRouteMapper.selectById(dto.getId());
        // 获取RouteDefinition
        RouteDefinition definition = GatewayRouteUtils.handleData(route);
        // 判断状态，1 开启，0 关闭
        if (route.getStatus() == 1){
            // 重新保存路由数据
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        }

        if (route.getStatus() == 0){
            // 重新保存路由数据
            routeDefinitionWriter.delete(Mono.just(definition.getId())).subscribe();
        }
        // 发布事件，通知更新数据
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
