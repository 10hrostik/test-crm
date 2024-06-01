package com.test.crm.services;

import com.test.crm.models.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class BaseService<T extends BaseEntity> {

  public T getExistent(String id) {
    if (id == null) {
      throw new RuntimeException(String.format("Id cannot be null for entity %s", this.getClass().getName()));
    }
    return getRepository().findById(id).orElseThrow(() -> new RuntimeException("No record found with id: " + id));
  }

  @Transactional
  public T save(T entity) {
    return getRepository().save(entity);
  }

  public List<T> saveAll(List<T> entities) {
    return entities.stream().peek(this::save).toList();
  }

  public void delete(T entity) {
    getRepository().delete(entity);
  }

  @Transactional
  public void delete(String id) {
    getRepository().deleteById(id);
  }

  @Transactional
  public T update(T entity) {
    return getRepository().save(entity);
  }

  protected abstract JpaRepository<T, String> getRepository();
}
