package com.example.pro3.DTO;

import com.example.pro3.Model.MyUser;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTOIn {
    @NotEmpty(message = "Please enter your username")
    @Size(min = 4,max = 10,message = "Enter username between 4 and 10 characters")
    private String username;
    @NotEmpty(message = "Please enter your password")
    @Size(min = 6,message = "At least 6 characters")
    private String password;
    @NotEmpty(message = "Please enter your name")
    @Size(min = 2,max = 20,message = "Please enter your name between 2 and 20 characters")
    private String name;
    @NotEmpty(message = "Please enter your email")
    @Email
    private String email;
    @NotEmpty(message = "Please enter your position")
    private String position;
    @NotNull(message = "Please enter your salary")
    @Positive
    private Double salary;

}
