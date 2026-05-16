package com.pichub.cloud_pichub.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pichub.cloud_pichub.model.dto.spaceuser.SpaceUserAddRequest;
import com.pichub.cloud_pichub.model.dto.spaceuser.SpaceUserQueryRequest;
import com.pichub.cloud_pichub.model.entity.SpaceUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pichub.cloud_pichub.model.vo.SpaceUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Yuki
* @description 针对表【space_user(空间用户关联)】的数据库操作Service
* @createDate 2026-05-10 22:40:41
*/
public interface SpaceUserService extends IService<SpaceUser> {

    long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest);

    void validSpaceUser(SpaceUser spaceUser, boolean add);

    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);

    SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request);

    List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList);
}
