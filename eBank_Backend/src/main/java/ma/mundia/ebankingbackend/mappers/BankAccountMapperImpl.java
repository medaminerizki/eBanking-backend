package ma.mundia.ebankingbackend.mappers;

import ma.mundia.ebankingbackend.dtos.AccountOperationDTO;
import ma.mundia.ebankingbackend.dtos.CurrentBankAccountDTO;
import ma.mundia.ebankingbackend.dtos.CustomerDTO;
import ma.mundia.ebankingbackend.dtos.SavingsBankAccountDTO;
import ma.mundia.ebankingbackend.entities.AccountOperation;
import ma.mundia.ebankingbackend.entities.CurrentAccount;
import ma.mundia.ebankingbackend.entities.Customer;
import ma.mundia.ebankingbackend.entities.SavingsAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

//MapStruct : framework qui fait ça automatiquement, il génère le code
@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO= new CustomerDTO();
        //manière dynamique
        BeanUtils.copyProperties(customer,customerDTO);
        //manière statique
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customer.getName());
//        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }

    public SavingsBankAccountDTO fromSavingsBankAccount(SavingsAccount savingsAccount){
        SavingsBankAccountDTO savingsBankAccountDTO= new SavingsBankAccountDTO();
        BeanUtils.copyProperties(savingsAccount,savingsBankAccountDTO);
        savingsBankAccountDTO.setCustomerDTO(fromCustomer(savingsAccount.getCustomer()));
        savingsBankAccountDTO.setType(savingsAccount.getClass().getSimpleName());
        return savingsBankAccountDTO;
    }
    public SavingsAccount fromSavingsBankAccountDTO(SavingsBankAccountDTO savingsBankAccountDTO){
        SavingsAccount savingsAccount = new SavingsAccount();
        BeanUtils.copyProperties(savingsBankAccountDTO, savingsAccount);
        savingsAccount.setCustomer(fromCustomerDTO(savingsBankAccountDTO.getCustomerDTO()));
        return savingsAccount;
    }
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){
        CurrentBankAccountDTO currentBankAccountDTO= new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;
    }
    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO= new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }

}
