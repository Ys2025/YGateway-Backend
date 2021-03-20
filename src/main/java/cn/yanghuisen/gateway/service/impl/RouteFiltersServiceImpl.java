package cn.yanghuisen.gateway.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yanghuisen.gateway.base.ResultMessage;
import cn.yanghuisen.gateway.dto.RouteFiltersDTO;
import cn.yanghuisen.gateway.dto.RoutePredicateDTO;
import cn.yanghuisen.gateway.entity.RouteFilters;
import cn.yanghuisen.gateway.entity.RoutePredicate;
import cn.yanghuisen.gateway.enums.RouteFiltersType;
import cn.yanghuisen.gateway.mapper.RouteFiltersMapper;
import cn.yanghuisen.gateway.service.RouteFiltersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Y
 */
@Service
public class RouteFiltersServiceImpl implements RouteFiltersService {

    @Autowired
    private RouteFiltersMapper routeFiltersMapper;

    /**
     * 获取路由过滤器列表
     * @param routeObj 路由Obj
     * @return 结果
     */
    @Override
    public List<RouteFiltersDTO> getRouteFiltersByRouteObj(Long routeObj) {
        QueryWrapper<RouteFilters> qw = new QueryWrapper<>();
        qw.eq("route_obj",routeObj);
        List<RouteFilters> routeFilters = routeFiltersMapper.selectList(qw);
        List<RouteFiltersDTO> tableList = new ArrayList<>();
        routeFilters.forEach(routePredicate -> {
            RouteFiltersDTO dto = new RouteFiltersDTO();
            BeanUtils.copyProperties(routePredicate,dto);
            tableList.add(dto);
        });
        return tableList;
    }

    /**
     * 保存路由过滤器
     * @param routeFiltersDTO dto
     * @return 结果
     */
    @Override
    public ResultMessage saveRouteFilters(RouteFiltersDTO routeFiltersDTO) {
        // 判断该类型的值是否已经存在
        QueryWrapper<RouteFilters> qw = new QueryWrapper<>();
        qw.eq("route_obj",routeFiltersDTO.getRouteObj());
        qw.eq("filters_key",routeFiltersDTO.getFiltersKey());
        RouteFilters routeFilters = routeFiltersMapper.selectOne(qw);
        if (null!=routeFilters){
            return ResultMessage.fail("该类型的过滤器已经配置，如要修改请先删除");
        }
        // 验证数据是否合法
        if (!isOk(routeFiltersDTO)){
            return ResultMessage.fail("格式不正确");
        }
        // 创建对象
        routeFilters = new RouteFilters();
        // copy数据
        BeanUtils.copyProperties(routeFiltersDTO,routeFilters);
        // 插入数据
        int insert = routeFiltersMapper.insert(routeFilters);
        return ResultMessage.builder(insert>0,"保存成功","保存失败");
    }


    /**
     * 删除过滤器
     * @param routeFiltersDTO dto
     * @return 结果
     */
    @Override
    public boolean deleteRouteFilters(RouteFiltersDTO routeFiltersDTO) {
        return routeFiltersMapper.deleteById(routeFiltersDTO.getId())>0;
    }



    private boolean isOk(RouteFiltersDTO routeFiltersDTO){
        RouteFiltersType routeFiltersType = RouteFiltersType.getRouteFiltersType(routeFiltersDTO.getFiltersKey());
        if (null == routeFiltersType){
            return false;
        }
        String[] values = routeFiltersDTO.getFiltersValue().split(",");
        switch (routeFiltersType){
            case AddRequestHeader:
            case AddResponseHeade:
            case AddRequestParameter:
            case DedupeResponseHeader:
            case MapRequestHeader:
            case RewritePath:
                return values.length == 2;
            case PrefixPath:
            case RemoveRequestHeader:
            case RemoveResponseHeader:
            case RemoveRequestParameter:
            case StripPrefix:
                return StrUtil.isNotBlank(routeFiltersDTO.getFiltersValue());
            case PreserveHostHeader:
                return true;
            default:return false;
        }
    }
}
