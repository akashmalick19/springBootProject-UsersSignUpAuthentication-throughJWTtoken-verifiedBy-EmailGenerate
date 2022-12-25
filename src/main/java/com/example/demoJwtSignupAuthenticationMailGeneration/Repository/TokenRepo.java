package com.example.demoJwtSignupAuthenticationMailGeneration.Repository;

import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<TokenEntity,Long> {
    @Query(value = "select * from token_entity t where t.token= ?1",
            nativeQuery = true
    )
    TokenEntity findByToken(String token);
}
