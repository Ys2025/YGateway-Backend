package cn.yanghuisen.gateway.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yanghuisen.gateway.base.ResultMessage;
import cn.yanghuisen.gateway.base.TableResult;
import cn.yanghuisen.gateway.dto.GatewayRouteDTO;
import cn.yanghuisen.gateway.entity.GatewayRoute;
import cn.yanghuisen.gateway.mapper.GatewayRouteMapper;
import cn.yanghuisen.gateway.service.GatewayRouteService;
import cn.yanghuisen.gateway.util.GatewayRouteUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Y
 */
@Service
public class GatewayRouteServiceImpl implements GatewayRouteService {

    @Autowired
    public GatewayRouteMapper gatewayRouteMapper;

    /**
     * 获取RouteList
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    @Override
    public TableResult<GatewayRouteDTO> getRouteList(GatewayRouteDTO gatewayRouteDTO) {
        QueryWrapper<GatewayRoute> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(gatewayRouteDTO.getRouteId())){
            qw.like("route_id",gatewayRouteDTO.getRouteId());
        }
        if (null != gatewayRouteDTO.getStatus() && -1 != gatewayRouteDTO.getStatus()){
            qw.eq("status",gatewayRouteDTO.getStatus());
        }
        if (null != gatewayRouteDTO.getType() && -1 != gatewayRouteDTO.getType()){
            qw.eq("type",gatewayRouteDTO.getType());
        }
        qw.orderByDesc("create_time");
        Page<GatewayRoute> page = new Page<>(gatewayRouteDTO.getPage(), gatewayRouteDTO.getSize());
        Page<GatewayRoute> data = gatewayRouteMapper.selectPage(page, qw);
        List<GatewayRouteDTO> tableList = new ArrayList<>();
        data.getRecords().forEach(gatewayRoute -> tableList.add(GatewayRouteUtils.togGatewayRouteDTO(gatewayRoute)));
        return new TableResult<>(tableList, data.getTotal());
    }

    /**
     * 修改状态
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    @Override
    public boolean updateStatus(GatewayRouteDTO gatewayRouteDTO) {
        // 获取ID
        Long id = gatewayRouteDTO.getId();
        // 根据ID查找数据
        GatewayRoute route = gatewayRouteMapper.selectById(id);
        if (null == route){
            return false;
        }
        // 修改状态
        route.setStatus(route.getStatus()==0?1:0);
        // 修改数据
        int i = gatewayRouteMapper.updateById(route);
        return i>0;
    }


    /**
     * 保存路由
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    @Override
    public ResultMessage saveRoute(GatewayRouteDTO gatewayRouteDTO) {

        if (gatewayRouteDTO.getType() == 1){
            String scheme = URI.create(gatewayRouteDTO.getUri()).getScheme();
            if (StrUtil.isBlank(scheme)){
                return ResultMessage.fail("URI格式不正确");
            }
        }


        Integer ord = gatewayRouteDTO.getOrd();
        if (null == ord){
            gatewayRouteDTO.setOrd(0);
        }

        String routeId = gatewayRouteDTO.getRouteId();
        QueryWrapper<GatewayRoute> qw = new QueryWrapper<>();
        qw.eq("route_id",routeId);
        GatewayRoute route = gatewayRouteMapper.selectOne(qw);
        if (null!=route){
            return ResultMessage.fail("路由ID已经存在");
        }
        int insert = gatewayRouteMapper.insert(gatewayRouteDTO);
        return ResultMessage.builder(insert>0,"保存成功","保存失败");
    }

    /**
     * 删除路由
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    @Override
    public ResultMessage deleteRoute(GatewayRouteDTO gatewayRouteDTO) {
        GatewayRoute route = gatewayRouteMapper.selectById(gatewayRouteDTO.getId());
        if (route.getStatus() == 1){
            return ResultMessage.fail("请关闭路由状态后再次操作");
        }
        int i = gatewayRouteMapper.deleteById(gatewayRouteDTO.getId());
        return ResultMessage.builder(i>0,"删除成功","删除失败");
    }

    /**
     * 更新路由
     * @param gatewayRouteDTO dto
     * @return 结果
     */
    @Override
    public ResultMessage updateRoute(GatewayRouteDTO gatewayRouteDTO) {
        GatewayRoute route = gatewayRouteMapper.selectById(gatewayRouteDTO.getId());
        if (null == route){
            return ResultMessage.fail("路由不存在");
        }
        if (route.getStatus() == 1){
            return ResultMessage.fail("请关闭路由状态后再次操作");
        }
        QueryWrapper<GatewayRoute> qw = new QueryWrapper<>();
        qw.eq("route_id",gatewayRouteDTO.getRouteId());
        GatewayRoute gatewayRoute = gatewayRouteMapper.selectOne(qw);
        if (null!=gatewayRoute && !gatewayRoute.getId().equals(gatewayRouteDTO.getId())){
            return ResultMessage.fail("路由ID已经存在");
        }
        BeanUtils.copyProperties(gatewayRouteDTO,route);
        int i = gatewayRouteMapper.updateById(route);
        return ResultMessage.builder(i>0,"修改成功","修改失败");
    }
}
