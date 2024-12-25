package com.example.pro3.DTO;

import com.example.pro3.Model.Account;
import com.example.pro3.Model.MyUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class CustomerDTOIn {

    @NotEmpty(message = "Please enter your username")
    @Size(min = 4,max = 10,message = "Enter username between 4 and 10 characters")
    private String username;
    @NotEmpty(message = "Please enter your password")
    @Size(min = 6,message = "At least 6 characters")
    private String password;
    @NotEmpty(message = "Please enter your name")
    @Size(min = 2,max = 20,message = "Please enter your name between 2 and 20 characters")
    private String name;
    @NotEmpty(message = "Please enter your phone number")
    @Pattern(regexp = "^05\\d{8}$",message = "Start with 05 and exactly 10 digits")
    private String phoneNumber;
    @NotEmpty(message = "Please enter your email")
    @Email
    private String email;

}
