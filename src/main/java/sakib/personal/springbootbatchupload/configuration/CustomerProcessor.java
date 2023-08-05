package sakib.personal.springbootbatchupload.configuration;

import org.springframework.batch.item.ItemProcessor;
import sakib.personal.springbootbatchupload.entity.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {
	@Override
	public Customer process(Customer customer) {
		// Under 18 age customer not allow
		return Integer.parseInt(customer.getAge()) >= 18 ? customer : null;
	}
}
