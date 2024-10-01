package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.constants.RoleName;
import com.ra.projectmd5.model.entity.Role;
import com.ra.projectmd5.model.repository.IRoleRepository;
import com.ra.projectmd5.model.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;
    @Override
    public Role findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
