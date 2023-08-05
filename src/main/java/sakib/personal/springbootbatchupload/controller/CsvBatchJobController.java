package sakib.personal.springbootbatchupload.controller;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sakib.personal.springbootbatchupload.service.batch.CsvBatchJobService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/import-csv-file")
@RequiredArgsConstructor
@Slf4j
public class CsvBatchJobController {

	private final CsvBatchJobService csvBatchJobService;

	@PostMapping(value = "/customer")
	public ResponseEntity<?> importCustomerCsvData(@RequestParam("file") @Nonnull MultipartFile multipartFile) {
		try {
			if (csvBatchJobService.importData(multipartFile)) {
				return ResponseEntity.status(HttpStatus.OK).body("CSV file imported.");
			}
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
		         JobParametersInvalidException | IOException exception) {
			log.error("Exception occurs during importing customer csv file. Exception: {}", exception.getMessage());
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to import.");
	}
}
