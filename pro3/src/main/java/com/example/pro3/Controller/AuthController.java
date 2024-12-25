package com.example.pro3.Controller;
import com.example.pro3.ApiResponse.ApiResponse;
import com.example.pro3.DTO.CustomerDTOIn;
import com.example.pro3.DTO.EmployeeDTOIn;
import com.example.pro3.Model.MyUser;
import com.example.pro3.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/get-all-users")
    public ResponseEntity getAllUsers(@AuthenticationPrincipal MyUser user) {
        List<MyUser> users =  authService.getAllUsers(user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(users);
    }

    @PostMapping("/customer-register")
    public ResponseEntity customerRegister(@RequestBody @Valid CustomerDTOIn customer) {
        authService.customerRegister(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("customer registered successfully"));
    }

    @PostMapping("/employee-register")
    public ResponseEntity employeeRegister(@RequestBody @Valid EmployeeDTOIn employee) {
        authService.employeeRegister(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("employee registered successfully"));
    }

    @PutMapping("/activate-account/{account_id}")
    public ResponseEntity activateAccount(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer account_id) {
        authService.activateAccount(myUser.getId(), account_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Account activated successfully"));
    }

    @PutMapping("/block-account/{account_id}")
    public ResponseEntity blockAccount(@AuthenticationPrincipal MyUser user,@PathVariable Integer account_id) {
        authService.blockAccount(user.getId(), account_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("account blocked successfully"));
    }

}
