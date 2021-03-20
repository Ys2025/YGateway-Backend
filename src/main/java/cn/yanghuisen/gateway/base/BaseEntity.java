package cn.yanghuisen.gateway.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Y
 */
@Data
public class BaseEntity {

    /**
     * ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 设置填充策略-插入时填充
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 设置填充策略-插入和更新时填充
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 分页
     */
    @TableField(exist = false)
    private Integer page = 1;

    /**
     * 每页数量
     */
    @TableField(exist = false)
    private Integer size = 10;
}
