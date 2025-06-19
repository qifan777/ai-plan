package io.github.qifan777.server.post.comment.repository;

import io.github.qifan777.server.Fetchers;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.post.comment.entity.PostComment;
import io.github.qifan777.server.post.comment.entity.PostCommentFetcher;
import io.github.qifan777.server.post.comment.entity.PostCommentTable;
import io.github.qifan777.server.post.comment.entity.dto.PostCommentSpec;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCommentRepository extends JRepository<PostComment, String> {
    PostCommentTable t = PostCommentTable.$;
    PostCommentFetcher COMPLEX_FETCHER_FOR_ADMIN = PostCommentFetcher.$.allScalarFields()
            .parent(Fetchers.POST_COMMENT_FETCHER.allScalarFields())
            .post(Fetchers.POST_FETCHER.allScalarFields())
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    PostCommentFetcher COMPLEX_FETCHER_FOR_FRONT = PostCommentFetcher.$.allScalarFields()
            .parent()
            .creator(true);
    PostCommentFetcher RECURSIVE_FETCHER = PostCommentFetcher.$.allScalarFields()
            .recursiveChildren();

    default Page<PostComment> findPage(QueryRequest<PostCommentSpec> queryRequest,
                                       Fetcher<PostComment> fetcher) {
        PostCommentSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}