package com.zyh.smartApi.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zyh.smartApi.model.entity.InterfaceInfo;

/**
* @author 张宇航
* @description 针对表【interface_info】的数据库操作Service
* @createDate 2023-07-29 21:28:28
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo post, boolean add);

    Boolean onlineInterface(long id);

    Boolean offlineInterface(long id);
}
