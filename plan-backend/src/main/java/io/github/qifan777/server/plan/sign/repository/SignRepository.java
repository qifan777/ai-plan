package io.github.qifan777.server.plan.sign.repository;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.plan.sign.entity.Sign;
import io.github.qifan777.server.plan.sign.entity.SignFetcher;
import io.github.qifan777.server.plan.sign.entity.SignTable;
import io.github.qifan777.server.plan.sign.entity.dto.SignSpec;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SignRepository extends JRepository<Sign, String> {
    SignTable t = SignTable.$;
    SignFetcher COMPLEX_FETCHER_FOR_ADMIN = SignFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    SignFetcher COMPLEX_FETCHER_FOR_FRONT = SignFetcher.$.allScalarFields()
            .creator(true);
    UserFetcher SIGN_FETCHER=UserFetcher.$.nickname().gender().avatar().signCount();

    default Page<Sign> findPage(QueryRequest<SignSpec> queryRequest,
                                Fetcher<Sign> fetcher) {
        SignSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}