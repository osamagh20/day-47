package com.example.pro3.Controller;

import com.example.pro3.ApiResponse.ApiResponse;
import com.example.pro3.DTO.CustomerDTO;
import com.example.pro3.DTO.CustomerDTOIn;
import com.example.pro3.Model.Customer;
import com.example.pro3.Model.MyUser;
import com.example.pro3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/get-all-customers")
    public ResponseEntity getAllCustomers(@AuthenticationPrincipal MyUser myUser){
        List<CustomerDTO> customersDTO = customerService.getAllCustomers(myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(customersDTO);
    }
    @PutMapping("/update-customer")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal MyUser myUser,@RequestBody @Valid CustomerDTOIn customerDTOIn){
        customerService.updateCustomer(myUser.getId(),customerDTOIn);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("update success"));
    }
    @PutMapping("/withdraw/{account_id}/{withdrawMoney}")
    public ResponseEntity withdrawMoney(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer account_id,@PathVariable Double withdrawMoney){
        customerService.withdrawMoney(myUser.getId(),account_id,withdrawMoney);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("withdraw success"));
    }
    @PutMapping("/deposit/{account_id}/{depositMoney}")
    public ResponseEntity depositMoney(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer account_id,@PathVariable Double depositMoney){
        customerService.depositMoney(myUser.getId(),account_id,depositMoney);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("deposit success"));
    }

    @PutMapping("/transfer/{account_id}/{transferMoney}/{toAccount_id}")
    public ResponseEntity transferMoney(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer account_id,@PathVariable Double transferMoney,@PathVariable Integer toAccount_id){
        customerService.transferMoney(myUser.getId(),account_id,transferMoney,toAccount_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("transfer success"));
    }

}
