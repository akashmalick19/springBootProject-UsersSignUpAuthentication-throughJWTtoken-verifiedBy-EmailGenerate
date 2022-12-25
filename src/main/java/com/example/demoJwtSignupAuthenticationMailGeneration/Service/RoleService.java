package com.example.demoJwtSignupAuthenticationMailGeneration.Service;

import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.RoleEntity;
import com.example.demoJwtSignupAuthenticationMailGeneration.Repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public RoleEntity createNewRole(RoleEntity role) {
        return roleRepo.save(role);
    }
}
