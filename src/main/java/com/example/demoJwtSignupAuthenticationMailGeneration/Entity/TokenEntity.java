package com.example.demoJwtSignupAuthenticationMailGeneration.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "token_table")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private String token;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "sellerId")
    private SellerEntity sellerEntity;

    public TokenEntity(LocalDateTime createdAt, LocalDateTime expiredAt, String token, SellerEntity sellerEntity) {
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.token = token;
        this.sellerEntity = sellerEntity;
    }
}


