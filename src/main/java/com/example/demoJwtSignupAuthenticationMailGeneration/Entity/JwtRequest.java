package com.example.demoJwtSignupAuthenticationMailGeneration.Entity;

import lombok.Data;

@Data
public class JwtRequest {
    private String sellerFirstName;
    private String sellerLastName;
    private String sellerEmailId;
    private String sellerPassword;
    private String sellerAcType;
    private String sellerCity;
    private String sellerCountry;
    private Long sellerZipcode;

    public JwtRequest() {
    }

    public JwtRequest(String sellerFirstName, String sellerLastName, String sellerEmailId, String sellerPassword,
                      String sellerAcType, String sellerCity, String sellerCountry, Long sellerZipcode) {

        this.sellerFirstName = sellerFirstName;
        this.sellerLastName = sellerLastName;
        this.sellerEmailId = sellerEmailId;
        this.sellerPassword = sellerPassword;
        this.sellerAcType = sellerAcType;
        this.sellerCity = sellerCity;
        this.sellerCountry = sellerCountry;
        this.sellerZipcode = sellerZipcode;
    }

}
