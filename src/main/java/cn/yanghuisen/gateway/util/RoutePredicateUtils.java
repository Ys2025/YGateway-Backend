package cn.yanghuisen.gateway.util;

import cn.hutool.core.util.StrUtil;
import cn.yanghuisen.gateway.dto.RoutePredicateDTO;
import cn.yanghuisen.gateway.enums.RoutePredicateType;
import org.springframework.cloud.gateway.handler.predicate.*;
import org.springframework.cloud.gateway.support.NameUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Y
 */
public class RoutePredicateUtils {
    private static final String SPLIT = ",";

    /**
     * 解析断言
     * @param list list
     * @return 结果
     */
    public static List<PredicateDefinition> getPredicate(List<RoutePredicateDTO> list){
        // 结果级
        List<PredicateDefinition> result = new ArrayList<>();
        // 遍历List集合
        list.forEach(routePredicateDTO -> {
            // 获取类型
            RoutePredicateType type = RoutePredicateType.getRoutePredicateType(routePredicateDTO.getPredicateKey());
            if (null != type){
                switch (type){
                    case Path:
                        PredicateDefinition predicateByPath = getPredicateByPath(routePredicateDTO);
                        if (null!=predicateByPath){
                            result.add(predicateByPath);
                        }
                        break;
                    case Query:
                        PredicateDefinition predicateByQuery = getPredicateByQuery(routePredicateDTO);
                        if (null!=predicateByQuery){
                            result.add(predicateByQuery);
                        }
                        break;
                    case Cookie:
                        PredicateDefinition predicateByCookie = getPredicateByCookie(routePredicateDTO);
                        if (null!=predicateByCookie){
                            result.add(predicateByCookie);
                        }
                        break;
                    case Header:
                        PredicateDefinition predicateByHeader = getPredicateByHeader(routePredicateDTO);
                        if (null!=predicateByHeader){
                            result.add(predicateByHeader);
                        }
                        break;
                    case Host:
                        PredicateDefinition predicateByHost = getPredicateByHost(routePredicateDTO);
                        if (null!=predicateByHost){
                            result.add(predicateByHost);
                        }
                        break;
                    case Method:
                        PredicateDefinition predicateByMethod = getPredicateByMethod(routePredicateDTO);
                        if (null!=predicateByMethod){
                            result.add(predicateByMethod);
                        }
                        break;
                    case RemoteAddr:
                        PredicateDefinition predicateByRemoteAddr = getPredicateByRemoteAddr(routePredicateDTO);
                        if (null!=predicateByRemoteAddr){
                            result.add(predicateByRemoteAddr);
                        }
                        break;
                    case After:
                        PredicateDefinition predicateByAfter = getPredicateByAfter(routePredicateDTO);
                        if (null!=predicateByAfter){
                            result.add(predicateByAfter);
                        }
                        break;
                    case Before:
                        PredicateDefinition predicateByBefore = getPredicateByBefore(routePredicateDTO);
                        if (null!=predicateByBefore){
                            result.add(predicateByBefore);
                        }
                        break;
                    case Between:
                        PredicateDefinition predicateByBetween = getPredicateByBetween(routePredicateDTO);
                        if (null!=predicateByBetween){
                            result.add(predicateByBetween);
                        }
                        break;
                    default:break;
                }
            }
        });
        return result;
    }

    /**
     * Path类型断言
     * @param routePredicateDTO dto
     * @return 结果
     */
    public static PredicateDefinition getPredicateByPath(RoutePredicateDTO routePredicateDTO){
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName(NameUtils.normalizeRoutePredicateName(PathRoutePredicateFactory.class));
        if (StrUtil.isBlank(routePredicateDTO.getPredicateValue())){
            return null;
        }
        predicateDefinition.addArg(PathRoutePredicateFactory.PATTERN_KEY,routePredicateDTO.getPredicateValue());
        return predicateDefinition;
    }

    /**
     * Query类型断言
     * @param routePredicateDTO dto
     * @return 结果
     */
    public static PredicateDefinition getPredicateByQuery(RoutePredicateDTO routePredicateDTO){
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName(NameUtils.normalizeRoutePredicateName(QueryRoutePredicateFactory.class));
        if (StrUtil.isBlank(routePredicateDTO.getPredicateValue())){
            return null;
        }
        String[] split = routePredicateDTO.getPredicateValue().split(SPLIT);
        predicateDefinition.addArg(QueryRoutePredicateFactory.PARAM_KEY,split[0]);
        if (split.length == 2){
            predicateDefinition.addArg(QueryRoutePredicateFactory.REGEXP_KEY,split[1]);
        }
        return predicateDefinition;
    }

    /**
     * Cookie
     * @param routePredicateDTO dto
     * @return 结果
     */
    public static PredicateDefinition getPredicateByCookie(RoutePredicateDTO routePredicateDTO){
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName(NameUtils.normalizeRoutePredicateName(CookieRoutePredicateFactory.class));
        String[] split = routePredicateDTO.getPredicateValue().split(SPLIT);
        if (split.length != 2){
            return null;
        }
        predicateDefinition.addArg(CookieRoutePredicateFactory.NAME_KEY,split[0]);
        predicateDefinition.addArg(CookieRoutePredicateFactory.REGEXP_KEY,split[1]);
        return predicateDefinition;
    }

    /**
     * Header
     * @param routePredicateDTO dto
     * @return 结果
     */
    public static PredicateDefinition getPredicateByHeader(RoutePredicateDTO routePredicateDTO){
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName(NameUtils.normalizeRoutePredicateName(HeaderRoutePredicateFactory.class));
        String[] split = routePredicateDTO.getPredicateValue().split(SPLIT);
        if (split.length != 2){
            return null;
        }
        predicateDefinition.addArg(HeaderRoutePredicateFactory.HEADER_KEY,split[0]);
        predicateDefinition.addArg(HeaderRoutePredicateFactory.REGEXP_KEY,split[0]);
        return predicateDefinition;
    }

    /**
     * Host
     * @param routePredicateDTO dto
     * @return 结果
     */
    public static PredicateDefinition getPredicateByHost(RoutePredicateDTO routePredicateDTO){
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName(NameUtils.normalizeRoutePredicateName(HostRoutePredicateFactory.class));
        if (StrUtil.isBlank(routePredicateDTO.getPredicateValue())){
            return null;
        }
        predicateDefinition.addArg(HostRoutePredicateFactory.PATTERN_KEY,routePredicateDTO.getPredicateValue());
        return predicateDefinition;
    }

    /**
     * Method
     * @param routePredicateDTO dto
     * @return 结果
     */
    public static PredicateDefinition getPredicateByMethod(RoutePredicateDTO routePredicateDTO){
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName(NameUtils.normalizeRoutePredicateName(MethodRoutePredicateFactory.class));
        if (StrUtil.isBlank(routePredicateDTO.getPredicateValue())){
            return null;
        }
        predicateDefinition.addArg(MethodRoutePredicateFactory.METHODS_KEY,routePredicateDTO.getPredicateValue());
        return predicateDefinition;
    }

    /**
     * RemoteAddr
     * @param routePredicateDTO dto
     * @return 结果
     */
    public static PredicateDefinition getPredicateByRemoteAddr(RoutePredicateDTO routePredicateDTO){
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName(NameUtils.normalizeRoutePredicateName(RemoteAddrRoutePredicateFactory.class));
        if (StrUtil.isBlank(routePredicateDTO.getPredicateValue())){
            return null;
        }
        predicateDefinition.addArg(RemoteAddrRoutePredicateFactory.PATTERN_KEY,routePredicateDTO.getPredicateValue());
        return predicateDefinition;
    }

    /**
     * After
     * @param routePredicateDTO dto
     * @return 结果
     */
    public static PredicateDefinition getPredicateByAfter(RoutePredicateDTO routePredicateDTO){
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName(NameUtils.normalizeRoutePredicateName(AfterRoutePredicateFactory.class));
        if (StrUtil.isBlank(routePredicateDTO.getPredicateValue())){
            return null;
        }
        predicateDefinition.addArg(AfterRoutePredicateFactory.DATETIME_KEY,routePredicateDTO.getPredicateValue());
        return predicateDefinition;
    }

    /**
     * Before
     * @param routePredicateDTO dto
     * @return 结果
     */
    public static PredicateDefinition getPredicateByBefore(RoutePredicateDTO routePredicateDTO){
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName(NameUtils.normalizeRoutePredicateName(BeforeRoutePredicateFactory.class));
        if (StrUtil.isBlank(routePredicateDTO.getPredicateValue())){
            return null;
        }
        predicateDefinition.addArg(BeforeRoutePredicateFactory.DATETIME_KEY,routePredicateDTO.getPredicateValue());
        return predicateDefinition;
    }

    /**
     * Between
     * @param routePredicateDTO dto
     * @return 结果
     */
    public static PredicateDefinition getPredicateByBetween(RoutePredicateDTO routePredicateDTO){
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName(NameUtils.normalizeRoutePredicateName(BetweenRoutePredicateFactory.class));
        String[] split = routePredicateDTO.getPredicateValue().split(SPLIT);
        if (split.length != 2){
            return null;
        }
        predicateDefinition.addArg(BetweenRoutePredicateFactory.DATETIME1_KEY,split[0]);
        predicateDefinition.addArg(BetweenRoutePredicateFactory.DATETIME2_KEY,split[1]);
        return predicateDefinition;
    }




}
