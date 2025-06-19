package io.github.qifan777.server.plan.listing.repository;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.plan.listing.entity.Listing;
import io.github.qifan777.server.plan.listing.entity.ListingFetcher;
import io.github.qifan777.server.plan.listing.entity.ListingTable;
import io.github.qifan777.server.plan.listing.entity.dto.ListingSpec;
import io.github.qifan777.server.plan.step.entity.TaskStepFetcher;
import io.github.qifan777.server.plan.task.entity.TaskFetcher;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListingRepository extends JRepository<Listing, String> {
    ListingTable t = ListingTable.$;
    ListingFetcher COMPLEX_FETCHER_FOR_ADMIN = ListingFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    ListingFetcher COMPLEX_FETCHER_FOR_FRONT = ListingFetcher.$.allScalarFields()
            .tasks(TaskFetcher.$.allScalarFields().steps(TaskStepFetcher.$.allScalarFields()))
            .creator(true);

    default Page<Listing> findPage(QueryRequest<ListingSpec> queryRequest,
                                   Fetcher<Listing> fetcher) {
        ListingSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}