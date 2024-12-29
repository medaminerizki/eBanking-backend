package ma.mundia.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.mundia.ebankingbackend.dtos.CustomerDTO;
import ma.mundia.ebankingbackend.entities.Customer;
import ma.mundia.ebankingbackend.exceptions.CustomerNotFoundException;
import ma.mundia.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
//@RequestMapping("/")
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
        public List<CustomerDTO> customers () {
            return bankAccountService.listCustomers();
        }
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers (@RequestParam(name = "keyword",defaultValue = "") String keyword) {
        return bankAccountService.searchCustomers(keyword);
    }
        @GetMapping("/customers/{id}")
        public CustomerDTO getCustomer (@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
            return bankAccountService.getCustomer(customerId);
        }
        @PostMapping("/customers")
        public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
            return bankAccountService.saveCustomer(customerDTO);
        }

        @PutMapping("/customers/{id}")
        //pas besoin de spécifier le name dans path variable s'ils ont le même nom
        public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
            customerDTO.setId(id);
            return bankAccountService.updateCustomer(customerDTO);
        }
        @DeleteMapping("/customers/{id}")
        public void deleteCustomer(@PathVariable Long id){
            bankAccountService.deleteCustomer(id);
    }
    }