package io.github.qifan777.server.ai.message.repository;

import io.github.qifan777.server.ai.message.entity.AiMessage;
import io.github.qifan777.server.ai.message.entity.AiMessageFetcher;
import io.github.qifan777.server.ai.message.entity.AiMessageTable;
import io.github.qifan777.server.ai.message.entity.dto.AiMessageSpec;
import io.github.qifan777.server.ai.session.entity.AiSessionFetcher;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AiMessageRepository extends JRepository<AiMessage, String> {
    AiMessageTable t = AiMessageTable.$;
    AiMessageFetcher COMPLEX_FETCHER_FOR_ADMIN = AiMessageFetcher.$.allScalarFields()
            .session(AiSessionFetcher.$.allScalarFields())
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    AiMessageFetcher COMPLEX_FETCHER_FOR_FRONT = AiMessageFetcher.$.allScalarFields()
            .creator(true);

    default Page<AiMessage> findPage(QueryRequest<AiMessageSpec> queryRequest,
                                     Fetcher<AiMessage> fetcher) {
        AiMessageSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }

    default List<AiMessage> findBySessionId(String sessionId, int lastN) {
        return sql()
                .createQuery(t)
                .where(t.sessionId().eq(sessionId))
                .orderBy(t.createdTime().desc())
                .select(t)
                .limit(lastN)
                .execute();
    }

    default void deleteBySessionId(String sessionId) {
        sql().createDelete(t)
                .where(t.sessionId().eq(sessionId))
                .execute();
    }
}