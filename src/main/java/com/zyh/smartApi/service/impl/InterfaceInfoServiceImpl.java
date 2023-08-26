package com.zyh.smartApi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyh.smartApi.common.ErrorCode;
import com.zyh.smartApi.exception.BusinessException;
import com.zyh.smartApi.exception.ThrowUtils;
import com.zyh.smartApi.mapper.InterfaceInfoMapper;
import com.zyh.smartApi.model.entity.InterfaceInfo;
import com.zyh.smartApi.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 张宇航
* @description 针对表【interface_info】的数据库操作Service实现
* @createDate 2023-07-29 21:28:28
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {


        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = interfaceInfo.getId();
        String name = interfaceInfo.getName();
        String url = interfaceInfo.getUrl();
        String description = interfaceInfo.getDescription();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responsetHeade = interfaceInfo.getResponseHeader();
        Integer status = interfaceInfo.getStatus();
        String method = interfaceInfo.getMethod();
        Long userId = interfaceInfo.getUserId();
        Integer isDelete = interfaceInfo.getIsDelete();
        Date createTime = interfaceInfo.getCreateTime();
        Date updateTime = interfaceInfo.getUpdateTime();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name,url,description,requestHeader,responsetHeade,method), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名字过长");
        }
    }

    @Override
    public Boolean onlineInterface(long id) {
        LambdaUpdateWrapper<InterfaceInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(InterfaceInfo::getStatus,1);
        return update(lambdaUpdateWrapper);
    }

    @Override
    public Boolean offlineInterface(long id) {
        LambdaUpdateWrapper<InterfaceInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(InterfaceInfo::getStatus,0);
        return update(lambdaUpdateWrapper);
    }

}




