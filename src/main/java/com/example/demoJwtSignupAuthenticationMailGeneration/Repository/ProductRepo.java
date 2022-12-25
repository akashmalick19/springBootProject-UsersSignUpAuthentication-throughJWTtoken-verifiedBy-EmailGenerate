package com.example.demoJwtSignupAuthenticationMailGeneration.Repository;

import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
}
