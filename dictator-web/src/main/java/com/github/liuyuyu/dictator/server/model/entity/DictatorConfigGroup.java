package com.github.liuyuyu.dictator.server.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "dictator_config_group")
public class DictatorConfigGroup {
    /**
     * 自增主键
     */
    @Id
    private Long id;

    /**
     * 分组名
     */
    @Column(name = "group_name")
    private String groupName;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 最后更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 后台操作人ID
     */
    @Column(name = "operator_id")
    private Long operatorId;

    /**
     * 操作者IP
     */
    @Column(name = "operator_ip")
    private String operatorIp;
}