package ma.mundia.ebankingbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.mundia.ebankingbackend.entities.BankAccount;

import java.util.List;

@Data //on a besoin des getters et setters et attributs
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;

}
