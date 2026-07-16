package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spanprints.authservice.dto.printjob.CreatePrintJobRequest;
import com.spanprints.authservice.dto.printjob.PrintJobPaginatonResponse;
import com.spanprints.authservice.dto.printjob.PrintJobResponse;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.entity.PrintJobType;
import com.spanprints.authservice.service.CustomerService;
import com.spanprints.authservice.service.FileAttachmentService;
import com.spanprints.authservice.service.LedgerEntryService;
import com.spanprints.authservice.service.PrintJobService;
import com.spanprints.authservice.service.PrintJobTypeService;
import com.spanprints.authservice.util.SecurityUtils;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/printjobs")
public class PrintJobController {

	@Autowired
	private PrintJobService printJobService;
	@Autowired
	private PrintJobTypeService printJobTypeService;
	@Autowired
	private LedgerEntryService ledgerEntryService;
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private FileAttachmentService fileAttachmentService;

	@PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public PrintJobResponse createPrintJob(@Valid @ModelAttribute CreatePrintJobRequest request,
			@RequestParam(name = "attachments", required = false) List<MultipartFile> attachments) {
		Account account = securityUtils.getRequestingAccount();
		Customer customer = null;
		if (!customerService.doesCustomerExistsByPhoneNumber(request.getCustomerPrimaryPhoneNumber())) {
			customer = customerService.createCustomerForPrintJob(request);
		} else {
			customer = customerService.getCustomerByPrimaryPhoneNumber(request.getCustomerPrimaryPhoneNumber());
		}
		PrintJobType printJobType = printJobTypeService.getPrintJobTypeById(request.getPrintJobTypeId());
		PrintJob printJob = printJobService.createPrintJob(request, printJobType, account, customer);
		fileAttachmentService.addFileAttachment(attachments, printJob);
		ledgerEntryService.createLedgerEntry(printJob);
		return new PrintJobResponse(printJob);
	}

	@GetMapping
	public List<PrintJobResponse> getAllPrintJobs() {
		return printJobService.getAllPrintJobs().stream().map(PrintJobResponse::new).toList();
	}

	@GetMapping("paginated")
	public PrintJobPaginatonResponse getAllPrintJobsPaginated(@RequestParam @NotNull @Min(0) int pageNumber,
			@RequestParam @NotNull @Min(1) int pageSize) {
		return printJobService.getAllPrintJobsPaginated(pageNumber, pageSize);
	}

	@GetMapping("/id/{id}")
	public PrintJobResponse getPrintJobById(@PathVariable @NotNull @Positive @Min(1) Long id) {
		return new PrintJobResponse(printJobService.getPrintJobById(id));
	}

	@GetMapping("/uuid/{uuid}")
	public PrintJobResponse getPrintJobByUuid(@PathVariable @NotNull String uuid) {
		return new PrintJobResponse(printJobService.getPrintJobByUuid(uuid));
	}

	@GetMapping("customer-uuid/{uuid}")
	public List<PrintJobResponse> getAllPrintJobsByCustomerUuid(@PathVariable @NotNull String uuid) {
		return printJobService.getAllPrintJobsByCustomerUuid(uuid).stream().map(PrintJobResponse::new).toList();
	}

	@GetMapping("/today")
	public List<PrintJobResponse> getAllPrintJobsPlacedToday() {
		return printJobService.getAllPrintJobsPlacedToday().stream().map(PrintJobResponse::new).toList();
	}

	@GetMapping("/yet-to-deliver")
	public List<PrintJobResponse> findPreparedAndDueTodayOrEarlier() {
		return printJobService.findPreparedAndDueTodayOrEarlier().stream().map(PrintJobResponse::new).toList();
	}

	@GetMapping("/to-be-prepared-today")
	public List<PrintJobResponse> findToBePreparedToday() {
		return printJobService.findByDateOfDeliveryTomorrow().stream().map(PrintJobResponse::new).toList();
	}

	@GetMapping("/to-be-deliver-today")
	public List<PrintJobResponse> findToBeDeliveredToday() {
		return printJobService.findByDateOfDeliveryToday().stream().map(PrintJobResponse::new).toList();
	}
}
