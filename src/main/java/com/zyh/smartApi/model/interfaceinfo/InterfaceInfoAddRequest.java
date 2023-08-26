package com.zyh.smartApi.model.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {



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
     * 请求参数  [{"name":"type}]
     */
    private String requestParams;

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

    private static final long serialVersionUID = 1L;

}