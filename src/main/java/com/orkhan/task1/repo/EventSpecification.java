package com.orkhan.task1.repo;

import com.orkhan.task1.model.entity.SportEvent;
import com.orkhan.task1.model.EventCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EventSpecification implements Specification<SportEvent> {

    private final EventCriteria criteria;

    @Override
    public Predicate toPredicate(Root<SportEvent> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(criteria.hasSportType()) {
            predicates.add(criteriaBuilder.equal(root.get("sportType"),criteria.getSportType()));
        }
        if(criteria.hasStatus()) {
            predicates.add(criteriaBuilder.equal(root.get("status"),criteria.getStatus()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
