package ma.mundia.ebankingbackend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SA") //Dans la strategie table per classe , cette annotation n'a plus de sens
@Data @NoArgsConstructor @AllArgsConstructor
public class SavingsAccount extends BankAccount{
    //Taux d'interets
    private double interestRate;
}
