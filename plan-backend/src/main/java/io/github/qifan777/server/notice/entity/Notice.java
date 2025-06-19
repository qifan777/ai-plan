package io.github.qifan777.server.notice.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenBooleanField;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenTextAreaField;
import io.qifan.infrastructure.generator.core.GenTextField;
import org.babyfish.jimmer.sql.Entity;


/**
 * <p>
 *  公告
 * </p>
 *
 */
@GenEntity
@Entity
public interface Notice extends BaseEntity {

    @GenTextField(label = "标题")
    String title();

    @GenTextAreaField(label = "内容")
    String content();

    @GenBooleanField(label = "是否激活")
    boolean active();

}
