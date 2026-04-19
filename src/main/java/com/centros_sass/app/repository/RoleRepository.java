package com.centros_sass.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centros_sass.app.model.catalogs.fixed.organization.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(String roleName);

    boolean existsByRoleName(String roleName);

    List<Role> findAllByOrderByRoleNameAsc();

}
