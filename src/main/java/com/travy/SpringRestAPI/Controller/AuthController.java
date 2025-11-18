package com.travy.SpringRestAPI.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.travy.SpringRestAPI.Model.Account;
import com.travy.SpringRestAPI.Payload.Auth.AccountDTO;
import com.travy.SpringRestAPI.Payload.Auth.AuthoritiesDTO;
import com.travy.SpringRestAPI.Payload.Auth.PasswordDTO;
import com.travy.SpringRestAPI.Payload.Auth.ProfileDTO;
import com.travy.SpringRestAPI.Payload.Auth.TokenDTO;
import com.travy.SpringRestAPI.Payload.Auth.UserloginDTO;
import com.travy.SpringRestAPI.Payload.Auth.Album.AccountViewDTO;
import com.travy.SpringRestAPI.Service.AccountService;
import com.travy.SpringRestAPI.Service.TokenService;
import com.travy.SpringRestAPI.Util.Constraints.AccountError;
import com.travy.SpringRestAPI.Util.Constraints.AccountSuccess;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("api/v1/Auth")
@Tag(name = "Auth Controller", description = "Controller For Account Management")
@Slf4j
public class AuthController {

    @Autowired
    private AccountService accountService;

     @Autowired
    private AuthenticationManager authenticationManager;
 
     @Autowired
    private TokenService tokenService;

    
@PostMapping("/token")
@ResponseStatus(HttpStatus.OK)
public ResponseEntity<TokenDTO>token(@Valid  @RequestBody UserloginDTO userlogin) throws AuthenticationException{

    try {
         Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(userlogin.getEmail(), userlogin.getPassword()));
         return ResponseEntity.ok(new TokenDTO(tokenService.generateToken(authentication)));

    } catch (Exception e) {

        log.debug(AccountError.TOKEN_GENERATION_ERROR.toString() + ":" + e.getMessage());
        return new ResponseEntity<>(new TokenDTO (null), HttpStatus.BAD_REQUEST);
      
    }
   
    
}

@PostMapping("/users/add")
@ResponseStatus(HttpStatus.CREATED)
@Operation(summary = "Add a new user")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "please enter a valid email and password"),
    @ApiResponse(responseCode = "401", description = "Unauthorized")
})

public ResponseEntity <String> addUser(@RequestBody AccountDTO accountDTO){

    try {
             Account account = new Account();
             account.setEmail(accountDTO.getEmail());
             account.setPassword(accountDTO.getPassword());
             account.setAuthorities("ROLE_USER");
             accountService.Save(account);
             return ResponseEntity.ok(AccountSuccess.ACCOUNT_ADDED.toString());



    } catch (Exception e) {
        
        log.debug(AccountError.ADD_ACCOUNT_ERROR.toString() + ":" + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }


}

@GetMapping(value = "/users", produces = "application/json")
@Operation(summary = "list users api")
@ApiResponses(value = {
   @ApiResponse(responseCode = "401", description = "Token Missing"),

   @ApiResponse(responseCode = "403", description = "Token Error")
})
@SecurityRequirement(name = "Tomiwa-Demo-API")

public List <AccountViewDTO> Users(){

    List<AccountViewDTO> accounts = new ArrayList<>();

    for(Account account : accountService.findAll()){

        accounts.add(new AccountViewDTO(account.getId(), account.getEmail(), account.getAuthorities()));

    }

    return accounts;

    
}

@GetMapping(value="/profile", produces = "application/json")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "list of users"),
    @ApiResponse(responseCode = "401", description = "Token missing"),
    @ApiResponse(responseCode = "403", description = "Token Error")
})
@Operation(summary = "view profile")
@SecurityRequirement(name = "Tomiwa-Demo-API")
public ProfileDTO profile(Authentication authentication){

    String email = authentication.getName();
    Optional<Account>optionalAccount = accountService.findByEmail(email);

    if(optionalAccount.isPresent()){

        Account account = optionalAccount.get();
        ProfileDTO profileDTO = new ProfileDTO(account.getId(), account.getEmail(), account.getAuthorities());
        return profileDTO;

    }
    return null;

}



@PutMapping(value="/profile/update_password", produces = "application/json", consumes = "application/json")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "list of users"),
    @ApiResponse(responseCode = "401", description = "Token missing"),
    @ApiResponse(responseCode = "403", description = "Token Error")
})
@Operation(summary = "update password")
@SecurityRequirement(name = "Tomiwa-Demo-API")
public AccountViewDTO update_password(@Valid @RequestBody AccountDTO accountDTO, PasswordDTO passwordDTO, Authentication authentication){

    String email = authentication.getName();

    Optional<Account>optionalAccount = accountService.findByEmail(email);

    if(optionalAccount.isPresent()){

        Account account = optionalAccount.get();
        account.setPassword(passwordDTO.getPassword());
        accountService.Save(account);
        AccountViewDTO accountViewDTO = new AccountViewDTO(account.getId(), account.getEmail(), account.getAuthorities());
        return accountViewDTO;


    }

    return null;
   
}

    
@PutMapping(value="/users/{user_id}/update_auth", produces = "application/json", consumes = "application/json")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "list of users"),
    @ApiResponse(responseCode = "401", description = "Token missing"),
    @ApiResponse(responseCode = "403", description = "Token Error"),
    @ApiResponse(responseCode = "400", description = "Invalid user")
})
@Operation(summary = "update_authorities")
@SecurityRequirement(name = "Tomiwa-Demo-API")
public ResponseEntity <AccountViewDTO> update_auth(@Valid @RequestBody AuthoritiesDTO authoritiesDTO, @PathVariable Long user_id){

    Optional<Account>optionalAccount = accountService.findById(user_id);

    if(optionalAccount.isPresent()){

        Account account = optionalAccount.get();
       account.setAuthorities(authoritiesDTO.getAuthorities());
       accountService.Save(account); 
       AccountViewDTO accountViewDTO = new AccountViewDTO(account.getId(), account.getEmail(), account.getAuthorities());
       return ResponseEntity.ok (accountViewDTO);
       
       


    }
 AccountViewDTO errorDto = new AccountViewDTO();
 return ResponseEntity.badRequest().body(errorDto);
   
}

@DeleteMapping("/profile/delete_profile")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "list of users"),
    @ApiResponse(responseCode = "401", description = "Token missing"),
    @ApiResponse(responseCode = "403", description = "Token Error")
})
@Operation(summary = "Delete Profile")
@SecurityRequirement(name = "Tomiwa-Demo-API")
public ResponseEntity<String>   delete_profile(Authentication authentication){

    String email = authentication.getName();

    Optional<Account>optionalaccount = accountService.findByEmail(email);

        if(optionalaccount.isPresent()){

            accountService.delete(optionalaccount.get().getId());

            return ResponseEntity.ok("User Deleted");

        }

        return ResponseEntity.badRequest().body("BAD REQUEST");
   
}


  

}
