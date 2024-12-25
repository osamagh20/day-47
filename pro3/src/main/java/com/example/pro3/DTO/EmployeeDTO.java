package com.example.pro3.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private String position;
    private Double salary;
}
