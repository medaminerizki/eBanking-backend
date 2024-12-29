package ma.mundia.ebankingbackend;

import ma.mundia.ebankingbackend.dtos.BankAccountDTO;
import ma.mundia.ebankingbackend.dtos.CurrentBankAccountDTO;
import ma.mundia.ebankingbackend.dtos.CustomerDTO;
import ma.mundia.ebankingbackend.dtos.SavingsBankAccountDTO;
import ma.mundia.ebankingbackend.entities.*;
import ma.mundia.ebankingbackend.enums.AccountStatus;
import ma.mundia.ebankingbackend.enums.OperationType;
import ma.mundia.ebankingbackend.exceptions.BalanceNotSufficientException;
import ma.mundia.ebankingbackend.exceptions.BankAccountNotFoundException;
import ma.mundia.ebankingbackend.exceptions.CustomerNotFoundException;
import ma.mundia.ebankingbackend.repositories.AccountOperationRepository;
import ma.mundia.ebankingbackend.repositories.BankAccountRepository;
import ma.mundia.ebankingbackend.repositories.CustomerRepository;
import ma.mundia.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Amine","Mohamed","Hicham").forEach(name->{
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"007@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
                    bankAccountService.saveSavingsBankAccount(Math.random() * 120000, 5.5, customer.getId());
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount : bankAccounts)
                for (int i = 0; i < 10; i++) {
                    String accountId;
                    if(bankAccount instanceof SavingsBankAccountDTO){
                        accountId=((SavingsBankAccountDTO) bankAccount).getId();
                    }else{
                        accountId=((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId, 10000 + Math.random() * 120000, "Credit");
                    bankAccountService.debit(accountId, 1000 + Math.random() * 9000, "Debit");
                }

        };
    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Abdelghani", "Wissal", "Marouane").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingsAccount savingsAccount =new SavingsAccount();
                savingsAccount.setId(UUID.randomUUID().toString());
                savingsAccount.setBalance(Math.random()*90000);
                savingsAccount.setCreatedAt(new Date());
                savingsAccount.setStatus(AccountStatus.CREATED);
                savingsAccount.setCustomer(cust);
                savingsAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingsAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i = 0; i <10 ; i++) {
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT); //Math.random(): genere un nombre entre 0 et 1
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }

            });
        };
    }


}