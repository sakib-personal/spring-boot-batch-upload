package sakib.personal.springbootbatchupload.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sakib.personal.springbootbatchupload.entity.Customer;
import sakib.personal.springbootbatchupload.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository repository;

	public Page<Customer> getCustomers(int page, int pageSize) {
		return repository.findAll(PageRequest.of(page, pageSize));
	}

}
