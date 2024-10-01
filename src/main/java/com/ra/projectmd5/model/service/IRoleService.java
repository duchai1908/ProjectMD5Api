package com.ra.projectmd5.model.service;

import com.ra.projectmd5.constants.RoleName;
import com.ra.projectmd5.model.entity.Role;

public interface IRoleService {
    Role findByRoleName(RoleName roleName);
}
