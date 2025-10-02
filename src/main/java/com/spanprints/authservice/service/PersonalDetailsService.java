package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.PersonalDetailsDto;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.PersonalDetails;
import com.spanprints.authservice.exception.personaldetails.PersonalDetailsFoundException;
import com.spanprints.authservice.repository.PersonalDetailsRepository;

@Service
public class PersonalDetailsService {

	@Autowired
	private PersonalDetailsRepository personalDetailsRepository;

	public PersonalDetails addPersonalDetail(PersonalDetailsDto dto, Account account) {
		PersonalDetails personDetails = account.getPersonalDetails();
		if (personDetails != null) {
			updatePersonDetails(personDetails, dto);
		} else {
			personDetails = createPersonDetails(dto);
		}
		personDetails.setAccount(account);
		return personalDetailsRepository.save(personDetails);
	}

	private PersonalDetails createPersonDetails(PersonalDetailsDto dto) {
		return PersonalDetails.builder().name(dto.getName()).gender(dto.getGender()).birthday(dto.getBirthday())
				.build();
	}

	private PersonalDetails updatePersonDetails(PersonalDetails details, PersonalDetailsDto dto) {
		details.setName(dto.getName());
		details.setGender(dto.getGender());
		details.setBirthday(dto.getBirthday());
		return details;
	}

	public PersonalDetails getPersonalDetailsById(Long id) {
		return personalDetailsRepository.findById(id).orElseThrow(
				() -> new PersonalDetailsFoundException(String.format("No personal detail exists with id `%d`", id)));
	}

	public List<PersonalDetails> getAll() {
		return personalDetailsRepository.findAll();
	}

	public void delete(PersonalDetails personalDetails) {
		personalDetailsRepository.delete(personalDetails);
	}

	public SuccessResponseDto deleteById(Long id) {
		personalDetailsRepository.delete(getPersonalDetailsById(id));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted personal details by id `%d`", id));
	}

	public SuccessResponseDto deleteAllDetails() {
		personalDetailsRepository.deleteAll();
		return new SuccessResponseDto(HttpStatus.OK, "Deleted all personal details.");
	}

}
