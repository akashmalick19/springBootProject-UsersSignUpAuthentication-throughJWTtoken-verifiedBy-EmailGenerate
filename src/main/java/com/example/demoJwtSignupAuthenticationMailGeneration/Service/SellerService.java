package com.example.demoJwtSignupAuthenticationMailGeneration.Service;

import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.JwtRequest;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.RoleEntity;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.SellerEntity;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.SellerStatus;
import com.example.demoJwtSignupAuthenticationMailGeneration.Repository.RoleRepo;
import com.example.demoJwtSignupAuthenticationMailGeneration.Repository.SellerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SellerService {

    Logger log= LoggerFactory.getLogger(SellerService.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SellerRepo sellerRepo;
    @Autowired
    private RoleRepo roleRepo;

    public SellerEntity findByEmail(String email) {
        return sellerRepo.findByEmail(email);
    }

    public List<SellerEntity> showAllData() {
        return sellerRepo.findAll();
    }

    public SellerEntity userSellerDetailsSave(JwtRequest jwtRequest) {
        SellerEntity seller=new SellerEntity();
        seller.setSellerFirstName(jwtRequest.getSellerFirstName());
        seller.setSellerLastName(jwtRequest.getSellerLastName());
        seller.setSellerEmailId(jwtRequest.getSellerEmailId());
        seller.setSellerPassword(getEncodedPassword(jwtRequest.getSellerPassword()));
        seller.setStatus(SellerStatus.InActive);
        seller.setSellerAcType(jwtRequest.getSellerAcType());
        seller.setSellerCity(jwtRequest.getSellerCity());
        seller.setSellerCountry(jwtRequest.getSellerCountry());
        seller.setSellerZipcode(jwtRequest.getSellerZipcode());
        Set<RoleEntity> roleEntities=new HashSet<>();
        RoleEntity role=roleRepo.findById("User").get();
        roleEntities.add(role);
        seller.setRoleEntitySet(roleEntities);
        return sellerRepo.save(seller);
    }

    public SellerEntity adminSellerDetailsSave(JwtRequest jwtRequest) {
        SellerEntity seller=new SellerEntity();
        seller.setSellerFirstName(jwtRequest.getSellerFirstName());
        seller.setSellerLastName(jwtRequest.getSellerLastName());
        seller.setSellerEmailId(jwtRequest.getSellerEmailId());
        seller.setSellerPassword(getEncodedPassword(jwtRequest.getSellerPassword()));
        seller.setStatus(SellerStatus.InActive);
        seller.setSellerAcType(jwtRequest.getSellerAcType());
        seller.setSellerCity(jwtRequest.getSellerCity());
        seller.setSellerCountry(jwtRequest.getSellerCountry());
        seller.setSellerZipcode(jwtRequest.getSellerZipcode());
        Set<RoleEntity> roleEntities=new HashSet<>();
        RoleEntity role=roleRepo.findById("Admin").get();
        roleEntities.add(role);
        seller.setRoleEntitySet(roleEntities);
        return sellerRepo.save(seller);
    }

    public List<SellerEntity> showSpecificData(String sellerFirstName) {
        return sellerRepo.findByFirstName(sellerFirstName);
    }

    public void saveData(SellerEntity seller) {
        sellerRepo.save(seller);
    }

    public List<SellerEntity> paginationSort(String searchKey, PageRequest pageRequest) {
        return sellerRepo.getSellerList(searchKey,pageRequest);
    }

    private String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }

    public SellerEntity updateNormal(SellerStatus status, String userEmail) {
        SellerEntity seller=sellerRepo.findByEmail(userEmail);
        seller.setStatus(status);
        return sellerRepo.save(seller);
    }

    public SellerEntity updateStatusOnly(SellerStatus deActive, String sellerEmailId) {
        SellerEntity seller=sellerRepo.findByEmail(sellerEmailId);
        seller.setStatus(deActive);
        return sellerRepo.save(seller);
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void displayAllSeller() {
        List<SellerEntity> s = sellerRepo.findAll();
        System.out.println("Hello corn scheduling is start after 10 seconds: " + new Date().toString());
        System.out.println("number of records: " + s.size());
        System.out.println("Sellers important Details:");

        // Here Uses for-each loop
        for (SellerEntity list : s)
            log.info(list.getSellerFirstName() + ", " + list.getSellerLastName() + ", " + list.getSellerEmailId() + ", " + list.getSellerAcType()
                    + ", " + list.getStatus());
    }

}
