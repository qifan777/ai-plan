package io.github.qifan777.server.post.root.repository;

import io.github.qifan777.server.Fetchers;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.post.root.entity.Post;
import io.github.qifan777.server.post.root.entity.PostFetcher;
import io.github.qifan777.server.post.root.entity.PostTable;
import io.github.qifan777.server.post.root.entity.dto.PostSpec;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepository extends JRepository<Post, String> {
    PostTable t = PostTable.$;
    PostFetcher COMPLEX_FETCHER_FOR_ADMIN = PostFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    UserFetcher USER_FETCHER = Fetchers.USER_FETCHER.nickname().avatar();
    PostFetcher COMPLEX_FETCHER_FOR_FRONT = PostFetcher.$.allScalarFields()
            .likes(Fetchers.POST_LIKE_FETCHER.creator(USER_FETCHER))
            .comments(Fetchers.POST_COMMENT_FETCHER
                    .content()
                    .createdTime()
                    .parent(Fetchers.POST_COMMENT_FETCHER
                            .creator(USER_FETCHER))
                    .creator(USER_FETCHER))
            .creator(USER_FETCHER);

    default Page<Post> findPage(QueryRequest<PostSpec> queryRequest,
                                Fetcher<Post> fetcher) {
        PostSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}