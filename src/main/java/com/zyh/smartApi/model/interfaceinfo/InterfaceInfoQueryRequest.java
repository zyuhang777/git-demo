package com.zyh.smartApi.model.interfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.zyh.smartApi.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {

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


    private static final long serialVersionUID = 1L;

}