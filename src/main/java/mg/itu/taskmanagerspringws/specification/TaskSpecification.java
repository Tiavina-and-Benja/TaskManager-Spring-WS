package mg.itu.taskmanagerspringws.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import mg.itu.taskmanagerspringws.model.Task;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TaskSpecification implements Specification<Task>{
    public static Specification<Task> hasStatus(String status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Task> hasPriority(String priority) {
        return (root, query, cb) ->
                priority == null ? null : cb.equal(root.get("priority"), priority);
    }

    public static Specification<Task> hasProject(String project) {
        return (root, query, cb) ->
                project == null ? null : cb.equal(root.get("project"), project);
    }

    public static Specification<Task> hasDeadline(LocalDate deadline) {
        return (root, query, cb) ->
                deadline == null ? null : cb.equal(root.get("deadline"), deadline);
    }

    public static Specification<Task> deadlineBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return null;
            if (start != null && end != null) return cb.between(root.get("deadline"), start, end);
            if (start != null) return cb.greaterThanOrEqualTo(root.get("deadline"), start);
            return cb.lessThanOrEqualTo(root.get("deadline"), end);
        };
    }

    @Override
    public @Nullable Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}