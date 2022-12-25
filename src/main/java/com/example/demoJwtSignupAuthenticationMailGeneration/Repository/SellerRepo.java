package com.example.demoJwtSignupAuthenticationMailGeneration.Repository;

import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.SellerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepo extends JpaRepository<SellerEntity,Long> {
    @Query(
            value = "select * from seller s where s.seller_email_id= ?1",
            nativeQuery = true
    )
    SellerEntity findByEmail(String sellerEmail);

    @Query(
            value = "select * from seller s where s.seller_first_name =:n",
            nativeQuery = true
    )
    List<SellerEntity> findByFirstName(@Param("n") String sellerFirstName);

    @Query(value = "select s.* from seller s where LOWER(s.seller_first_name) LIKE CONCAT('%',:searchKey,'%')",
            nativeQuery = true
    )
    List<SellerEntity> getSellerList(String searchKey, Pageable pageable);

}
