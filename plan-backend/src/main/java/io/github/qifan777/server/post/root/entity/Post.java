package io.github.qifan777.server.post.root.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.post.comment.entity.PostComment;
import io.github.qifan777.server.post.like.entity.PostLike;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenImageField;
import io.qifan.infrastructure.generator.core.GenTextField;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Serialized;

import java.util.List;

/**
 * 帖子
 */
@GenEntity
@Entity
public interface Post extends BaseEntity {

    @GenImageField(label = "帖子图片", order = 0)
    @Serialized
    List<String> pictures();

    @Null
    @GenTextField(label = "帖子内容", order = 1)
    String content();

    @OneToMany(mappedBy = "post")
    List<PostComment> comments();

    @OneToMany(mappedBy = "post")
    List<PostLike> likes();
}

