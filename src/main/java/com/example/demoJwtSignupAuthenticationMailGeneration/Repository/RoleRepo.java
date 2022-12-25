package com.example.demoJwtSignupAuthenticationMailGeneration.Repository;

import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity,String> {
}
