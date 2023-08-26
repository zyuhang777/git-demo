package com.zyh.smartApi.model.interfaceinfo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName interface_info
 */
@TableName(value ="interface_info")
@Data
public class InterfaceInfoInvokeRequest implements Serializable {
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
     * 接口请求头信息
     */
    private String requestHeader;

    /**
     * 接口相应头信息
     */
    private String responseHeader;


    /**
     * 接口请求类型
     */
    private String method;



    /**
     * 请求参数  [{"name":"type}]
     */
    private String userRequestParams;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}