package io.github.qifan777.server.plan.task.repository;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.plan.listing.entity.ListingFetcher;
import io.github.qifan777.server.plan.step.entity.TaskStepFetcher;
import io.github.qifan777.server.plan.task.entity.Task;
import io.github.qifan777.server.plan.task.entity.TaskFetcher;
import io.github.qifan777.server.plan.task.entity.TaskTable;
import io.github.qifan777.server.plan.task.entity.dto.TaskSpec;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskRepository extends JRepository<Task, String> {
    TaskTable t = TaskTable.$;
    TaskFetcher COMPLEX_FETCHER_FOR_ADMIN = TaskFetcher.$.allScalarFields()
            .listing(ListingFetcher.$.name())
            .steps(TaskStepFetcher.$.allScalarFields())
            .creator(true)
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    TaskFetcher COMPLEX_FETCHER_FOR_FRONT = TaskFetcher.$.allScalarFields()
            .listing()
            .steps(TaskStepFetcher.$.allScalarFields())
            .creator(true);

    default Page<Task> findPage(QueryRequest<TaskSpec> queryRequest,
                                Fetcher<Task> fetcher) {
        TaskSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }

    default List<Task> findUserTasks(String userId) {
        return sql().createQuery(t)
                .where(t.userId().eq(userId))
                .select(t.fetch(COMPLEX_FETCHER_FOR_FRONT))
                .execute();
    }
    default void checkTask(String taskId,boolean checked){
        sql().createUpdate(t)
                .where(t.id().eq(taskId))
                .set(t.checked(),checked)
                .execute();
    }
}