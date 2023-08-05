package sakib.personal.springbootbatchupload.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import sakib.personal.springbootbatchupload.entity.Customer;
import sakib.personal.springbootbatchupload.listener.StepSkipListener;
import sakib.personal.springbootbatchupload.repository.CustomerRepository;

import java.io.File;

@Configuration
@RequiredArgsConstructor
public class SpringBatchConfig {

	private final CustomerRepository customerRepository;
	private final CustomerItemWriter customerItemWriter;

	@Bean
	@StepScope
	public FlatFileItemReader<Customer> flatFileItemReader(@Value("#{jobParameters[fullPathFileName]}") String pathToFIle) {
		FlatFileItemReader<Customer> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new FileSystemResource(new File(pathToFIle)));
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	private LineMapper<Customer> lineMapper() {
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setStrict(false);
		delimitedLineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "phone", "country", "dateOfBirth", "age");

		BeanWrapperFieldSetMapper<Customer> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(Customer.class);

		DefaultLineMapper<Customer> defaultLineMapper = new DefaultLineMapper<>();
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

		return defaultLineMapper;
	}

	@Bean
	public RepositoryItemWriter<Customer> writer() {
		RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
		writer.setRepository(customerRepository);
		writer.setMethodName("save");
		return writer;
	}

	@Bean
	public Step csvBatchStep(FlatFileItemReader<Customer> flatFileItemReader,
	                         JobRepository jobRepository,
	                         PlatformTransactionManager transactionManager) {
		return new StepBuilder("csv-batch-step", jobRepository)
				.<Customer, Customer>chunk(100, transactionManager)
				.reader(flatFileItemReader)
				.processor(processor())
				.writer(customerItemWriter)
				.faultTolerant()
				.listener(skipListener())
				.skipPolicy(skipPolicy())
				.taskExecutor(taskExecutor())
				.build();
	}

	@Bean
	public Job runJob(FlatFileItemReader<Customer> flatFileItemReader,
	                  JobRepository jobRepository,
	                  PlatformTransactionManager transactionManager) {
		return new JobBuilder("importCustomers", jobRepository)
				.flow(csvBatchStep(flatFileItemReader, jobRepository, transactionManager)).end().build();
	}

	@Bean
	public SkipPolicy skipPolicy() {
		return new ExceptionSkipPolicy();
	}

	@Bean
	public SkipListener skipListener() {
		return new StepSkipListener();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(10);
		return taskExecutor;
	}

	@Bean
	public CustomerProcessor processor() {
		return new CustomerProcessor();
	}
}
