package io.github.qifan777.server.plan.step.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.plan.task.entity.Task;
import io.qifan.infrastructure.generator.core.GenAssociationField;
import io.qifan.infrastructure.generator.core.GenBooleanField;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenTextField;
import org.babyfish.jimmer.sql.*;

import javax.validation.constraints.Null;

/**
 * 任务步骤
 */
@GenEntity
@Entity
public interface TaskStep extends BaseEntity {
    /**
     * 步骤名称
     */
    @Key
    @GenTextField(label = "步骤名称", order = 1)
    String name();

    /**
     * 是否完成
     */
    @GenBooleanField(label = "是否完成", order = 2)
    @Null
    Boolean checked();

    /**
     * 任务id
     */
    @Key
    @ManyToOne
    @GenAssociationField(label = "任务id", order = 3)
    @OnDissociate(DissociateAction.DELETE)
    Task task();
}

