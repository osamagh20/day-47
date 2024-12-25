package com.example.pro3.Service;

import com.example.pro3.ApiResponse.ApiException;
import com.example.pro3.DTO.EmployeeDTO;
import com.example.pro3.Model.Employee;
import com.example.pro3.Model.MyUser;
import com.example.pro3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.pro3.Repository.AuthRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    public List<EmployeeDTO> getAllEmployees(Integer user_id) {
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");
        }
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("user is not admin");
        }
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new ApiException("not have any employees");
        }
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setUsername(employee.getUser().getUsername());
            employeeDTO.setPassword(employee.getUser().getPassword());
            employeeDTO.setName(employee.getUser().getName());
            employeeDTO.setEmail(employee.getUser().getEmail());
            employeeDTO.setPosition(employee.getPosition());
            employeeDTO.setSalary(employee.getSalary());
            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }

    public void updateEmployee(Integer emp_id,EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findEmployeeById(emp_id);
        if (employee == null) {
            throw new ApiException("employee not found");
        }
        employee.getUser().setUsername(employeeDTO.getUsername());
        employee.getUser().setPassword(employeeDTO.getPassword());
        employee.getUser().setName(employeeDTO.getName());
        employee.getUser().setEmail(employeeDTO.getEmail());
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employeeRepository.save(employee);
    }
}
