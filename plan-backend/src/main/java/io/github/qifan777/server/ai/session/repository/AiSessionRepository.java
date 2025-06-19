package io.github.qifan777.server.ai.session.repository;

import io.github.qifan777.server.ai.message.entity.AiMessageFetcher;
import io.github.qifan777.server.ai.session.entity.AiSession;
import io.github.qifan777.server.ai.session.entity.AiSessionFetcher;
import io.github.qifan777.server.ai.session.entity.AiSessionTable;
import io.github.qifan777.server.ai.session.entity.dto.AiSessionSpec;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiSessionRepository extends JRepository<AiSession, String> {
    AiSessionTable t = AiSessionTable.$;
    AiSessionFetcher COMPLEX_FETCHER_FOR_ADMIN = AiSessionFetcher.$.allScalarFields()
            .messages(AiMessageFetcher.$.allScalarFields().sessionId())
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());

    AiSessionFetcher COMPLEX_FETCHER_FOR_FRONT = AiSessionFetcher.$.allScalarFields()
            .messages(AiMessageFetcher.$.allScalarFields().sessionId())
            .creator(true);

    default Page<AiSession> findPage(QueryRequest<AiSessionSpec> queryRequest,
                                     Fetcher<AiSession> fetcher) {
        AiSessionSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}