package io.github.qifan777.server.post.comment.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.post.root.entity.Post;
import io.qifan.infrastructure.generator.core.GenAssociationField;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenTextField;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.*;

import java.util.List;

/**
 * 帖子评论
 */
@GenEntity
@Entity
public interface PostComment extends BaseEntity {
    @GenAssociationField(label = "帖子", prop = "postId", order = 0)
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    Post post();

    @GenTextField(label = "评论内容", order = 1)
    String content();

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    @GenAssociationField(label = "父亲评论", prop = "parentId", order = 0)
    @Null
    PostComment parent();
    @OneToMany(mappedBy = "parent")
    List<PostComment> children();
}

