package sakib.personal.springbootbatchupload.service.batch;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CsvBatchJobService {

	boolean importData(MultipartFile multipartFile) throws IOException, JobInstanceAlreadyCompleteException,
			JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException;
}
