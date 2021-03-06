package com.github.liuyuyu.dictator.server.web.service;

import com.github.liuyuyu.dictator.common.utils.BeanConverter;
import com.github.liuyuyu.dictator.server.mapper.DictatorResourceMapper;
import com.github.liuyuyu.dictator.server.mapper.DictatorRoleResourceMapper;
import com.github.liuyuyu.dictator.server.model.entity.DictatorResource;
import com.github.liuyuyu.dictator.server.web.annotation.TransactionalAutoRollback;
import com.github.liuyuyu.dictator.server.web.model.dto.DictatorResourceDto;
import com.github.liuyuyu.dictator.server.web.model.param.ResourceSaveOrUpdateParam;
import com.github.liuyuyu.dictator.server.web.model.type.ResourceTypeEnum;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author liuyuyu
 */
@Service
public class ResourceService {
    @Autowired private DictatorResourceMapper resourceMapper;
    @Autowired private DictatorRoleResourceMapper roleResourceMapper;

    @TransactionalAutoRollback
    public Long saveOrUpdate(@NonNull ResourceSaveOrUpdateParam resourceSaveOrUpdateParam){
        DictatorResource dictatorResourceEntity = resourceSaveOrUpdateParam.to(DictatorResource.class);
        if(dictatorResourceEntity.getId() == null){
            DictatorResource parentResourceEntity = this.resourceMapper.selectByPrimaryKey(dictatorResourceEntity.getParentId());
            if(parentResourceEntity != null){
                dictatorResourceEntity.setParentIds(parentResourceEntity.getParentId() + "/" + resourceSaveOrUpdateParam.getParentId());
            }else{
                dictatorResourceEntity.setParentIds("/"+dictatorResourceEntity.getParentId());
            }
            dictatorResourceEntity.setResourceType(ResourceTypeEnum.valueOf(resourceSaveOrUpdateParam.getResourceType()).getValue());
            this.resourceMapper.insertSelective(dictatorResourceEntity);
        }else{
            this.resourceMapper.updateByPrimaryKeySelective(dictatorResourceEntity);
        }
        return dictatorResourceEntity.getId();
    }

    @TransactionalAutoRollback
    public void delete(@NonNull Long resourceId){
        //删除资源
        this.resourceMapper.deleteByPrimaryKey(resourceId);
        //删除和角色的关联
        this.roleResourceMapper.deleteByResourceId(resourceId);
    }

    public List<DictatorResourceDto> findByParentId(@NonNull List<Long> parentIdList){
        Map<Long, List<DictatorResource>> parentIdResourceMap = this.resourceMapper.findByParentIdList(parentIdList);
        List<Long> nextParentIdList = parentIdResourceMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .map(DictatorResource::getId)
                .collect(Collectors.toList());
        List<DictatorResource> flatResourceList = parentIdResourceMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        List<DictatorResourceDto> resourceDtoList = BeanConverter.from(flatResourceList)
                .toList(DictatorResourceDto.class);
        resourceDtoList.forEach(r-> r.setResourceTypeName(ResourceTypeEnum.valueOf(r.getResourceType()).getName()));

        if(!nextParentIdList.isEmpty()){
            List<DictatorResourceDto> children = this.findByParentId(nextParentIdList);
            resourceDtoList.forEach(r -> {
                final List<Long> pidList = Arrays.stream(r.getParentIds().split("/"))
                        .filter(StringUtils::isNotBlank)
                        .map(Long::valueOf)
                        .collect(Collectors.toList());
                r.setParentIdList(pidList);
                List<DictatorResourceDto> childrenList = children.stream()
                        .filter(c -> r.getId().equals(c.getParentId()))
                        .collect(Collectors.toList());
                r.getChildren().addAll(childrenList);
            });
        }
        return resourceDtoList;
    }

    public List<DictatorResourceDto> findByUserIdAndParentId(@NonNull Long userId, @NonNull Long parentId){
        List<DictatorResource> resourceList = this.resourceMapper.findByUserIdAndParentId(userId,parentId);

        List<Long> nextParentIdList = resourceList.stream()
                .map(DictatorResource::getId)
                .collect(Collectors.toList());
        List<DictatorResourceDto> resourceDtoList = BeanConverter.from(resourceList)
                .toList(DictatorResourceDto.class);
        resourceDtoList.forEach(r-> r.setResourceTypeName(ResourceTypeEnum.valueOf(r.getResourceType()).getName()));

        if(!nextParentIdList.isEmpty()){
            resourceDtoList.stream()
                    .forEach(r->{
                        List<DictatorResourceDto> children = this.findByUserIdAndParentId(userId,r.getId());
                        List<DictatorResourceDto> childrenList = children.stream()
                                .filter(c -> r.getId().equals(c.getParentId()))
                                .collect(Collectors.toList());
                        r.getChildren().addAll(childrenList);
                    });
        }
        return resourceDtoList;
    }

    public List<DictatorResourceDto> findMine(@NonNull Long userId) {
        return null;
    }

    public List<DictatorResourceDto> findByUserId(@NonNull Long userId) {
        return this.findByUserIdAndParentId(userId,0L);
    }

    /**
     * 改变父节点
     */
    public void changeParent(@NotNull Long resourceId,@NonNull Long newParentId){
        final Optional<DictatorResource> currentOpt = this.resourceMapper.findById(resourceId);
        if(!currentOpt.isPresent()){
            return;
        }
        final Optional<DictatorResource> newParentOpt = this.resourceMapper.findById(newParentId);
        if(!newParentOpt.isPresent()){
            return;
        }
        DictatorResource current = currentOpt.get();
        DictatorResource newParent = newParentOpt.get();
        final String currentParentIds = newParent.getParentIds() + "/" + newParentId;
        this.resourceMapper.updateParentIdById(resourceId,newParentId);
        this.resourceMapper.updateParentIdsById(resourceId,currentParentIds);

        final String newParentIds = currentParentIds + "/" + current.getId();
        //所有下一级节点
        List<DictatorResource> dictatorResourceList = this.resourceMapper.findByParentIdContains(current.getId());
        dictatorResourceList.forEach(r->{
                    //可食用部分
                    final int start = r.getParentIds().indexOf("/" + r.getParentId()) + r.getParentId().toString().length() + 1;
                    String tenderloin = StringUtils.EMPTY;
                    if(start >= 0){
                        tenderloin = StringUtils.substring(r.getParentIds(), start);
                    }
                    this.resourceMapper.updateParentIdsById(r.getId(),newParentIds + "/" + tenderloin);
                });
    }
}
