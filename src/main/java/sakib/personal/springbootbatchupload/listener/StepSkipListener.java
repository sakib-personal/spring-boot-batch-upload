package sakib.personal.springbootbatchupload.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import sakib.personal.springbootbatchupload.entity.Customer;

@Slf4j
public class StepSkipListener implements SkipListener<Customer, Number> {

	@Override
	public void onSkipInRead(Throwable throwable) {
		log.error("Skip due to an exception while reading. Exception: {}", throwable.getMessage());
	}

	@Override
	public void onSkipInWrite(Number number, Throwable throwable) {
		log.error("Skip {} due to an exception while writing. Exception: {}", throwable.getMessage(), number);
	}

	@SneakyThrows
	@Override
	public void onSkipInProcess(Customer customer, Throwable throwable) {
		log.error("Skip {} due to an exception while processing. Exception: {}",
				new ObjectMapper().writeValueAsString(customer),
				throwable.getMessage());
	}
}
