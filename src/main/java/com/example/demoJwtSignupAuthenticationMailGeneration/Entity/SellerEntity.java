package com.example.demoJwtSignupAuthenticationMailGeneration.Entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "seller")
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sellerId;
    @NotNull
    private String sellerFirstName;
    @NotNull
    private String sellerLastName;
    @NotNull
    private String sellerPassword;
    @NotNull
    private String sellerEmailId;
    @Enumerated(EnumType.STRING)
    private  SellerStatus status;

    private String sellerAcType;
    private String sellerCity;
    private String sellerCountry;
    private Long sellerZipcode;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "sellers_roles",
            joinColumns = @JoinColumn(name = "seller_id"),
            inverseJoinColumns = @JoinColumn(name = "role_name_id")
    )
    private Set<RoleEntity> roleEntitySet;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id",referencedColumnName = "sellerId")
    private List<ProductEntity> productEntityList;


    public SellerEntity(String sellerFirstName, String sellerLastName, String sellerPassword,
                        String sellerEmailId, SellerStatus status, String sellerAcType,
                        String sellerCity, String sellerCountry,
                        Long sellerZipcode, Set<RoleEntity> roleEntitySet,
                        List<ProductEntity> productEntityList) {

        this.sellerFirstName = sellerFirstName;
        this.sellerLastName = sellerLastName;
        this.sellerPassword = sellerPassword;
        this.sellerEmailId = sellerEmailId;
        this.status = status;
        this.sellerAcType = sellerAcType;
        this.sellerCity = sellerCity;
        this.sellerCountry = sellerCountry;
        this.sellerZipcode = sellerZipcode;
        this.roleEntitySet = roleEntitySet;
        this.productEntityList = productEntityList;
    }
}
