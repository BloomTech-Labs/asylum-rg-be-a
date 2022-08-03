package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.controllers.exceptions.AsylumCaseNotFoundException;
import com.bloomtech.asylumrgbea.controllers.exceptions.BadRequestException;
import com.bloomtech.asylumrgbea.controllers.exceptions.PageNotFoundException;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.mappers.AsylumCaseMapper;
import com.bloomtech.asylumrgbea.models.CasesRequestDto;
import com.bloomtech.asylumrgbea.models.Page;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AsylumCaseService {

	private final AsylumCaseRepository asylumCaseRepository;

	private final AsylumCaseMapper asylumCaseMapper;

	/**
	 * Retrieves all entries of asylum cases from the repository.
	 * @return Iterable of CaseRequestDtos.
	 */
	@Cacheable("asylum_case_cache")
	public Iterable<CasesRequestDto> getAllAsylumCases() {

		Iterable<AsylumCase> caseIterable = asylumCaseRepository.findAll();
		validateIterableIsNotEmpty(caseIterable);

		return asylumCaseMapper.entitiesToResponseDtos(caseIterable);
	}

	/**
	 * Returns entries of asylum cases based on the query parameters.
	 * @param casesRequestDto a dto for query parameters.
	 * @return PageResponseDto.
	 */
	public PageResponseDto getCasesBy(CasesRequestDto casesRequestDto) {
		this.validateRequestDto(casesRequestDto);

		Map<String, List<String>> filterMap = Map.of(
				"citizenship",	getListOfFilters(casesRequestDto.getCitizenship(),	"0"),
				"caseOutcome", 	getListOfFilters(casesRequestDto.getOutcome(),	","),
				"asylumOffice",	getListOfFilters(casesRequestDto.getOffice(), ","));

		Map<String, String[]> rangeMap = Map.of(
				"completionDate", new String[] {
						casesRequestDto.getFrom(),
						casesRequestDto.getTo()
				}
		);

		Iterable<AsylumCase> casesIterable = asylumCaseRepository.find(filterMap, rangeMap).getResults();
		this.validateIterableIsNotEmpty(casesIterable);

		//FIXME: remember to add to cache when cache is implemented
		List<AsylumCase> asylumCaseArrayList = new ArrayList<>();
		for (AsylumCase asylumCase : casesIterable) {
			asylumCaseArrayList.add(asylumCase);
		}

		this.validatePageRequest(casesRequestDto, asylumCaseArrayList);

		return asylumCaseMapper.pageToResponseDto(
				new Page(casesRequestDto.getPage(),
						getTotalPages(casesRequestDto, asylumCaseArrayList.size()),
						getCurrentPage(casesRequestDto, asylumCaseArrayList)));
	}

	/**
	 * Private helper method that calculates the total number of pages for a specific request.
	 * @param casesRequestDto a dto for query parameters.
	 * @param arraySize int the total number of items of the scan.
	 * @return the int number of pages total.
	 */
	private int getTotalPages(CasesRequestDto casesRequestDto, int arraySize) {
		return (int) Math.ceil((double)arraySize / casesRequestDto.getLimit());
	}

	//TODO add cache
	/**
	 * Private helper method that generates the list of items to return for a specific page.
	 * @param casesRequestDto a dto for query parameters.
	 * @param asylumCaseList the List of AsylumCase from the scan result.
	 * @return A sub List of AsylumCase to include in a specific page.
	 */
	private List<AsylumCase> getCurrentPage(CasesRequestDto casesRequestDto, List<AsylumCase> asylumCaseList) {
		List<AsylumCase> listOfAsylumCases = new ArrayList<>();

		int currentIndex = (casesRequestDto.getPage() - 1) * casesRequestDto.getLimit();

		for (int i = 0; i < casesRequestDto.getLimit(); i++, currentIndex++) {
			if (currentIndex >= asylumCaseList.size()) { break; }

			listOfAsylumCases.add(asylumCaseList.get(currentIndex));
		}

		return listOfAsylumCases;
	}

	/**
	 * Private helper method that breaks a Single string by its delimiter.
	 * @param object The wrapper class to stringify.
	 * @param delimiter the String to delimit with.
	 * @return List of delimited Strings.
	 */
	private List<String> getListOfFilters(Object object, String delimiter) {
		if (delimiter == null) {
			return object == null ?
					Collections.emptyList() :
					List.of(object.toString());
		}
		if (delimiter.equals("0") || delimiter.equals(",")) {
			return object == null ?
					Collections.emptyList() :
					Arrays.asList(object.toString().split(delimiter));
		}
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if an Iterable has at least 1 element and throws an exception if not so.
	 * @param caseIterable An empty or non-empty Iterable of AsylumCase objects.
	 * @throws AsylumCaseNotFoundException if the Iterable is empty this exception is thrown.
	 */
	private void validateIterableIsNotEmpty(Iterable<AsylumCase> caseIterable)
			throws AsylumCaseNotFoundException {

		if (!caseIterable.iterator().hasNext()) {
			throw new AsylumCaseNotFoundException("ERROR: No cases were found...");
		}
	}

	/**
	 * Checks if a page can be generated given a List of asylum cases.
	 * @param requestDto a dto for the query parameters.
	 * @param asylumCaseList a List of AsylumCases.
	 * @throws PageNotFoundException if an empty page is generated.
	 */
	private void validatePageRequest(CasesRequestDto requestDto, List<AsylumCase> asylumCaseList)
			throws PageNotFoundException {

		if (!getCurrentPage(requestDto, asylumCaseList).iterator().hasNext()) {
			throw new PageNotFoundException("Error: No pages left to access...");
		}
	}

	/**
	 * Checks if the limit and page query parameter values are valid.
	 * @param casesRequestDto the query parameter dto.
	 * @throws BadRequestException The exception thrown if triggered.
	 */
	private void validateRequestDto(CasesRequestDto casesRequestDto)
			throws BadRequestException {

		if (casesRequestDto.getLimit() < 1 || casesRequestDto.getPage() < 1) {
			throw new BadRequestException("ERROR: The page or limit value cannot be less than 1...");
		}
	}
}
