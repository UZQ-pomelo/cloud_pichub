package com.pichub.cloud_pichub.controller;

import com.pichub.cloud_pichub.common.BaseResponse;
import com.pichub.cloud_pichub.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器，用于测试项目基本功能
 */
@RestController
@RequestMapping("/test")
public class TestController {
    /**
     * 测试健康检查接口
     * @return 健康状态
     */
    @GetMapping("/health")
    public BaseResponse<String> health(){
        return ResultUtils.success("OK");
    }

}
