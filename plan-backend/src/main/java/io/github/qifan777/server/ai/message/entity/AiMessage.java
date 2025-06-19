package io.github.qifan777.server.ai.message.entity;

import io.github.qifan777.server.ai.session.entity.AiSession;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenAssociationField;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenTextAreaField;
import io.qifan.infrastructure.generator.core.GenTextField;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.experimental.Accessors;
import org.babyfish.jimmer.sql.*;
import org.springframework.ai.chat.messages.MessageType;

import java.util.List;

/**
 * AI消息
 */
@GenEntity
@Entity
public interface AiMessage extends BaseEntity {

    /**
     * 消息类型(用户/助手/系统)
     */
    @GenTextField(label = "消息类型", order = 1)
    MessageType type();

    /**
     * 消息内容
     */
    @GenTextAreaField(label = "消息内容", order = 2)
    String textContent();

    /**
     * 媒体内容如图片链接、语音链接
     */
    @Serialized
    @Null
    List<Media> medias();

    @IdView
    String sessionId();

    /**
     * 会话id
     */
    @GenAssociationField(label = "会话", prop = "id")
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    AiSession session();

    @Data
    @Accessors(chain = true)
    class Media {
        public String type;
        public String data;
    }
}

