package io.github.qifan777.server.post.like.repository;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.post.like.entity.PostLike;
import io.github.qifan777.server.post.like.entity.PostLikeFetcher;
import io.github.qifan777.server.post.like.entity.PostLikeTable;
import io.github.qifan777.server.post.like.entity.dto.PostLikeSpec;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostLikeRepository extends JRepository<PostLike, String> {
    PostLikeTable t = PostLikeTable.$;
    PostLikeFetcher COMPLEX_FETCHER_FOR_ADMIN = PostLikeFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    PostLikeFetcher COMPLEX_FETCHER_FOR_FRONT = PostLikeFetcher.$.allScalarFields()
            .creator(true);

    default Page<PostLike> findPage(QueryRequest<PostLikeSpec> queryRequest,
                                    Fetcher<PostLike> fetcher) {
        PostLikeSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}