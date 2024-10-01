package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.constants.RoleName;
import com.ra.projectmd5.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(RoleName roleName);
}
