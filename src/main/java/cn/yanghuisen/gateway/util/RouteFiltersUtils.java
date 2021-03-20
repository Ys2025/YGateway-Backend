package cn.yanghuisen.gateway.util;

import cn.hutool.core.util.StrUtil;
import cn.yanghuisen.gateway.dto.RouteFiltersDTO;
import cn.yanghuisen.gateway.enums.RouteFiltersType;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.filter.factory.*;
import org.springframework.cloud.gateway.support.NameUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Y
 */
public class RouteFiltersUtils {
    public static List<FilterDefinition> getFilterDefinitions(List<RouteFiltersDTO> routeFiltersDTOList){
        List<FilterDefinition> result = new ArrayList<>();

        routeFiltersDTOList.forEach(routeFiltersDTO -> {
            RouteFiltersType routeFiltersType = RouteFiltersType.getRouteFiltersType(routeFiltersDTO.getFiltersKey());
            if (null != routeFiltersType) {
                switch (routeFiltersType){
                    case AddRequestHeader:
                        FilterDefinition filterDefinitionByAddRequestHeader = getFilterDefinitionByAddRequestHeader(routeFiltersDTO);
                        if (null != filterDefinitionByAddRequestHeader){
                            result.add(filterDefinitionByAddRequestHeader);
                        }
                        break;
                    case AddResponseHeade:
                        FilterDefinition filterDefinitionByAddResponseHeader = getFilterDefinitionByAddResponseHeader(routeFiltersDTO);
                        if (null != filterDefinitionByAddResponseHeader){
                            result.add(filterDefinitionByAddResponseHeader);
                        }
                        break;
                    case AddRequestParameter:
                        FilterDefinition filterDefinitionByAddRequestParameter = getFilterDefinitionByAddRequestParameter(routeFiltersDTO);
                        if (null != filterDefinitionByAddRequestParameter){
                            result.add(filterDefinitionByAddRequestParameter);
                        }
                        break;
                    case DedupeResponseHeader:
                        FilterDefinition filterDefinitionByDedupeResponseHeader = getFilterDefinitionByDedupeResponseHeader(routeFiltersDTO);
                        if (null != filterDefinitionByDedupeResponseHeader){
                            result.add(filterDefinitionByDedupeResponseHeader);
                        }
                        break;
                    case MapRequestHeader:
                        FilterDefinition filterDefinitionByMapRequestHeader = getFilterDefinitionByMapRequestHeader(routeFiltersDTO);
                        if (null != filterDefinitionByMapRequestHeader){
                            result.add(filterDefinitionByMapRequestHeader);
                        }
                        break;
                    case PrefixPath:
                        FilterDefinition filterDefinitionByPrefixPath = getFilterDefinitionByPrefixPath(routeFiltersDTO);
                        if (null != filterDefinitionByPrefixPath){
                            result.add(filterDefinitionByPrefixPath);
                        }
                        break;
                    case PreserveHostHeader:
                        result.add(getFilterDefinitionByPreserveHostHeader());
                        break;
                    case RemoveRequestHeader:
                        FilterDefinition filterDefinitionByRemoveRequestHeader = getFilterDefinitionByRemoveRequestHeader(routeFiltersDTO);
                        if (null != filterDefinitionByRemoveRequestHeader){
                            result.add(filterDefinitionByRemoveRequestHeader);
                        }
                        break;
                    case RemoveResponseHeader:
                        FilterDefinition filterDefinitionByRemoveResponseHeader = getFilterDefinitionByRemoveResponseHeader(routeFiltersDTO);
                        if (null != filterDefinitionByRemoveResponseHeader){
                            result.add(filterDefinitionByRemoveResponseHeader);
                        }
                        break;
                    case RemoveRequestParameter:
                        FilterDefinition filterDefinitionByRemoveRequestParameter = getFilterDefinitionByRemoveRequestParameter(routeFiltersDTO);
                        if (null != filterDefinitionByRemoveRequestParameter){
                            result.add(filterDefinitionByRemoveRequestParameter);
                        }
                        break;
                    case RewritePath:
                        FilterDefinition filterDefinitionByRewritePath = getFilterDefinitionByRewritePath(routeFiltersDTO);
                        if (null != filterDefinitionByRewritePath){
                            result.add(filterDefinitionByRewritePath);
                        }
                        break;
                    case StripPrefix:
                        FilterDefinition filterDefinitionByStripPrefix = getFilterDefinitionByStripPrefix(routeFiltersDTO);
                        if (null != filterDefinitionByStripPrefix){
                            result.add(filterDefinitionByStripPrefix);
                        }
                        break;
                    default:break;
                }
            }

        });
        return result;
    }

    private static FilterDefinition getFilterDefinitionByAddRequestHeader (RouteFiltersDTO routeFiltersDTO){
        String[] data = routeFiltersDTO.getFiltersValue().split(",");
        if (data.length!=2){
            return null;
        }
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(NameUtils.normalizeFilterFactoryName(AddRequestHeaderGatewayFilterFactory.class));
        filterDefinition.addArg(AddRequestHeaderGatewayFilterFactory.NAME_KEY,data[0]);
        filterDefinition.addArg(AddRequestHeaderGatewayFilterFactory.VALUE_KEY,data[1]);
        return filterDefinition;
    }


    private static FilterDefinition getFilterDefinitionByAddResponseHeader (RouteFiltersDTO routeFiltersDTO){
        String[] data = routeFiltersDTO.getFiltersValue().split(",");
        if (data.length!=2){
            return null;
        }
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(NameUtils.normalizeFilterFactoryName(AddResponseHeaderGatewayFilterFactory.class));
        filterDefinition.addArg(AddResponseHeaderGatewayFilterFactory.NAME_KEY,data[0]);
        filterDefinition.addArg(AddResponseHeaderGatewayFilterFactory.VALUE_KEY,data[1]);
        return filterDefinition;
    }


    private static FilterDefinition getFilterDefinitionByAddRequestParameter (RouteFiltersDTO routeFiltersDTO){
        String[] data = routeFiltersDTO.getFiltersValue().split(",");
        if (data.length!=2){
            return null;
        }
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(NameUtils.normalizeFilterFactoryName(AddRequestParameterGatewayFilterFactory.class));
        filterDefinition.addArg(AddRequestParameterGatewayFilterFactory.NAME_KEY,data[0]);
        filterDefinition.addArg(AddRequestParameterGatewayFilterFactory.VALUE_KEY,data[1]);
        return filterDefinition;
    }

    private static FilterDefinition getFilterDefinitionByDedupeResponseHeader (RouteFiltersDTO routeFiltersDTO){
        String[] data = routeFiltersDTO.getFiltersValue().split(",");
        if (data.length!=2){
            return null;
        }
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(NameUtils.normalizeFilterFactoryName(DedupeResponseHeaderGatewayFilterFactory.class));
        filterDefinition.addArg(DedupeResponseHeaderGatewayFilterFactory.NAME_KEY,data[0]);
        filterDefinition.addArg(DedupeResponseHeaderGatewayFilterFactory.VALUE_KEY,data[1]);
        return filterDefinition;
    }

    /**
     *
     * @return
     *
     *  如果 TO_HEADER_KEY 存在，则往 TO_HEADER_KEY 里添加 FROM_HEADER_KEY 对应的值
     *  如果 TO_HEADER_KEY 不存在， 则 往header中添加一个header key 是 TO_HEADER_KEY value是 FROM_HEADER_KEY的值
     */
    private static FilterDefinition getFilterDefinitionByMapRequestHeader (RouteFiltersDTO routeFiltersDTO){
        String[] data = routeFiltersDTO.getFiltersValue().split(",");
        if (data.length!=2){
            return null;
        }
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(NameUtils.normalizeFilterFactoryName(MapRequestHeaderGatewayFilterFactory.class));
        filterDefinition.addArg(MapRequestHeaderGatewayFilterFactory.FROM_HEADER_KEY,data[0]);
        filterDefinition.addArg(MapRequestHeaderGatewayFilterFactory.TO_HEADER_KEY,data[1]);
        return filterDefinition;
    }


    private static FilterDefinition getFilterDefinitionByPrefixPath (RouteFiltersDTO routeFiltersDTO){
        if (StrUtil.isNotBlank(routeFiltersDTO.getFiltersValue())){
            return null;
        }
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(NameUtils.normalizeFilterFactoryName(PrefixPathGatewayFilterFactory.class));
        filterDefinition.addArg(PrefixPathGatewayFilterFactory.PREFIX_KEY,routeFiltersDTO.getFiltersValue());
        return filterDefinition;
    }

    private static FilterDefinition getFilterDefinitionByPreserveHostHeader (){
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(NameUtils.normalizeFilterFactoryName(PreserveHostHeaderGatewayFilterFactory.class));
        filterDefinition.addArg(PreserveHostHeaderGatewayFilterFactory.NAME_KEY,null);
        return filterDefinition;
    }

    private static FilterDefinition getFilterDefinitionByRemoveRequestHeader (RouteFiltersDTO routeFiltersDTO){
        if (StrUtil.isNotBlank(routeFiltersDTO.getFiltersValue())){
            return null;
        }
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(NameUtils.normalizeFilterFactoryName(RemoveRequestHeaderGatewayFilterFactory.class));
        filterDefinition.addArg(RemoveRequestHeaderGatewayFilterFactory.NAME_KEY,routeFiltersDTO.getFiltersValue());
        return filterDefinition;
    }

    private static FilterDefinition getFilterDefinitionByRemoveResponseHeader (RouteFiltersDTO routeFiltersDTO){
        if (StrUtil.isNotBlank(routeFiltersDTO.getFiltersValue())){
            return null;
        }
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(NameUtils.normalizeFilterFactoryName(RemoveResponseHeaderGatewayFilterFactory.class));
        filterDefinition.addArg(RemoveResponseHeaderGatewayFilterFactory.NAME_KEY,routeFiltersDTO.getFiltersValue());
        return filterDefinition;
    }

    private static FilterDefinition getFilterDefinitionByRemoveRequestParameter (RouteFiltersDTO routeFiltersDTO){
        if (StrUtil.isNotBlank(routeFiltersDTO.getFiltersValue())){
            return null;
        }
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(NameUtils.normalizeFilterFactoryName(RemoveRequestParameterGatewayFilterFactory.class));
        filterDefinition.addArg(RemoveRequestParameterGatewayFilterFactory.NAME_KEY,routeFiltersDTO.getFiltersValue());
        return filterDefinition;
    }

    private static FilterDefinition getFilterDefinitionByRewritePath (RouteFiltersDTO routeFiltersDTO){
        String[] data = routeFiltersDTO.getFiltersValue().split(",");
        if (data.length!=2){
            return null;
        }
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(NameUtils.normalizeFilterFactoryName(RewritePathGatewayFilterFactory.class));
        filterDefinition.addArg(RewritePathGatewayFilterFactory.REGEXP_KEY,data[0]);
        filterDefinition.addArg(RewritePathGatewayFilterFactory.REPLACEMENT_KEY,data[1]);
        return filterDefinition;
    }

    private static FilterDefinition getFilterDefinitionByStripPrefix (RouteFiltersDTO routeFiltersDTO){
        if (StrUtil.isNotBlank(routeFiltersDTO.getFiltersValue())){
            return null;
        }
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(NameUtils.normalizeFilterFactoryName(StripPrefixGatewayFilterFactory.class));
        filterDefinition.addArg(StripPrefixGatewayFilterFactory.PARTS_KEY,routeFiltersDTO.getFiltersValue());
        return filterDefinition;
    }
}
