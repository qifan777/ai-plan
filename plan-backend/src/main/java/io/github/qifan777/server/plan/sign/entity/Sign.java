package io.github.qifan777.server.plan.sign.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.user.root.entity.User;
import io.qifan.infrastructure.generator.core.GenAssociationField;
import io.qifan.infrastructure.generator.core.GenEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToOne;

/**
 * 签到
 */
@GenEntity
@Entity
public interface Sign extends BaseEntity {
    @GenAssociationField(label = "用户", prop = "userId")
    @ManyToOne
    User user();
}

