package com.bloomtech.asylumrgbea.services.impl;

import com.bloomtech.asylumrgbea.controllers.exceptions.AsylumCaseNotFoundException;
import com.bloomtech.asylumrgbea.controllers.exceptions.BadRequestException;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.mappers.AsylumCaseMapper;
import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

@Service
@RequiredArgsConstructor
public class AsylumCaseServiceImpl implements AsylumCaseService {

	private final AsylumCaseRepository asylumCaseRepository;

	private final AsylumCaseMapper asylumCaseMapper;

	/**
	 * Gets all entities from repository and returns them out of the program.
	 * @return This is an Iterable of AsylumCase objects.
	 */
	@Override
	@Cacheable("asylum_case_cache")
	public Iterable<AsylumCaseResponseDto> getAllAsylumCases() {

		Iterable<AsylumCase> caseIterable = asylumCaseRepository.findAll();
		validateIterableIsNotEmpty(caseIterable);

		return asylumCaseMapper.entitiesToResponseDtos(caseIterable);
	}

	/**
	 * Gets a page of entities from repository and returns them out of the program.
	 * @param asylumCaseRequestDto Contains the page number as a field.
	 * @return This is a limited Iterable of AsylumCase objects.
	 */
	@Override
	public PageResponseDto getPageOfAsylumCases(AsylumCaseRequestDto asylumCaseRequestDto) {

		validateRequestDto(asylumCaseRequestDto);
		Page<AsylumCase> pageOfEntities = asylumCaseRepository.findAll
				(PageRequest.of(asylumCaseRequestDto.getPageNumber(), asylumCaseRequestDto.getNumberOfItemsInPage()));

		return asylumCaseMapper.pageDataAndPageToResponseDto
				(pageOfEntities.getTotalPages(), asylumCaseMapper.pageToResponseDtos(pageOfEntities));
	}

	/**
	 * Checks if an Iterable has at least 1 element and throws an exception if not so.
	 * @param caseIterable An empty or non-empty Iterable of AsylumCase objects.
	 * @throws AsylumCaseNotFoundException if the Iterable is empty this exception is thrown.
	 */
	private void validateIterableIsNotEmpty(Iterable<AsylumCase> caseIterable) throws AsylumCaseNotFoundException {

		if (!caseIterable.iterator().hasNext())
			throw new AsylumCaseNotFoundException("ERROR: No cases were found...");
	}

	/**
	 * Checks if a AsylumCaseRequestDto object contains a null field and throws an exception if so.
	 * @param asylumCaseRequestDto AsylumCase package and a request Dto.
	 * @throws BadRequestException The exception thrown if triggered.
	 */
	private void validateRequestDto(AsylumCaseRequestDto asylumCaseRequestDto) throws BadRequestException {

		if (asylumCaseRequestDto.getNumberOfItemsInPage() == null)
			throw new BadRequestException("ERROR: Enter message here...");
	}
}
