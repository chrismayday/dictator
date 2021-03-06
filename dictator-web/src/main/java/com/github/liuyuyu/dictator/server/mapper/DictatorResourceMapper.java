package com.github.liuyuyu.dictator.server.mapper;

import com.github.liuyuyu.dictator.server.model.entity.DictatorResource;
import com.github.liuyuyu.dictator.server.web.model.dto.DictatorResourcePermissionDto;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface DictatorResourceMapper extends SimpleMapper<DictatorResource> {
    List<DictatorResource> findByRoleIdList(@Param("roleIdList") List<Long> roleIdList);

    default Map<Long, List<DictatorResource>> findByParentIdList(@NonNull List<Long> parentIdList){
        Weekend<DictatorResource> weekend = Weekend.of(DictatorResource.class);
        weekend.weekendCriteria()
                .andIn(DictatorResource::getParentId,parentIdList);
        Map<Long,List<DictatorResource>> parentIdResourceMap = new HashMap<>();
        parentIdList.stream()
                .parallel()
                .forEach(parentId->{
                    parentIdResourceMap.put(parentId, Lists.newArrayList());
                });
        this.findAll(weekend).forEach(e->{
            List<DictatorResource> resourceList = parentIdResourceMap.get(e.getParentId());
            resourceList.add(e);
        });
        return parentIdResourceMap;
    }

    List<DictatorResourcePermissionDto> findPermissionByRoleIdList(@Param("roleIdList") List<Long> roleIdList);

    default List<DictatorResource> findByParentId(@NonNull Long parentId){
        Weekend<DictatorResource> weekend = Weekend.of(DictatorResource.class);
        weekend.weekendCriteria()
                .andEqualTo(DictatorResource::getParentId,parentId);
        return this.findAll(weekend);
    }

    List<DictatorResource> findByRoleIdAndParentId(@Param("roleId") Long roleId,@Param("parentId") Long parentId);

    List<DictatorResource> findByUserIdAndParentId(@Param("userId") Long userId, @Param("parentId") Long parentId);

    List<DictatorResource> findByParentIdContains(@Param("parentId") Long parentId);

    int updateParentIdsById(@Param("id") Long id,@Param("parentIds") String parentIds);

    int updateParentIdById(@Param("id") Long id, @Param("parentId") Long parentId);
}