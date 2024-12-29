package ma.mundia.ebankingbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.mundia.ebankingbackend.dtos.*;
import ma.mundia.ebankingbackend.entities.*;
import ma.mundia.ebankingbackend.enums.OperationType;
import ma.mundia.ebankingbackend.exceptions.BalanceNotSufficientException;
import ma.mundia.ebankingbackend.exceptions.BankAccountNotFoundException;
import ma.mundia.ebankingbackend.exceptions.CustomerNotFoundException;
import ma.mundia.ebankingbackend.mappers.BankAccountMapperImpl;
import ma.mundia.ebankingbackend.repositories.AccountOperationRepository;
import ma.mundia.ebankingbackend.repositories.BankAccountRepository;
import ma.mundia.ebankingbackend.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount =bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingsBankAccountDTO saveSavingsBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId(UUID.randomUUID().toString());
        savingsAccount.setCreatedAt(new Date());
        savingsAccount.setBalance(initialBalance);
        savingsAccount.setInterestRate(interestRate);
        savingsAccount.setCustomer(customer);
        SavingsAccount savedBankAccount =bankAccountRepository.save(savingsAccount);
        return dtoMapper.fromSavingsBankAccount(savedBankAccount);
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customers.stream()
                .map(customer -> dtoMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        /*
        List<CustomerDTO> customerDTOS= new ArrayList<>();
        for(Customer customer : customers){
            CustomerDTO customerDTO=dtoMapper.fromCustomer(customer);
            customerDTOS.add(customerDTO);
        }*/
        return customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank Account not found"));
        if(bankAccount instanceof SavingsAccount){
            SavingsAccount savingsAccount =(SavingsAccount) bankAccount;
            return dtoMapper.fromSavingsBankAccount(savingsAccount);
        }else {
            CurrentAccount currentAccount= (CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank Account not found"));
        if(bankAccount.getBalance()<amount)
        {throw new BalanceNotSufficientException("Balance not sufficient");}
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank Account not found"));
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }
    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);

    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        List<BankAccount> bankAccounts= bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            } else {
                SavingsAccount savingsAccount = (SavingsAccount) bankAccount;
                return dtoMapper.fromSavingsBankAccount(savingsAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return dtoMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccount_Id(accountId);
        return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount==null) throw new BankAccountNotFoundException("Account not found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccount_IdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        List<AccountOperationDTO> accountOperationDTOs = accountOperations.getContent().stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        AccountHistoryDTO accountHistoryDTO= new AccountHistoryDTO();
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOs);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers= customerRepository.findByNameContainsIgnoreCase(keyword);
        List<CustomerDTO> customerDTO = customers.stream().map(cust -> dtoMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customerDTO;
    }

}

