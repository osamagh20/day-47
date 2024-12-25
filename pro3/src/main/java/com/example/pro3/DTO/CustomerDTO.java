package com.example.pro3.DTO;

import com.example.pro3.Model.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private Set<Account> accountSet;
}
