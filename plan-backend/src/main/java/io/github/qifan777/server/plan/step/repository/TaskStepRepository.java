package io.github.qifan777.server.plan.step.repository;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.plan.step.entity.TaskStep;
import io.github.qifan777.server.plan.step.entity.TaskStepFetcher;
import io.github.qifan777.server.plan.step.entity.TaskStepTable;
import io.github.qifan777.server.plan.step.entity.dto.TaskStepSpec;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskStepRepository extends JRepository<TaskStep, String> {
    TaskStepTable t = TaskStepTable.$;
    TaskStepFetcher COMPLEX_FETCHER_FOR_ADMIN = TaskStepFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    TaskStepFetcher COMPLEX_FETCHER_FOR_FRONT = TaskStepFetcher.$.allScalarFields()
            .creator(true);

    default Page<TaskStep> findPage(QueryRequest<TaskStepSpec> queryRequest,
                                    Fetcher<TaskStep> fetcher) {
        TaskStepSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}