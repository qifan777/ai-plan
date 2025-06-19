package io.github.qifan777.server.notice.repository;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.notice.entity.Notice;
import io.github.qifan777.server.notice.entity.NoticeFetcher;
import io.github.qifan777.server.notice.entity.NoticeTable;
import io.github.qifan777.server.notice.entity.dto.NoticeSpec;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepository extends JRepository<Notice, String> {
    NoticeTable t = NoticeTable.$;
    NoticeFetcher COMPLEX_FETCHER_FOR_ADMIN = NoticeFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    NoticeFetcher COMPLEX_FETCHER_FOR_FRONT = NoticeFetcher.$.allScalarFields()
            .creator(true);

    default Page<Notice> findPage(QueryRequest<NoticeSpec> queryRequest,
                                  Fetcher<Notice> fetcher) {
        NoticeSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}