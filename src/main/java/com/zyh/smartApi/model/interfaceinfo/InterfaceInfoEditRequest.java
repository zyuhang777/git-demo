package com.zyh.smartApi.model.interfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 编辑请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class InterfaceInfoEditRequest implements Serializable {
    /**
     * 接口id号
     */
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
     * 请求参数  [{"name":"type}]
     */
    private String requestParams;

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
    private String responsetHeade;


    /**
     * 接口请求类型
     */
    private String method;
    private static final long serialVersionUID = 1L;
}