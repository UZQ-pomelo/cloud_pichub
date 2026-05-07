package com.pichub.cloud_pichub.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pichub.cloud_pichub.model.dto.space.SpaceAddRequest;
import com.pichub.cloud_pichub.model.dto.space.SpaceQueryRequest;
import com.pichub.cloud_pichub.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pichub.cloud_pichub.model.entity.User;
import com.pichub.cloud_pichub.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Yuki
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2026-05-05 17:46:45
*/
public interface SpaceService extends IService<Space> {

    /**
     * 校验空间数据
     *
     * @param space 空间对象
     * @param add   是否为创建时校验（true 创建，false 编辑）
     */
    void validSpace(Space space, boolean add);

    /**
     * 根据空间级别自动填充限额
     */
    void fillSpaceBySpaceLevel(Space space);

    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 获取查询条件
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    void checkSpaceAuth(User loginUser, Space space);
}
