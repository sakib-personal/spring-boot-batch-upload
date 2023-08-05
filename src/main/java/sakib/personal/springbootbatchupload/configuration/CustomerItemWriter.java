package sakib.personal.springbootbatchupload.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import sakib.personal.springbootbatchupload.entity.Customer;
import sakib.personal.springbootbatchupload.repository.CustomerRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerItemWriter implements ItemWriter<Customer> {

	private final CustomerRepository repository;

	@Override
	public void write(Chunk<? extends Customer> chunk) throws Exception {
		log.info("Current writer thread {}", Thread.currentThread().getName());
		repository.saveAll(chunk);
	}
}
