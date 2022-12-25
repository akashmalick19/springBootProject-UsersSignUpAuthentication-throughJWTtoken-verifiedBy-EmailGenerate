package com.example.demoJwtSignupAuthenticationMailGeneration.Entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @NotNull
    private Integer productQuantity;
    @NotNull
    private String productName;
    @NotNull
    private Integer productPrice;
    private int productDiscount;
    @NotNull
    private int productNetPrice;

    public ProductEntity(Integer productQuantity, String productName, Integer productPrice,
                         int productDiscount, int productNetPrice) {

        this.productQuantity = productQuantity;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productNetPrice = productNetPrice;
    }
}
