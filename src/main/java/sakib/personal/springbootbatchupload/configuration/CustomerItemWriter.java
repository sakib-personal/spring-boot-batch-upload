package sakib.personal.springbootbatchupload.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import sakib.personal.springbootbatchupload.entity.Customer;
import sakib.personal.springbootbatchupload.repository.CustomerRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerItemWriter implements ItemWriter<Customer> {

	private final CustomerRepository repository;

	@Override
	public void write(List<? extends Customer> list) throws Exception {
		log.info("Current writer thread {}", Thread.currentThread().getName());
		repository.saveAll(list);
	}
}
