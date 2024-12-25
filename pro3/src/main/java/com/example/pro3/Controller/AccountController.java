package com.example.pro3.Controller;

import com.example.pro3.ApiResponse.ApiResponse;
import com.example.pro3.DTO.AccountDTO;
import com.example.pro3.Model.Account;
import com.example.pro3.Model.Customer;
import com.example.pro3.Model.MyUser;
import com.example.pro3.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/add-account")
    public ResponseEntity addAccount(@AuthenticationPrincipal MyUser myUser, @RequestBody @Valid Account account) {
        accountService.addAccount(myUser.getId(), account);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Account created successfully"));
    }

    @PutMapping("/update-account")
    public ResponseEntity updateAccount(@AuthenticationPrincipal MyUser myUser, @RequestBody @Valid Account account) {
        accountService.updateAccount(myUser.getId(), account);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Account updated successfully"));
    }
    @DeleteMapping("/delete-account")
    public ResponseEntity deleteAccount(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer account_id) {
        accountService.deleteAccount(myUser.getId(), account_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Account deleted successfully"));
    }

    @GetMapping("/get-account-details/{account_id}")
    public ResponseEntity accountDetails(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer account_id) {
        AccountDTO accountDTO = accountService.accountDetails(myUser.getId(),account_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDTO);
    }

    @GetMapping("/get-all-accounts")
    public ResponseEntity getAccounts(@AuthenticationPrincipal MyUser myUser) {
        List<AccountDTO> accountsDTO = accountService.getAccounts(myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(accountsDTO);
    }

}
