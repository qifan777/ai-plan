package io.github.qifan777.server.plan.listing.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.plan.task.entity.Task;
import io.github.qifan777.server.user.root.entity.User;
import io.qifan.infrastructure.generator.core.GenAssociationField;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenTextField;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OneToMany;

import java.util.List;

/**
 * 任务列表
 */
@Entity
@GenEntity
public interface Listing extends BaseEntity {
    @GenTextField(label = "任务列表名称", order = 1)
    String name();

    @GenAssociationField(label = "用户id", prop = "userId")
    @ManyToOne
    User user();

    @OneToMany(mappedBy = "listing")
    List<Task> tasks();
}

