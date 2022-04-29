package com.comerlato.school_project.repository.specification;

import com.comerlato.school_project.entity.Instructor;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Optional;

import static java.util.Optional.empty;

@Builder
public class InstructorSpecification implements Specification<Instructor> {

    @Builder.Default
    private final transient Optional<String> departmentName = empty();
    @Builder.Default
    private final transient Optional<String> headedBy = empty();
    @Builder.Default
    private final transient Optional<String> firstName = empty();
    @Builder.Default
    private final transient Optional<String> lastName = empty();
    @Builder.Default
    private final transient Optional<String> phone = empty();


    @Override
    public Predicate toPredicate(Root<Instructor> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        final var predicates = new ArrayList<Predicate>();

        departmentName.ifPresent(s -> predicates.add(builder.like(
                builder.lower(root.get("departmentName")), "%" + s.toLowerCase() + "%"
        )));
        headedBy.ifPresent(s -> predicates.add(builder.like(
                builder.lower(root.get("headedBy")), "%" + s.toLowerCase() + "%"
        )));
        firstName.ifPresent(s -> predicates.add(builder.like(
                builder.lower(root.get("firstName")), "%" + s.toLowerCase() + "%"
        )));
        lastName.ifPresent(s -> predicates.add(builder.like(
                builder.lower(root.get("lastName")), "%" + s.toLowerCase() + "%"
        )));
        phone.ifPresent(s -> predicates.add(builder.like(
                builder.lower(root.get("phone")), "%" + s.toLowerCase() + "%"
        )));

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
