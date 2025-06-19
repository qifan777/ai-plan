package io.github.qifan777.server.post.like.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.post.root.entity.Post;
import io.qifan.infrastructure.generator.core.GenAssociationField;
import io.qifan.infrastructure.generator.core.GenEntity;
import org.babyfish.jimmer.sql.DissociateAction;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OnDissociate;

/**
 * 帖子点赞
 */
@GenEntity
@Entity
public interface PostLike extends BaseEntity {
    @GenAssociationField(label = "帖子", prop = "postId", order = 0)
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    Post post();
}

