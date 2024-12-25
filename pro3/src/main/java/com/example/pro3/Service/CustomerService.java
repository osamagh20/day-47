package com.example.pro3.Service;

import com.example.pro3.ApiResponse.ApiException;
import com.example.pro3.DTO.CustomerDTO;
import com.example.pro3.DTO.CustomerDTOIn;
import com.example.pro3.Model.Account;
import com.example.pro3.Model.Customer;
import com.example.pro3.Model.MyUser;
import com.example.pro3.Repository.AuthRepository;
import com.example.pro3.Repository.CustomerRepository;
import com.example.pro3.Repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;

    public List<CustomerDTO> getAllCustomers(Integer user_id) {
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");
        }
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("user is not admin");
        }

        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new ApiException("not have any customers");
        }
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setUsername(customer.getUser().getUsername());
            customerDTO.setPassword(customer.getUser().getPassword());
            customerDTO.setName(customer.getUser().getName());
            customerDTO.setEmail(customer.getUser().getEmail());
            customerDTO.setPhoneNumber(customer.getPhoneNumber());
            customerDTOList.add(customerDTO);
        }
        return customerDTOList;
    }

    public void updateCustomer(Integer customer_id, CustomerDTOIn customerDTO) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        customer.getUser().setUsername(customerDTO.getUsername());
        customer.getUser().setPassword(customerDTO.getPassword());
        customer.getUser().setName(customerDTO.getName());
        customer.getUser().setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customerRepository.save(customer);
    }

    public void withdrawMoney(Integer customer_id,Integer account_id,Double withdraw) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        Account account = accountRepository.findAccountById(account_id);
        if (account == null) {
            throw new ApiException("account not found");
        }
        Collection<Account> customerAccounts = customer.getAccounts();
        for(Account a : customerAccounts) {
            if (a.getId().equals(account_id)) {
                if(a.getBalance()>withdraw) {
                    a.setBalance(a.getBalance()-withdraw);
                }

            }
        }

    }

    public void depositMoney(Integer customer_id,Integer account_id,Double deposit) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        Account account = accountRepository.findAccountById(account_id);
        if (account == null) {
            throw new ApiException("account not found");
        }

        Collection<Account> customerAccounts = customer.getAccounts();
        for(Account a : customerAccounts) {
            if (a.getId().equals(account_id)) {
                a.setBalance(a.getBalance()+deposit);
            }
        }

    }

    public void transferMoney(Integer customer_id,Integer fromAccount_id,Double transfer,Integer toAccount_id) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        Account fromAccount = accountRepository.findAccountById(fromAccount_id);
        Account toAccount = accountRepository.findAccountById(toAccount_id);
        if (fromAccount == null) {
            throw new ApiException("You not have account");
        }
        if (toAccount == null) {
            throw new ApiException("Account you want transfer to it is not found");
        }
        Collection<Account> customerAccounts = customer.getAccounts();
        for(Account a : customerAccounts) {
            if (a.getId().equals(fromAccount_id)) {
                if(a.getBalance()>transfer) {
                    a.setBalance(a.getBalance()-transfer);
                    toAccount.setBalance(toAccount.getBalance()+transfer);
                }
            }
        }

    }


}
