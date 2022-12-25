# springBootProject-UsersSignUpAuthentication-throughJWTtoken-verifiedBy-EmailGenerate

# springBootProject-UsersSignUpAuthentication-throughJWTtoken-verifiedBy-EmailGenerate
ProjectDescriptions:  
		      This project is Spring Boot Security Base project.Here uses JWT (Json Web Token) security for Authorization and Authentication purpose.
		      Here also JWT is generated for SignUp Or register new Normal_user and Admin_User, and Email generate feature is added for Users who are existing inside Data base.
		      Here I create many operations of CURD APIs for fetch data, store data, updating data, inserting data and manipulating of data into the Data Base.
		      In this project there are four Tables i.e Seller table,Role Table,Product Table and Token Table. Among these tables having ONE TO MANY
		      and MANY TO MANY and MANY TO ONE mapping relationship. And also uses many Spring features like CORN SCHEDULING, ENVIRONMENT PROPERTIES feature etc.
		      In this project I use MySQL Woekbench for Data Base and Testing purpose uses Postman Applications.
		      Initially when SignUp a Seller and sellers details is save inside Seller Table and also here create JWT token and its verify through mail service,after that this token is stored inside Token Table.
		      Also when Do any CURD operations on existing Data at that time need for JWT security Authorization and Authentication and at the same time this Token is verified through mail service.
		
		Corn Scheduling:- 
		   One method ( public void displayAllSeller(){} ) is used to repeatedly show entier seller's details by using Corn Scheduling features.  

	APIs Used :- 
		1.  @PostMapping({"/createNewRole"}) : create new Roles of Role Table.

		2.  @PostMapping({"/authenticate"}) : If users is exist inside the data base then authorized that user and also generated Jwt Token for that particular user by generating the mail to the particular user.
		
		3.  @GetMapping("/verifyToken") : Email Verification of JWT token is done by this API call.

		4.  @PostMapping("/signUpUser") : Any new Users(seller) are signUp or Register inside the Seller Data Base with some proper conditions checking and it also should be verified through mail service.

		5.  @PostMapping("/signUpAdmin") : Any new Admin-Users(seller) are signUp or Register inside the Seller Data Base without any condition checking and it also should be verified through mail service.
		
		6.  @GetMapping({"/showAllData"}) : All records are show or fetch from all the Data Base ( Seller,Role,Product) Tables. Its Only done by Admin_Users.
		
		7.  @GetMapping("/showSpecificData/{sellerFirstName}") : Here passes the specific Seller-Name as a parametter with the Apis, if the specified Seller-Name is exist inside the seller Data Base then it will be show.Its Only done by Admin_Users.

		8.  @PutMapping("/updateStatus") : Seller-Status will be update of specific seller if seller-email is matched with given seller-email's .
		
		9.  @PutMapping("/updateStatusDeActivate") :  Seller-Status will be update of specific seller if seller-email is matched with given seller-email's and Seller-Status will be update when Seller-Status is Active.
		
		10. @GetMapping("/paginationSortQuery") : By this Apis all manipulating data from the Data Base(Seller,Product,Role) are show or fetch. It sorts all the data from the data base and show the data page wise.

		11. @PostMapping("/productStore") : For a specified seller all Products are store into the Product table. Seller, Who want to store the products needs to be authorized by the particular seller whose status is to be Active.

		
