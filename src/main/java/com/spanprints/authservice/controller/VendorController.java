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
import com.spanprints.authservice.dto.vendor.AddVendorRequestDto;
import com.spanprints.authservice.dto.vendor.UpdateVendorRequestDto;
import com.spanprints.authservice.entity.Vendor;
import com.spanprints.authservice.service.VendorService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("")
public class VendorController {

	@Autowired
	private VendorService vendorService;

	@PostMapping("/vendor")
	public ResponseEntity<Vendor> addVendor(@Valid @RequestBody AddVendorRequestDto dto) {
		return new ResponseEntity<>(vendorService.createVendor(dto), HttpStatus.CREATED);
	}

	@GetMapping("/vendor/id/{vendorId}")
	public ResponseEntity<Vendor> getVendorById(@PathVariable("vendorId") @NotNull @Positive @Min(1) Long id) {
		return new ResponseEntity<>(vendorService.getVendorById(id), HttpStatus.OK);
	}

	@GetMapping("/vendor/uuid/{vendorUuid}")
	public ResponseEntity<Vendor> getVendorByUuid(@PathVariable("vendorUuid") @NotNull String uuid) {
		return new ResponseEntity<>(vendorService.getVendorByUuid(uuid), HttpStatus.OK);
	}

	@GetMapping("/vendors")
	public ResponseEntity<List<Vendor>> getAllVendors() {
		return new ResponseEntity<>(vendorService.getAllVendors(), HttpStatus.OK);
	}

	@PutMapping("/vendor")
	public ResponseEntity<Vendor> updateVendor(@Valid @RequestBody UpdateVendorRequestDto dto) {
		return new ResponseEntity<>(vendorService.updateVendor(dto), HttpStatus.OK);
	}

	@DeleteMapping("/vendor/id/{vendorId}")
	public ResponseEntity<SuccessResponseDto> deleteVendorById(
			@PathVariable("vendorId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = vendorService.deleteVendorById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/vendor/uuid/{vendorId}")
	public ResponseEntity<SuccessResponseDto> deleteVendorByUuid(
			@PathVariable("vendorId") @NotNull String uuid) {
		SuccessResponseDto responseDto = vendorService.deleteVendorByUuid(uuid);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/vendors")
	public ResponseEntity<SuccessResponseDto> deleteAllVendors() {
		SuccessResponseDto responseDto = vendorService.deleteAllVendors();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
