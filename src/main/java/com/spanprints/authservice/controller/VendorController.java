package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.vendor.CreateVendorRequest;
import com.spanprints.authservice.dto.vendor.UpdateVendorRequest;
import com.spanprints.authservice.dto.vendor.VendorResponse;
import com.spanprints.authservice.service.VendorService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/vendors")
public class VendorController {

	@Autowired
	private VendorService vendorService;

	@PostMapping
	public ResponseEntity<VendorResponse> addVendor(@Valid @RequestBody CreateVendorRequest request) {
		VendorResponse vendorResponse = new VendorResponse(vendorService.createVendor(request));
		return new ResponseEntity<>(vendorResponse, HttpStatus.CREATED);
	}

	@GetMapping
	public List<VendorResponse> getAllVendors() {
		return vendorService.getAllVendors().stream().map(VendorResponse::new).toList();
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<VendorResponse> getVendorById(@PathVariable @NotNull @Positive @Min(1) Long id) {
		VendorResponse vendorResponse = new VendorResponse(vendorService.getVendorById(id));
		return new ResponseEntity<>(vendorResponse, HttpStatus.OK);
	}

	@GetMapping("/uuid/{uuid}")
	public ResponseEntity<VendorResponse> getVendorByUuid(@PathVariable @NotNull String uuid) {
		VendorResponse vendorResponse = new VendorResponse(vendorService.getVendorByUuid(uuid));
		return new ResponseEntity<>(vendorResponse, HttpStatus.OK);
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<VendorResponse> updateVendor(@PathVariable @NotNull @Positive @Min(1) Long id,
			@Valid @RequestBody UpdateVendorRequest request) {
		VendorResponse vendorResponse = new VendorResponse(vendorService.updateVendor(id, request));
		return new ResponseEntity<>(vendorResponse, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponseDto> deleteAllVendors() {
		SuccessResponseDto responseDto = vendorService.deleteAllVendors();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<SuccessResponseDto> deleteVendorById(@PathVariable @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = vendorService.deleteVendorById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/uuid/{uuid}")
	public ResponseEntity<SuccessResponseDto> deleteVendorByUuid(@PathVariable @NotNull String uuid) {
		SuccessResponseDto responseDto = vendorService.deleteVendorByUuid(uuid);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
