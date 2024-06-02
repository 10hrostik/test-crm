package com.test.crm.repositories;

import com.test.crm.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  User findByUsername(String username);

  User findByUsernameAndEnabled(String username, boolean enabled);

  User findByUsernameAndPasswordAndEnabled(String username, String password, boolean enabled);
}
