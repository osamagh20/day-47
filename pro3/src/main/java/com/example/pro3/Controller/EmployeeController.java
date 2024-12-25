package com.example.pro3.Controller;
import com.example.pro3.ApiResponse.ApiResponse;
import com.example.pro3.DTO.EmployeeDTO;
import com.example.pro3.Model.Employee;
import com.example.pro3.Model.MyUser;
import com.example.pro3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/get-all-employees")
    public ResponseEntity getAllEmployees(@AuthenticationPrincipal MyUser myUser) {
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployees(myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTOList);
    }

    @PutMapping("/update")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal Employee employee, @RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(employee.getId(),employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Employee updated successfully"));
    }

}
