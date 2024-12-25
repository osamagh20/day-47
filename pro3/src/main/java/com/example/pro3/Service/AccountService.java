package com.example.pro3.Service;
import com.example.pro3.ApiResponse.ApiException;
import com.example.pro3.DTO.AccountDTO;
import com.example.pro3.DTO.CustomerDTO;
import com.example.pro3.Model.Account;
import com.example.pro3.Model.Customer;
import com.example.pro3.Model.MyUser;
import com.example.pro3.Repository.AccountRepository;
import com.example.pro3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.pro3.Repository.AuthRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;

    // create new account
    public void addAccount(Integer user_id, Account newAccount) {
        MyUser myUser = authRepository.findMyUserById(user_id);
        if (myUser == null) {
            throw new ApiException("user not found");
        }
        Account account = accountRepository.findAccountById(newAccount.getId());
        if (account != null) {
            throw new ApiException("account exists");
        }
        if (myUser.getRole().equals("CUSTOMER")) {
            accountRepository.save(newAccount);
        }


    }

    public void updateAccount(Integer customer_id, Account account) {
        MyUser myUser = authRepository.findMyUserById(customer_id);
        if (myUser == null) {
            throw new ApiException("customer not found");
        }

        Account existingAccount = accountRepository.findAccountById(account.getId());
        Set<Account> customerAccounts = myUser.getCustomer().getAccounts();
        if (existingAccount == null) {
            throw new ApiException("account not found");
        }
        for(Account a : customerAccounts) {
            if (a.getId().equals(account.getId())) {
                account.setBalance(existingAccount.getBalance());
                accountRepository.save(account);
            }
        }

    }

    public void deleteAccount(Integer customer_id,Integer account_id) {
        MyUser myUser = authRepository.findMyUserById(customer_id);
        if (myUser == null) {
            throw new ApiException("customer not found");
        }

        Account account = accountRepository.findAccountById(account_id);
        Set<Account> customerAccounts = myUser.getCustomer().getAccounts();
        if (account == null) {
            throw new ApiException("account not found");
        }
        for(Account a : customerAccounts) {
            if (a.getId().equals(account.getId())) {
                accountRepository.delete(a);
            }
        }
        throw new ApiException("account not found");

    }

    public AccountDTO accountDetails(Integer customer_id,Integer account_id) {
        MyUser myUser = authRepository.findMyUserById(customer_id);
        if (myUser == null) {
            throw new ApiException("customer not found");
        }
        Account account = accountRepository.findAccountById(account_id);
        if (account == null) {
            throw new ApiException("account not found");
        }

        Set<Account> customerAccounts = myUser.getCustomer().getAccounts();
        AccountDTO accountDTO = new AccountDTO();
        for(Account a : customerAccounts) {
            if (a.getId().equals(account.getId())) {
                accountDTO.setAccountNumber(account.getAccountNumber());
                accountDTO.setBalance(account.getBalance());
                accountDTO.setIsActive(account.getIsActive());
                return accountDTO;
            }
        }
        throw new ApiException("account not found");
    }

    public List<AccountDTO> getAccounts(Integer user_id) {
        MyUser myUser = authRepository.findMyUserById(user_id);
        if (myUser == null) {
            throw new ApiException("user not found");
        }
        List<Account> accounts = accountRepository.findAll();
        if(accounts.isEmpty()) {
            throw new ApiException("Not have any accounts");
        }
        if (!myUser.getRole().equals("ADMIN")) {
            throw new ApiException("user not admin");
        }
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for(Account a : accounts) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setAccountNumber(a.getAccountNumber());
            accountDTO.setBalance(a.getBalance());
            accountDTO.setIsActive(a.getIsActive());
            accountDTOS.add(accountDTO);

        }
        return accountDTOS;
    }
}
