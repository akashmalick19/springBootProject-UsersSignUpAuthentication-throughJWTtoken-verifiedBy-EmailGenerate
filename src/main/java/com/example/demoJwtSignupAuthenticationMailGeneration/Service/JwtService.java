package com.example.demoJwtSignupAuthenticationMailGeneration.Service;

import com.example.demoJwtSignupAuthenticationMailGeneration.EmailConfiguration.EmailService;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.JwtRequest;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.JwtResponse;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.SellerEntity;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.SellerStatus;
import com.example.demoJwtSignupAuthenticationMailGeneration.Repository.SellerRepo;
import com.example.demoJwtSignupAuthenticationMailGeneration.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private SellerRepo sellerRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public Map<String,Object> signUpProcessUserWithJwtToken(JwtRequest jwtRequest) throws Exception {
        Map<String,Object> map=new HashMap<>();
        String sellerEmail=jwtRequest.getSellerEmailId();
        String sellerPassword=jwtRequest.getSellerPassword();
        String token=null;
        String link=null;
        if(StringUtils.hasText(sellerEmail)){
            try{
                SellerEntity seller1=sellerService.findByEmail(sellerEmail);
                if(seller1.getSellerEmailId().equals(sellerEmail)){
                    map.put("Status: ", HttpStatus.ALREADY_REPORTED);
                    map.put("Message: ","Same email has already signUp.");
                    map.put("Email_Verification: "," Email Verification does not successful.");
                    return map;
                }
                throw new NullPointerException();
            }
            catch (NullPointerException e){
                if(jwtRequest.getSellerPassword().isEmpty()){
                    map.put("Status: ", HttpStatus.BAD_REQUEST);
                    map.put("Message: ","Seller password Should not be Null.");
                    map.put("Email_Verification: "," Email Verification does not successful.");
                    return map;
                }
                if(jwtRequest.getSellerFirstName().isEmpty() || jwtRequest.getSellerFirstName()==null){
                    map.put("Status: ", HttpStatus.BAD_REQUEST);
                    map.put("Message: ","Seller First Name Should not be Null.");
                    map.put("Email_Verification: "," Email Verification does not successful.");
                    return map;
                }
                if(jwtRequest.getSellerLastName().isEmpty() || jwtRequest.getSellerLastName()==null){
                    map.put("Status: ", HttpStatus.BAD_REQUEST);
                    map.put("Message: ","Seller Last Name Should not be Null.");
                    map.put("Email_Verification: "," Email Verification does not successful.");
                    return map;
                }
                SellerEntity sellerEntity=sellerService.userSellerDetailsSave(jwtRequest);
                authenticate(sellerEmail,sellerPassword);
                UserDetails userDetails=loadUserByUsername(sellerEmail);
                token=jwtUtil.generateToken(userDetails);
                /* for token table records insert */
                //TokenEntity tokenEntity=tokenService.tokenDetailsSave(token);
                link="http://localhost:9000/verifyToken?token=" + token;

                emailService.send(sellerEntity.getSellerEmailId(),
                        "hi, "+ sellerEntity.getSellerFirstName()+" "+sellerEntity.getSellerLastName()+"\n"
                                +"Click this below link for signUp new User.\n"+ link,
                        "VERIFICATION_LINK ");

                map.put("Status: ", HttpStatus.ACCEPTED);
                map.put("Message: ","Seller Records are stored inside seller table.");
                map.put("Token: ",token);
                return map;
            }
        }
        map.put("Status: ", HttpStatus.BAD_REQUEST);
        map.put("Message: ","Seller Email field Should not be Empty.");
        map.put("Email_Verification: "," Email Verification does not successful.");
        return map;
    }

    public Map<String, Object> signUpProcessAdminWithJwtToken(JwtRequest jwtRequest) throws Exception {
        Map<String,Object> map=new HashMap<>();
        String sellerEmail=jwtRequest.getSellerEmailId();
        String sellerPassword=jwtRequest.getSellerPassword();
        String token=null;
        String link=null;
        if(StringUtils.hasText(sellerEmail)){
            try{
                SellerEntity seller1=sellerService.findByEmail(sellerEmail);
                if(seller1.getSellerEmailId().equals(sellerEmail)){
                    map.put("Status: ", HttpStatus.ALREADY_REPORTED);
                    map.put("Message: ","Same email has already signUp.");
                    map.put("Email_Verification: "," Email Verification does not successful.");
                    return map;
                }
                throw new NullPointerException();
            }
            catch (NullPointerException e){
                if(jwtRequest.getSellerPassword().isEmpty()){
                    map.put("Status: ", HttpStatus.BAD_REQUEST);
                    map.put("Message: ","Seller password Should not be Null.");
                    map.put("Email_Verification: "," Email Verification does not successful.");
                    return map;
                }
                if(jwtRequest.getSellerFirstName().isEmpty() || jwtRequest.getSellerFirstName()==null){
                    map.put("Status: ", HttpStatus.BAD_REQUEST);
                    map.put("Message: ","Seller First Name Should not be Null.");
                    map.put("Email_Verification: "," Email Verification does not successful.");
                    return map;
                }
                if(jwtRequest.getSellerLastName().isEmpty() || jwtRequest.getSellerLastName()==null){
                    map.put("Status: ", HttpStatus.BAD_REQUEST);
                    map.put("Message: ","Seller Last Name Should not be Null.");
                    map.put("Email_Verification: "," Email Verification does not successful.");
                    return map;
                }
                SellerEntity sellerEntity=sellerService.adminSellerDetailsSave(jwtRequest);
                authenticate(sellerEmail,sellerPassword);
                UserDetails userDetails=loadUserByUsername(sellerEmail);
                token=jwtUtil.generateToken(userDetails);
                link="http://localhost:9000/verifyToken?token=" + token;
                emailService.send(sellerEntity.getSellerEmailId(),
                        "hi, "+ sellerEntity.getSellerFirstName()+" "+sellerEntity.getSellerLastName()+"\n"
                                +"Click this below link for signUp new Admin.\n"+ link,
                        "VERIFICATION_LINK ");

                map.put("Status: ", HttpStatus.ACCEPTED);
                map.put("Message: ","Seller Records are stored inside seller table.");
                map.put("Token: ",token);
                return map;
            }
        }
        map.put("Status: ", HttpStatus.BAD_REQUEST);
        map.put("Message: ","Seller Email field Should not be Empty.");
        map.put("Email_Verification: "," Email Verification does not successful.");
        return map;
    }

    @Transactional
    public String tokenVerification(String token) {
        String email=jwtUtil.getSellerEmailFromToken(token);
        SellerEntity seller=sellerService.findByEmail(email);
        // TokenEntity token1=tokenRepo.findByToken(token);
//        try{
//            if(token1.getExpiredAt().isBefore(LocalDateTime.now())){
//                throw new IllegalStateException();
//            }
//        }
//        catch(IllegalStateException e){
//            return "Token was Expired.";
//        }
        if(seller.getStatus().equals(SellerStatus.InActive)){
            seller.setStatus(SellerStatus.Active);
            sellerRepo.save(seller);
            /* for token table records insert */
            tokenService.tokenDetailsSave(token);
            return "Email verification is Confirmed and Token records are stored inside Token table.";
        }
        return "Email Already Verified.";
    }

    @Override
    public UserDetails loadUserByUsername(String sellerEmail) throws UsernameNotFoundException {
        SellerEntity seller = sellerService.findByEmail(sellerEmail);

        if (seller != null) {
            return new org.springframework.security.core.userdetails.User(
                    seller.getSellerEmailId(),
                    seller .getSellerPassword(),
                    //new ArrayList<>()
                    getAuthority(seller)
            );
        } else {
            throw new UsernameNotFoundException("User not found with userEmail: " + sellerEmail);
        }
    }

    private Set getAuthority(SellerEntity seller) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        seller.getRoleEntitySet().forEach( roleEntity-> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleEntity.getRoleNameId()));
        });
        return authorities;
    }

    private void authenticate(String sellerEmail, String sellerPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sellerEmail, sellerPassword ));
        }
        catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    /*This method creates the authentication token */
    public JwtResponse createJwtAuthenticationToken(JwtRequest jwtRequest) throws Exception {
        String sellerEmail = jwtRequest.getSellerEmailId();
        String sellerPassword = jwtRequest.getSellerPassword();
        authenticate(sellerEmail, sellerPassword);

        UserDetails userDetails= loadUserByUsername(sellerEmail);
        String token= jwtUtil.generateToken(userDetails);
        SellerEntity seller=sellerService.findByEmail(sellerEmail);
        // 1st line for send mail to the mail address
        emailService.send(/*"akashmailck2022@gmail.com",*/ seller.getSellerEmailId(),
                "hi " + seller.getSellerFirstName()+",\n"
                        + "This is Your Authorization_Token :\n" +"\t"+ token + "\n"
                        + "\n Thank You.\n",
                "Confirmation Of Authentication Token."
        );
        tokenService.tokenDetailsSave(token);
        return new JwtResponse(token,seller);
    }

}

