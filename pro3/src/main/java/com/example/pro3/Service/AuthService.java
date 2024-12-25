package com.example.pro3.Service;
import com.example.pro3.ApiResponse.ApiException;
import com.example.pro3.DTO.CustomerDTOIn;
import com.example.pro3.DTO.EmployeeDTOIn;
import com.example.pro3.Model.Account;
import com.example.pro3.Model.Customer;
import com.example.pro3.Model.Employee;
import com.example.pro3.Model.MyUser;
import com.example.pro3.Repository.AccountRepository;
import com.example.pro3.Repository.AuthRepository;
import com.example.pro3.Repository.CustomerRepository;
import com.example.pro3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public List<MyUser> getAllUsers(Integer user_id) {
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");
        }
        if (user.getRole().equals("ADMIN")) {
            return authRepository.findAll();
        }
        throw new ApiException("user not admin");
    }


    // particular register for customer
    public void customerRegister(CustomerDTOIn customerDTO) {
        List<Customer> customer = customerRepository.findAll();
        for (Customer c : customer) {
            if (c.getUser().getUsername().equals(customerDTO.getUsername())) {
                throw new ApiException("user already registered");
            }
        }
        MyUser myUser = new MyUser();
        myUser.setId(null);
        myUser.setUsername(customerDTO.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        myUser.setPassword(hashPassword);
        myUser.setName(customerDTO.getName());
        myUser.setEmail(customerDTO.getEmail());
        myUser.setRole("CUSTOMER");
        authRepository.save(myUser);

        Customer newCustomer = new Customer();
        newCustomer.setUser(myUser);
        newCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        customerRepository.save(newCustomer);
    }

    // particular register for Employee
    public void employeeRegister(EmployeeDTOIn employeeDTO) {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee e : employees) {
            if (e.getPosition().equals(employeeDTO.getPosition())) {
                throw new ApiException("employee already registered");
            }
        }

        MyUser myUser = new MyUser();
        myUser.setUsername(employeeDTO.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        myUser.setPassword(hashPassword);
        myUser.setName(employeeDTO.getName());
        myUser.setEmail(employeeDTO.getEmail());
        myUser.setRole("EMPLOYEE");
        authRepository.save(myUser);

        Employee employee = new Employee();
        employee.setUser(myUser);
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employeeRepository.save(employee);
    }

    // activate customer account by admin
    public void activateAccount(Integer user_id,Integer account_id) {
        MyUser myUser = authRepository.findMyUserById(user_id);
        if (myUser == null) {
            throw new ApiException("user not found");
        }
        if (myUser.getRole().equals("ADMIN")) {
            throw new ApiException("user not admin");
        }
        Account account = accountRepository.findAccountById(account_id);
        if (account == null) {
            throw new ApiException("account not found");
        }
        account.setIsActive(true);
        accountRepository.save(account);
    }

    public void blockAccount(Integer user_id, Integer account_id) {
        MyUser myUser = authRepository.findMyUserById(user_id);
        if (myUser == null) {
            throw new ApiException("user not found");
        }
        Account account = accountRepository.findAccountById(account_id);
        if (account == null) {
            throw new ApiException("account not found");
        }
        if (myUser.getRole().equals("ADMIN")) {
            if (account.getIsActive().equals(true)){
                account.setIsActive(false);
                accountRepository.save(account);
            }
            throw new ApiException("Account already blocked");
        }
    }

}
