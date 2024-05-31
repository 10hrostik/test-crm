package com.test.crm.models.client;

import com.test.crm.models.BaseEntity;
import com.test.crm.models.task.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client extends BaseEntity implements ClientUserDetails {
  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Basic
  private String companyName;

  @Basic
  private String branch;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private ClientRole clientRole;

  @OneToMany(mappedBy = "createdBy")
  private List<Task> tasks;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(clientRole);
  }
}
