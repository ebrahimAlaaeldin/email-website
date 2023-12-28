package com.cse.mail.controls;

import com.cse.mail.dal.model.Email;
import com.cse.mail.dal.model.User;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;


public class EmailSpecification implements Specification<Email> {

    private final SearchCriteria criteria;

    public EmailSpecification(final SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }
    @Override
    public Predicate toPredicate
            (Root<Email> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                
        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else if (criteria.getKey().equalsIgnoreCase("sender")) {
                Join<Email, User> senderJoin = root.join("sender");
                return builder.like(senderJoin.get("email"), "%" + criteria.getValue() + "%");
            } else if (criteria.getKey().equalsIgnoreCase("receivers")) {
                Join<Email, User> receiversJoin = root.join("receivers");
                return builder.like(receiversJoin.get("email"), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
