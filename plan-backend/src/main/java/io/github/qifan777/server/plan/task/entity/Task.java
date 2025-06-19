package io.github.qifan777.server.plan.task.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.infrastructure.model.UploadFile;
import io.github.qifan777.server.plan.listing.entity.Listing;
import io.github.qifan777.server.plan.step.entity.TaskStep;
import io.github.qifan777.server.user.root.entity.User;
import io.qifan.infrastructure.generator.core.*;
import org.babyfish.jimmer.sql.*;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务
 */
@GenEntity
@Entity
public interface Task extends BaseEntity {

    /**
     * 任务名称
     */
    @GenTextField(label = "任务名称", order = 1)
    String name();

    /**
     * 完成时间
     */
    @GenDateTimeField(label = "完成时间", order = 2)
    @Null
    LocalDateTime finishTime();

    /**
     * 提醒时间
     */
    @GenDateTimeField(label = "提醒时间", order = 3)
    @Null
    LocalDateTime remindTime();

    /**
     * 附件
     */
    @Serialized
    @Null
    List<UploadFile> files();

    /**
     * 用户id
     */
    @GenAssociationField(label = "用户id", prop = "userId", order = 4)
    @ManyToOne
    User user();

    /**
     * 是否完成
     */
    @GenBooleanField(label = "是否完成", order = 5)
    @Null
    Boolean checked();

    @OneToMany(mappedBy = "task")
    List<TaskStep> steps();

    @OnDissociate(DissociateAction.DELETE)
    @ManyToOne
    Listing listing();
}

