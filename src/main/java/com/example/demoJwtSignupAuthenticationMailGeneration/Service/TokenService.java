package com.example.demoJwtSignupAuthenticationMailGeneration.Service;

import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.SellerEntity;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.TokenEntity;
import com.example.demoJwtSignupAuthenticationMailGeneration.Repository.TokenRepo;
import com.example.demoJwtSignupAuthenticationMailGeneration.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenService {
    @Autowired
    private TokenRepo tokenRepo;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private JwtUtil jwtUtil;


    public void tokenDetailsSave(String token) {
        String email=jwtUtil.getSellerEmailFromToken(token);
        SellerEntity sellerEntity=sellerService.findByEmail(email);

        TokenEntity tokenEntity=new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setCreatedAt(LocalDateTime.now());
        tokenEntity.setExpiredAt(LocalDateTime.now().plusMinutes(2));

        SellerEntity sellerEntity1=new SellerEntity();
        sellerEntity1.setSellerId(sellerEntity.getSellerId());
        tokenEntity.setSellerEntity(sellerEntity1);
        tokenRepo.save(tokenEntity);
    }

}
