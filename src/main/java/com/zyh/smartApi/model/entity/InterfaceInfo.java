package com.zyh.smartApi.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName interface_info
 */
@TableName(value ="interface_info")
@Data
public class InterfaceInfo implements Serializable {
    /**
     * 接口id号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口请求地址
     */
    private String url;

    /**
     * 接口信息描述
     */
    private String description;

    /**
     * 接口请求头信息
     */
    private String requestHeader;

    /**
     * 接口相应头信息
     */
    private String responseHeader;

    /**
     * 接口提供状态码（0：不能使用1：可以使用默认为0）
     */
    private Integer status;

    /**
     * 接口请求类型
     */
    private String method;

    /**
     * 接口创建者id
     */
    private Long userId;

    /**
     * 接口是否删除（0：未删除  1：删除 默认为0）
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 接口创建时间
     */
    private Date createTime;

    /**
     * 请求参数  [{"name":"type}]
     */
    private String requestParams;

    /**
     * 接口更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}