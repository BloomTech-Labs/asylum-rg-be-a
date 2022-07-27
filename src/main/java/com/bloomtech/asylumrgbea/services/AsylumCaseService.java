package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.controllers.exceptions.AsylumCaseNotFoundException;
import com.bloomtech.asylumrgbea.controllers.exceptions.BadRequestException;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.mappers.AsylumCaseMapper;
import com.bloomtech.asylumrgbea.models.CaseResponseDto;
import com.bloomtech.asylumrgbea.models.CasesRequestDto;
import com.bloomtech.asylumrgbea.models.Page;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AsylumCaseService {

	private final AsylumCaseRepository asylumCaseRepository;

	private final AsylumCaseMapper asylumCaseMapper;

	/**
	 * Gets all entities from repository and returns them out of the program.
	 * @return This is an Iterable of AsylumCase objects.
	 */
	@Cacheable("asylum_case_cache")
	public Iterable<CasesRequestDto> getAllAsylumCases() {

		Iterable<AsylumCase> caseIterable = asylumCaseRepository.findAll();
		validateIterableIsNotEmpty(caseIterable);

		return asylumCaseMapper.entitiesToResponseDtos(caseIterable);
	}

	/**
	 * Gets a page of entities from repository and returns them out of the program.
	 * @param casesRequestDto Contains the page number as a field.
	 * @return This is a limited Iterable of AsylumCase objects.
	 */
	public PageResponseDto getCasesBy(CasesRequestDto casesRequestDto) {
		Map<String, List<String>> filterMap = Map.of(
				"citizenship", 		getListOfFilters(casesRequestDto.getCitizenship(), 		"0"),
				"caseOutcome", 		getListOfFilters(casesRequestDto.getCaseOutcome(), 		","),
				"completionTo", 	getListOfFilters(casesRequestDto.getCompletionTo(), 	null),
				"completionFrom", 	getListOfFilters(casesRequestDto.getCompletionFrom(),	null),
				"currentDate", 		getListOfFilters(casesRequestDto.getCurrentDate(),      null),
				"isFiscalYear", 	getListOfFilters(casesRequestDto.getIsFiscalYear(), 	null),
				"asylumOffice", 	getListOfFilters(casesRequestDto.getAsylumOffice(), 	","));

		Map<String, String> operatorMap = Map.of(
				"citizenship",		"=",
				"caseOutcome", 		"=",
				"completionTo", 	"<=",
				"completionFrom",	">=",
				"currentDate",  	"=",
				"isFiscalYear", 	"",//later
				"asylumOffice",		"=");

		Iterable<AsylumCase> casesIterable = asylumCaseRepository.find(filterMap, operatorMap).getResults();
		validateIterableIsNotEmpty(casesIterable);

		//FIXME: remember to add to cache when cache is implemented
		List<AsylumCase> asylumCaseArrayList = new ArrayList<>();
		for (AsylumCase asylumCase : casesIterable) {
			asylumCaseArrayList.add(asylumCase);
		}

		return asylumCaseMapper.pageToResponseDto(
				new Page(casesRequestDto.getPageNumber(),
						getTotalPages(casesRequestDto, asylumCaseArrayList.size()),
						getCurrentPage(casesRequestDto, asylumCaseArrayList)));
	}

	private int getTotalPages(CasesRequestDto casesRequestDto, int arraySize) {
		return (int) Math.ceil((double)arraySize / casesRequestDto.getNumberOfItemsInPage());
	}

	//TODO add cache
	//TODO add error if current page is out of bounds
	private List<AsylumCase> getCurrentPage(CasesRequestDto casesRequestDto, List<AsylumCase> asylumCaseList) {
		List<AsylumCase> listOfAsylumCases = new ArrayList<>();

		int currentIndex = (casesRequestDto.getPageNumber() - 1) * casesRequestDto.getNumberOfItemsInPage();

		for (int i = 0; i < casesRequestDto.getNumberOfItemsInPage(); i++, currentIndex++) {
			if (currentIndex >= asylumCaseList.size()) { break; }

			listOfAsylumCases.add(asylumCaseList.get(currentIndex));
		}

		return listOfAsylumCases;
	}

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
	private void validateIterableIsNotEmpty(Iterable<AsylumCase> caseIterable) throws AsylumCaseNotFoundException {

		if (!caseIterable.iterator().hasNext())
			throw new AsylumCaseNotFoundException("ERROR: No cases were found...");
	}

	/**
	 * Checks if a CasesRequestDto object contains a null field and throws an exception if so.
	 * @param casesRequestDto package and a request Dto.
	 * @throws BadRequestException The exception thrown if triggered.
	 */
	private void validateRequestDto(CasesRequestDto casesRequestDto) throws BadRequestException {

		if (casesRequestDto.getNumberOfItemsInPage() == null)
			throw new BadRequestException("ERROR: The field number of items in a page cannot be null...");

	}
}
