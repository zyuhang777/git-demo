package com.zyh.smartApi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.zyh.apiclient.httpUtil.HelloHttpClient;
import com.zyh.smartApi.annotation.AuthCheck;
import com.zyh.smartApi.common.BaseResponse;
import com.zyh.smartApi.common.DeleteRequest;
import com.zyh.smartApi.common.ErrorCode;
import com.zyh.smartApi.common.ResultUtils;
import com.zyh.smartApi.constant.UserConstant;
import com.zyh.smartApi.exception.BusinessException;
import com.zyh.smartApi.exception.ThrowUtils;
import com.zyh.smartApi.model.commen.IdRequest;
import com.zyh.smartApi.model.interfaceinfo.*;
import com.zyh.smartApi.model.entity.InterfaceInfo;
import com.zyh.smartApi.model.entity.User;
import com.zyh.smartApi.service.InterfaceInfoService;
import com.zyh.smartApi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.zyh.smartApi.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 接口管理
 *
 * @Author zyh
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceController {

    @Resource
    private InterfaceInfoService interfaceinfoService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest 增加接口接受参数
     * @param request                 HttpServletRequest
     * @Author zyh
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceinfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceinfo);
//        List<String> tags = interfaceinfoAddRequest.getTags();
//        if (tags != null) {
//            interfaceinfo.setTags(GSON.toJson(tags));
//        }
        interfaceinfoService.validInterfaceInfo(interfaceinfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceinfo.setUserId(loginUser.getId());
        boolean result = interfaceinfoService.save(interfaceinfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newInterfaceInfoId = interfaceinfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest 删除接口接收参数
     * @param request       HttpServletRequest
     * @Author zyh
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceinfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceinfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param interfaceInfoUpdateRequest 更新接口信息接收参数
     * @Author zyh
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceinfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceinfo);

        // 参数校验
        interfaceinfoService.validInterfaceInfo(interfaceinfo, false);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceinfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = interfaceinfoService.updateById(interfaceinfo);
        return ResultUtils.success(result);
    }


    /**
     * 分页查询 接口信息
     *
     * @param current  查询页码
     * @param pageSize 查询页大小
     * @Author zyh
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(String current, String pageSize) {
        // 限制爬虫
        ThrowUtils.throwIf(Long.parseLong(pageSize) > 20, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> interfaceInfoPage = interfaceinfoService.
                page(new Page<>(Long.parseLong(current), Long.parseLong(pageSize)),
                        new QueryWrapper<>());
        return ResultUtils.success(interfaceInfoPage);
    }

    @PostMapping("/online")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest) {
        checkRequestParam(idRequest);
        long id = idRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceinfoService.getById(id);
        if (oldInterfaceInfo == null) {
        }
        Integer status = oldInterfaceInfo.getStatus();
        if (status == 1) {
            return ResultUtils.success(true);
        }
        Boolean isOnline = interfaceinfoService.onlineInterface(id);
        return ResultUtils.success(isOnline);
    }

    @PostMapping("/offline")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest) {
        checkRequestParam(idRequest);
        long id = idRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceinfoService.getById(id);
        if (oldInterfaceInfo == null) {
        }
        Integer status = oldInterfaceInfo.getStatus();
        if (status == 0) {
            return ResultUtils.success(true);
        }
        Boolean isOnline = interfaceinfoService.offlineInterface(id);
        return ResultUtils.success(isOnline);
    }

    @PostMapping("/invoke")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<String> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest, HttpServletRequest request) {
        Long interfaceInfoInvokeId = interfaceInfoInvokeRequest.getId();
        if (interfaceInfoInvokeId == null) {
            throw new BusinessException(400000, "接口id为空");
        }
        InterfaceInfo interfaceInfo = interfaceinfoService.getById(interfaceInfoInvokeId);
        if (interfaceInfo == null) {
            throw new BusinessException(50000, "接口不存在");
        }
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        String accessKey = user.getAccessKey();
        String secretKey = user.getSecretKey();
        if (accessKey==null||secretKey==null){
            throw new BusinessException(50000, "该用户没有权限调用该接口功能");
        }
        HelloHttpClient helloHttpClient = new HelloHttpClient("CKLJASHJDKJASKLAJSD","DASKLJDKLASJDKLAJSD");
        com.zyh.apiclient.model.User user1 = new com.zyh.apiclient.model.User();
        user1.setName(interfaceInfoInvokeRequest.getUserRequestParams());
        user1.setAge("11");
        helloHttpClient.httpPostHello(user1);
        return ResultUtils.success(GSON.toJson(user1));
    }


    private void checkRequestParam(IdRequest idRequest) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }


    // ——————————————————————————————————————————————————————————————————————————————————————————————————————

    /**
     * 暂时没用到
     * <p>
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceinfo = interfaceinfoService.getById(id);
        if (interfaceinfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(interfaceinfoService.getById(id));
    }

    /**
     * 暂时没用到
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<InterfaceInfo>> listMyInterfaceInfoByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                                       HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        interfaceInfoQueryRequest.setUserId(loginUser.getId());
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> interfaceinfoPage = interfaceinfoService.page(new Page<>(current, size),
                new QueryWrapper<>());
        return ResultUtils.success(null);
    }

    // endregion

    /**
     * 暂时没用到
     * <p>
     * 分页搜索（从 ES 查询，封装类）
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/search/page/vo")
    public BaseResponse<Page<InterfaceInfo>> searchInterfaceInfoByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                                       HttpServletRequest request) {
        long size = interfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<InterfaceInfo> interfaceinfoPage = interfaceinfoService.searchFromEs(interfaceinfoQueryRequest);
        return ResultUtils.success(null);
    }

    /**
     * 暂时没用到
     *
     * @param interfaceInfoEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editInterfaceInfo(@RequestBody InterfaceInfoEditRequest interfaceInfoEditRequest, HttpServletRequest request) {
        if (interfaceInfoEditRequest == null || interfaceInfoEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceinfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoEditRequest, interfaceinfo);
        // 参数校验
        interfaceinfoService.validInterfaceInfo(interfaceinfo, false);
        User loginUser = userService.getLoginUser(request);
        long id = interfaceInfoEditRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceinfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldInterfaceInfo.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceinfoService.updateById(interfaceinfo);
        return ResultUtils.success(result);
    }

}
