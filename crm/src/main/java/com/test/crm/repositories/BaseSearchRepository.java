package com.test.crm.repositories;

import com.test.crm.models.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class BaseSearchRepository<T extends BaseEntity> {
  @PersistenceContext
  private EntityManager entityManager;

  private final Class<T> entityClass;

  public List<T> searchByFields(Map<String, Object> fields) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(entityClass);
    Root<T> product = cq.from(entityClass);
    List<Predicate> predicates = new ArrayList<>();
    fields.forEach((field, value) -> predicates.add(cb.equal(product.get(field), value)));
    cq.where(predicates.toArray(new Predicate[0]));

    TypedQuery<T> query = entityManager.createQuery(cq);
    return query.getResultList();
  }
}
