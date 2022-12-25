package com.example.demoJwtSignupAuthenticationMailGeneration.Controller;

import com.example.demoJwtSignupAuthenticationMailGeneration.Configuration.JwtRequestFilter;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.ProductEntity;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.Response;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.SellerEntity;
import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.SellerStatus;
import com.example.demoJwtSignupAuthenticationMailGeneration.Exception.UserException;
import com.example.demoJwtSignupAuthenticationMailGeneration.Repository.ProductRepo;
import com.example.demoJwtSignupAuthenticationMailGeneration.Service.SellerService;
import com.example.demoJwtSignupAuthenticationMailGeneration.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Profile(value = {"local","prod","local"})
public class SellerController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ProductRepo productRepo;


    @GetMapping({"/showAllData"}) //for all data shows by Admin only
    @PreAuthorize("hasRole('Admin')")
    public Response showAllData() {
        Response response =new Response();
        Iterable<SellerEntity> sellers= sellerService.showAllData();
        try{
            if(sellers==null){
                throw new UserException("No data are found. Data Base is Empty.");
            }
            response.setStatus(HttpStatus.FOUND);
            response.setMessage("Here All Data are found.");
            response.setObject(sellers);
            return response;
        }
        catch (UserException e){
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage(e.toString());
            response.setObject(sellers);
            return response;
        }
    }


    @GetMapping("/showSpecificData/{sellerFirstName}") //for specific data shows by Admin only
    @PreAuthorize("hasRole('Admin')")
    public Response showSpecificData(@PathVariable("sellerFirstName") String sellerFirstName) {
        List<SellerEntity> seller = sellerService.showSpecificData(sellerFirstName);
        Response response=new Response();
        try{
            if(seller.isEmpty()){
                throw new UserException(sellerFirstName +" is not found inside Data Base.");
            }
            response.setStatus(HttpStatus.FOUND);
            response.setMessage(sellerFirstName +" record is found inside Data Base");
            response.setObject(seller);
            return response;
        }
        catch (UserException e){
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage(e.toString());
            response.setObject(seller);
            return response;
        }
    }

    @PostMapping("/productStore")
    public Response productStore(HttpServletRequest request, @RequestBody List<ProductEntity> product) {
        Response response = new Response();
        SellerEntity seller = new SellerEntity();

        //these codes for jwt token validation checking
        String jwt = jwtRequestFilter.parseJwt(request);
        String userEmail = null;
        if (jwt == null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("No Token Provided.");
            return response;
        }
        if (jwt!=null) {
            userEmail = jwtUtil.getSellerEmailFromToken(jwt);
            seller = sellerService.findByEmail(userEmail);

            //here checks the both email and seller-status of seller
            if (seller.getSellerEmailId().equals(userEmail) && seller.getStatus().equals(SellerStatus.Active)) {
                seller.setProductEntityList(product); //here all products are stored by the seller object
                sellerService.saveData(seller);

                for(ProductEntity product1 : product) {
                    int netPrice=calProductNetPrice(product1.getProductPrice(),product1.getProductDiscount());
                    product1.setProductNetPrice(netPrice);
                    productRepo.save(product1);
                }

                response.setStatus(HttpStatus.OK);
                response.setMessage("All Products are stored inside Product Data Base.");
                response.setObject(seller);
                return response;
            }
        }
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Product does not store because of either wrong EmailId authentication or sellerStatus is DeActivate Or InActive of this particular Seller.");
        return response;
    }

    private int calProductNetPrice(int productPrice, int productDiscount){
        int net=productPrice * productDiscount / 100;
        int netPrice = productPrice-net;
        return netPrice;
    }

    @PutMapping("/updateStatus")
    public Response updateNormal(HttpServletRequest request, @RequestBody SellerEntity sellerEntity) {
        Response response = new Response();
        SellerEntity seller = new SellerEntity();
        //these codes for jwt token validation checking
        String jwt = jwtRequestFilter.parseJwt(request);
        String userEmail = null;
        if (jwt == null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("No Token Provided.");
            return response;
        }
        else {
            userEmail = jwtUtil.getSellerEmailFromToken(jwt);
            if (userEmail.equals(sellerEntity.getSellerEmailId())) {
                SellerEntity seller1 = sellerService.updateNormal(sellerEntity.getStatus(),userEmail);
                response.setStatus(HttpStatus.OK);
                response.setMessage("Seller Status is changed.");
                response.setObject(seller1);
                return response;
            }
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage("Seller Status can't change due to wrong user Authentication.");
            return response;
        }

    }

    /* This method only does Status- DeActive */
    @PutMapping("/updateStatusDeActivate")
    public Response updateStatusOnlyDeActivate(HttpServletRequest request, @RequestBody SellerEntity sellerEmail) {
        Response response = new Response();
        SellerEntity seller = new SellerEntity();

        //These codes for jwt token validation checking
        String jwt = jwtRequestFilter.parseJwt(request);
        String userEmail = null;
        if (jwt == null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("No Token Provided.");
            return response;
        }
        userEmail = jwtUtil.getSellerEmailFromToken(jwt);
        try{
            seller=sellerService.findByEmail(sellerEmail.getSellerEmailId());
            if(seller==null) {
                throw new NullPointerException();
            }
            if (userEmail.equals(seller.getSellerEmailId())) {
                if(seller.getStatus().equals(SellerStatus.Active)){
                    SellerEntity seller1=sellerService.updateStatusOnly(SellerStatus.DeActive,seller.getSellerEmailId());
                    response.setStatus(HttpStatus.OK);
                    response.setMessage("Seller Status is changed.");
                    response.setObject(seller1);
                    return response;
                }
                response.setStatus(HttpStatus.NOT_MODIFIED);
                response.setMessage("Seller Status has Already DeActive Or InActive.");
                return response;
            }
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage("Seller Status can't change due to wrong user Authentication.");
            return response;
        }
        catch (NullPointerException e){
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage("Seller Status can't change due to put wrong email as input.");
            return response;
        }

    }

    @GetMapping("/paginationSortQuery")
    public ResponseEntity<?> findAllByPaginationUsingQuery(@RequestParam(value = "pageSize") int pageSize, //page size
                                                           @RequestParam(value = "pageNumber") int pageNumber, //page number
                                                           @RequestParam(value = "order_by_column", required = false, defaultValue = "sellerId") String orderByColumn,
                                                           @RequestParam(value = "sort_desc", required = false, defaultValue = "false") boolean sortDesc,
                                                           @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey){

        Map<String, String> map = new HashMap<String, String>();
        String s = map.getOrDefault(orderByColumn, orderByColumn); //for sort data asc or desc

        Sort sort = Sort.by(s);
        if (sortDesc) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        pageNumber = pageNumber <= 0 ? 1 : pageNumber;
        pageSize = pageSize == 0 ? 5 : pageSize;

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sort);
        List<SellerEntity> sellerList=sellerService.paginationSort(searchKey,pageRequest);
        Response response=new Response();
        try{
            if(sellerList.isEmpty())
            {
                throw new NoSuchFieldException("No such sorted and pageable data are exist in this page.");
            }
            else{

                return ResponseEntity.ok(new Response(HttpStatus.OK,"sorted pageable Seller list is found.",sellerList));
            }
        }
        catch (NoSuchFieldException e)
        {
            return ResponseEntity.ok(new Response(HttpStatus.NOT_FOUND,e.toString(),sellerList));
        }
    }

    /* setUp Corn Scheduler */
    public void schedule(){
        sellerService.displayAllSeller();
    }

}

