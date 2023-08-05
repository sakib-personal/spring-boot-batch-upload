package sakib.personal.springbootbatchupload.service.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sakib.personal.springbootbatchupload.service.store.FileStoreService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CustomerCsvBatchJobService implements CsvBatchJobService {

	private final JobLauncher jobLauncher;
	private final Job job;
	private final FileStoreService fileStoreService;

	public boolean importData(MultipartFile multipartFile) throws IOException,
			JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
			JobParametersInvalidException, JobRestartException {

		String fileFullPath = fileStoreService.storeTemporaryFile(multipartFile);

		JobParameters jobParameters = new JobParametersBuilder()
				.addString("fullPathFileName", fileFullPath)
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();

		JobExecution execution = jobLauncher.run(job, jobParameters);

		if (execution.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED.getExitCode())) {
			fileStoreService.RemoveTemporaryFile(fileFullPath);
		}

		return Boolean.TRUE;
	}
}
