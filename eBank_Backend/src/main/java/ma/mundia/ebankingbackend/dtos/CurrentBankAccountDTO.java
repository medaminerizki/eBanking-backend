package ma.mundia.ebankingbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.mundia.ebankingbackend.entities.AccountOperation;
import ma.mundia.ebankingbackend.entities.Customer;
import ma.mundia.ebankingbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Data
public class CurrentBankAccountDTO extends BankAccountDTO{

    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;

}
