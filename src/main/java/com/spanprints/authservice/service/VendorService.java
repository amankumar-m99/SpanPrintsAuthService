package com.spanprints.authservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.vendor.AddVendorRequestDto;
import com.spanprints.authservice.dto.vendor.UpdateVendorRequestDto;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Vendor;
import com.spanprints.authservice.exception.vendor.VendorAlreadyExistsException;
import com.spanprints.authservice.exception.vendor.VendorNotFoundException;
import com.spanprints.authservice.repository.VendorRepository;
import com.spanprints.authservice.util.SecurityUtils;

@Service
public class VendorService {

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private SecurityUtils securityUtils;

	public Vendor createVendor(AddVendorRequestDto dto) {
		throwIfNameAlreadyExists(dto.getName());
		Account account = securityUtils.getRequestingAccount();
		Vendor vendor = Vendor.builder().id(null).uuid(UUID.randomUUID().toString()).name(dto.getName())
				.email(dto.getEmail()).primaryPhoneNumber(dto.getPrimaryPhoneNumber()).address(dto.getAddress())
				.alternatePhoneNumber(dto.getAlternatePhoneNumber()).addedBy(account).dateAdded(LocalDateTime.now())
				.build();
		return vendorRepository.save(vendor);
	}

	public List<Vendor> getAllVendors() {
		return vendorRepository.findAll();
	}

	public Vendor getVendorById(Long id) {
		return vendorRepository.findById(id).orElseThrow(() -> new VendorNotFoundException("id", id));
	}

	public Vendor getVendorByUuid(String uuid) {
		return vendorRepository.findByUuid(uuid).orElseThrow(() -> new VendorNotFoundException("UUID", uuid));
	}

	public List<Vendor> getAllVendorsByEmail(String email) {
		return vendorRepository.findAllByEmail(email).orElseThrow(() -> new VendorNotFoundException("email", email));
	}

	public Vendor getVendorByName(String name) {
		return vendorRepository.findByName(name).orElseThrow(() -> new VendorNotFoundException("name", name));
	}

	public List<Vendor> getVendorByPrimaryPhoneNumber(String primaryPhoneNumber) {
		return vendorRepository.findAllByPrimaryPhoneNumber(primaryPhoneNumber)
				.orElseThrow(() -> new VendorNotFoundException("primary phone number", primaryPhoneNumber));
	}

	private void throwIfNameAlreadyExists(String name) {
		if (!vendorRepository.findByName(name).isEmpty()) {
			throw new VendorAlreadyExistsException("A vendor already exists with the name " + name);
		}
	}

	public Vendor updateVendor(UpdateVendorRequestDto dto) {
		Vendor vendor = getVendorById(dto.getId());
		vendor.setEmail(dto.getEmail());
		vendor.setName(dto.getName());
		vendor.setPrimaryPhoneNumber(dto.getPrimaryPhoneNumber());
		vendor.setAlternatePhoneNumber(dto.getAlternatePhoneNumber());
		return vendorRepository.save(vendor);
	}

	public SuccessResponseDto deleteAllVendors() {
		vendorRepository.deleteAll();
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted all vendors"));

	}

	public SuccessResponseDto deleteVendorById(Long id) {
		vendorRepository.delete(getVendorById(id));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted vendor by id `%d`", id));

	}

	public SuccessResponseDto deleteVendorByUuid(String uuid) {
		vendorRepository.delete(getVendorByUuid(uuid));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted vendor by UUID `%s`", uuid));
	}

	public SuccessResponseDto deleteVendorByName(String name) {
		vendorRepository.delete(getVendorByName(name));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted vendor by name `%s`", name));
	}
}
