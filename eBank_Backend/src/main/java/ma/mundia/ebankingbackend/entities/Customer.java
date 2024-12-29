package ma.mundia.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "customer")
    //Dans la classe BankAccount y'a un attribut "customer" qui utilise @ManyToOne et il s'agit de la meme relation
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //ignorer list bank account pas la peine de sérialiser mais j'aurai pas la liste des comptes, il faut faire appel aux dtos pour préciser les données qu'on veut
    private List<BankAccount> bankAccounts;
}
