package sakib.personal.springbootbatchupload.controller;

import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sakib.personal.springbootbatchupload.service.CustomerService;

@RequiredArgsConstructor
@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping
	public ResponseEntity<?> getCustomers(
			@Valid @RequestParam(defaultValue = "1")
			@Nonnull
			@Min(value = 1, message = "Must be greater than or equal to 1")
			Integer page,
			@RequestParam(defaultValue = "20") Integer pageSize) {
		return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomers(page - 1, pageSize));
	}
}
