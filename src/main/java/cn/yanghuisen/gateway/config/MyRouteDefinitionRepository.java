package cn.yanghuisen.gateway.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import cn.yanghuisen.gateway.mapper.GatewayRouteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Y
 * 自定义路由储存库
 */
@Component
@Slf4j
public class MyRouteDefinitionRepository implements RouteDefinitionRepository {


    private static final String GATEWAY_ROUTES = "gateway:routes";

    @Autowired
    private GatewayRouteMapper gatewayRouteMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    /**
     * 获取路由
     * @return 结果
     * 在项目启动的时候会先触发该方法，从数据库获取路由信息。
     * 如果在程序中使用publisher.publishEvent(new RefreshRoutesEvent(this))发起事件则也会调用该方法。
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        log.info(DateUtil.now());
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        // 获取Redis中配置的路由信息
        List<Object> routes = redisTemplate.opsForHash().values(GATEWAY_ROUTES);
        // 遍历路由
        routes.forEach(route->{
            // 把json反序列话为RouteDefinition类对象
            RouteDefinition routeDefinition = JSONUtil.toBean(route.toString(), RouteDefinition.class);
            routeDefinitions.add(routeDefinition);
        });
        return Flux.fromIterable(routeDefinitions);
    }


    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(routeDefinition -> {
            // 把路由信息存到Redis中
            redisTemplate.opsForHash().put(GATEWAY_ROUTES,routeDefinition.getId(),JSONUtil.toJsonStr(routeDefinition));
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id->{
            // 判断ID是否存在
            if (redisTemplate.opsForHash().hasKey(GATEWAY_ROUTES,id)){
                redisTemplate.opsForHash().delete(GATEWAY_ROUTES,id);
            }
            return Mono.empty();
        });
    }


}
