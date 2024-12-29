package ma.mundia.ebankingbackend.dtos;

import lombok.Data;
import ma.mundia.ebankingbackend.enums.AccountStatus;

import java.util.Date;



@Data
public class SavingsBankAccountDTO extends BankAccountDTO{
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}