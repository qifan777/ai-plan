package io.github.qifan777.server.ai.session.entity;

import io.github.qifan777.server.ai.message.entity.AiMessage;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenTextField;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.OrderedProp;

import java.util.List;

/**
 * AI会话
 */
@GenEntity
@Entity
public interface AiSession extends BaseEntity {

    /**
     * 会话名称
     */
    @GenTextField(label = "会话名称", order = 1)
    String name();

    @OneToMany(mappedBy = "session", orderedProps = @OrderedProp(value = "createdTime"))
    List<AiMessage> messages();
}

