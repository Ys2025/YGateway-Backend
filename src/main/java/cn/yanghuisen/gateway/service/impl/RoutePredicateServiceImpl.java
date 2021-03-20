package cn.yanghuisen.gateway.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yanghuisen.gateway.base.ResultMessage;
import cn.yanghuisen.gateway.base.TableResult;
import cn.yanghuisen.gateway.dto.RoutePredicateDTO;
import cn.yanghuisen.gateway.entity.RoutePredicate;
import cn.yanghuisen.gateway.enums.RoutePredicateType;
import cn.yanghuisen.gateway.mapper.RoutePredicateMapper;
import cn.yanghuisen.gateway.service.RoutePredicateService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Y
 */
@Service
public class RoutePredicateServiceImpl implements RoutePredicateService {

    @Autowired
    private RoutePredicateMapper routePredicateMapper;

    /**
     * 根据RouteObj查询
     * @param routeObj routeObj
     * @return 查询结果
     */
    @Override
    public List<RoutePredicateDTO> getRoutePredicateByRouteObj(Long routeObj) {
        QueryWrapper<RoutePredicate> qw = new QueryWrapper<>();
        qw.eq("route_obj",routeObj);
        List<RoutePredicate> routePredicates = routePredicateMapper.selectList(qw);
        List<RoutePredicateDTO> tableList = new ArrayList<>();
        routePredicates.forEach(routePredicate -> {
            RoutePredicateDTO dto = new RoutePredicateDTO();
            BeanUtils.copyProperties(routePredicate,dto);
            tableList.add(dto);
        });
        return tableList;
    }

    /**
     * 获取所有的路由断言列表
     * @param routeObj dto
     * @return 结果
     */
    @Override
    public TableResult<RoutePredicateDTO> getRoutePredicateList(String routeObj) {
        List<RoutePredicate> routePredicates = routePredicateMapper.selectList(null);
        List<RoutePredicateDTO> tableList = new ArrayList<>();
        routePredicates.forEach(routePredicate -> {
            RoutePredicateDTO dto = new RoutePredicateDTO();
            BeanUtils.copyProperties(routePredicate,dto);
            tableList.add(dto);
        });
        return new TableResult<>(tableList);
    }

    /**
     * 保存断言
     * @param routePredicateDTO 断言
     * @return 结果
     */
    @Override
    public ResultMessage saveRoutePredicate(RoutePredicateDTO routePredicateDTO) {
        // 判断该类型的数据是否已经存在
        QueryWrapper<RoutePredicate> qw = new QueryWrapper<>();
        qw.eq("route_obj",routePredicateDTO.getRouteObj());
        qw.eq("predicate_key",routePredicateDTO.getPredicateKey());
        RoutePredicate routePredicate = routePredicateMapper.selectOne(qw);
        if (null!=routePredicate){
            return ResultMessage.fail("该类型的断言配置已经存在，请直接修改或者删除配置");
        }
        // 验证数据
        if (!isOk(routePredicateDTO)){
            return ResultMessage.fail("格式不正确");
        }
        // 创建对象
        routePredicate = new RoutePredicate();
        // copy数据
        BeanUtils.copyProperties(routePredicateDTO,routePredicate);
        // 插入数据
        int insert = routePredicateMapper.insert(routePredicate);
        return ResultMessage.builder(insert>0,"保存成功","保存失败");
    }

    /**
     * 更新断言
     * @param routePredicateDTO 断言
     * @return 结果
     */
    @Override
    public ResultMessage updateRoutePredicate(RoutePredicateDTO routePredicateDTO) {
        RoutePredicate routePredicate = routePredicateMapper.selectById(routePredicateDTO.getId());
        if (null == routePredicate){
            return ResultMessage.fail("断言配置不存在");
        }
        routePredicate.setPredicateKey(routePredicateDTO.getPredicateKey());
        routePredicate.setPredicateValue(routePredicateDTO.getPredicateValue());
        int i = routePredicateMapper.updateById(routePredicate);
        return ResultMessage.builder(i>0,"更新成功","更新失败");
    }

    /**
     * 删除断言
     * @param routePredicateDTO 断言
     * @return 结果
     */
    @Override
    public boolean deleteRoutePredicate(RoutePredicateDTO routePredicateDTO) {
        int i = routePredicateMapper.deleteById(routePredicateDTO.getId());
        return i>0;
    }

    /**
     * 是否可用
     * @param dto 断言信息
     * @return 结果
     */
    private boolean isOk(RoutePredicateDTO dto){
        RoutePredicateType routePredicateType = RoutePredicateType.getRoutePredicateType(dto.getPredicateKey());
        if (null == routePredicateType){
            return false;
        }
        String predicateValue = dto.getPredicateValue();
        switch (routePredicateType){
            case Path:
            case Query:
            case Host:
            case Method:
            case After:
            case Before:
            case RemoteAddr:
                return StrUtil.isBlank(predicateValue);
            case Cookie:
            case Header:
            case Between:
                return predicateValue.split(",").length == 2;
            default:
                return false;
        }
    }
}
