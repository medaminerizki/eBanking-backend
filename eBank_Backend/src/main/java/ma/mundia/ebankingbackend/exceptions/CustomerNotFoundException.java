package ma.mundia.ebankingbackend.exceptions;

//Exception surveillée
public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
