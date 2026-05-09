package com.pichub.cloud_pichub.service;

import com.pichub.cloud_pichub.model.dto.space.analyze.*;
import com.pichub.cloud_pichub.model.entity.Space;
import com.pichub.cloud_pichub.model.vo.space.analyze.*;
import com.pichub.cloud_pichub.model.entity.User;

import java.util.List;

/**
 * @author Yuki
 * @description 空间数据分析
 * @createDate 2026-04-28 18:47:12
 */
public interface SpaceAnalyzeService {

    SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest, User loginUser);

    List<SpaceCategoryAnalyzeResponse> getSpaceCategoryAnalyze(SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest, User loginUser);

    List<SpaceTagAnalyzeResponse> getSpaceTagAnalyze(SpaceTagAnalyzeRequest spaceTagAnalyzeRequest, User loginUser);

    List<SpaceSizeAnalyzeResponse> getSpaceSizeAnalyze(SpaceSizeAnalyzeRequest spaceSizeAnalyzeRequest, User loginUser);

    List<SpaceUserAnalyzeResponse> getSpaceUserAnalyze(SpaceUserAnalyzeRequest spaceUserAnalyzeRequest, User loginUser);

    List<Space> getSpaceRankAnalyze(SpaceRankAnalyzeRequest spaceRankAnalyzeRequest, User loginUser);
}
