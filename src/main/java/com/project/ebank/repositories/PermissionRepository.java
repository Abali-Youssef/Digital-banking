package com.project.ebank.repositories;

import com.project.ebank.entities.Card;
import com.project.ebank.entities.Permission;
import com.project.ebank.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    Permission findByName(String name);

}
