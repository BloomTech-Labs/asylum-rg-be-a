package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.controllers.exceptions.AsylumCaseNotFoundException;
import com.bloomtech.asylumrgbea.controllers.exceptions.BadRequestException;
import com.bloomtech.asylumrgbea.controllers.exceptions.PageNotFoundException;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.mappers.AsylumCaseMapper;
import com.bloomtech.asylumrgbea.models.AsylumCaseModel;
import com.bloomtech.asylumrgbea.models.CasesQueryParameterDto;
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
	 * @return Iterable of CasesQueryParameterDtos.
	 */
	@Cacheable("asylum_case_cache")
	public Iterable<CasesQueryParameterDto> getAllAsylumCases() {

		Iterable<AsylumCase> caseIterable = asylumCaseRepository.findAll();
		validateIterableIsNotEmpty(caseIterable);

		return asylumCaseMapper.entitiesToQueryParameters(caseIterable);
	}

	/**
	 * Returns a PageResponseDto based on the query parameters.
	 * @param queryParameters a dto containing query parameters.
	 * @return PageResponseDto.
	 */
	public PageResponseDto getCasesBy(CasesQueryParameterDto queryParameters) {
		this.validateRequestDto(queryParameters);

		Map<String, List<String>> filterMap = Map.of(
				"citizenship",	getListOfFilters(queryParameters.getCitizenship(),	"0"),
				"caseOutcome", 	getListOfFilters(queryParameters.getOutcome(),	","),
				"asylumOffice",	getListOfFilters(queryParameters.getOffice(), ","));

		Map<String, String[]> rangeMap = Map.of(
				"completionDate", new String[] {
						queryParameters.getFrom(),
						queryParameters.getTo()
				}
		);

		Iterable<AsylumCase> casesIterable = asylumCaseRepository.find(filterMap, rangeMap).getResults();
		this.validateIterableIsNotEmpty(casesIterable);

		//FIXME: remember to add to cache when cache is implemented
		List<AsylumCaseModel> asylumCaseModelList = new ArrayList<>();
		for (AsylumCase asylumCase : casesIterable) {
			asylumCaseModelList.add(asylumCaseMapper.entityToModel(asylumCase));
		}

		this.validatePageRequest(queryParameters, asylumCaseModelList);

		return asylumCaseMapper.pageToResponseDto(
				new Page(queryParameters.getPage(),
						getTotalPages(queryParameters, asylumCaseModelList.size()),
						getCurrentPage(queryParameters, asylumCaseModelList)));
	}

	/**
	 * Private helper method that calculates the total number of pages for a specific request.
	 * @param queryParameters a dto for query parameters.
	 * @param arraySize int the total number of items of the scan.
	 * @return the int number of pages total.
	 */
	private int getTotalPages(CasesQueryParameterDto queryParameters, int arraySize) {
		return (int) Math.ceil((double)arraySize / queryParameters.getLimit());
	}

	//TODO add cache
	/**
	 * Private helper method that generates the list of items to return for a specific page.
	 * @param queryParameters a dto for query parameters.
	 * @param asylumCaseList the List of AsylumCase from the scan result.
	 * @return A sub List of AsylumCase to include in a specific page.
	 */
	private List<?> getCurrentPage(CasesQueryParameterDto queryParameters, List<?> asylumCaseList) {
		List<Object> listOfAsylumCases = new ArrayList<>();

		int currentIndex = (queryParameters.getPage() - 1) * queryParameters.getLimit();

		for (int i = 0; i < queryParameters.getLimit(); i++, currentIndex++) {
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
	 * @param queryParameters a dto for the query parameters.
	 * @param asylumCaseList a List of AsylumCases.
	 * @throws PageNotFoundException if an empty page is generated.
	 */
	private void validatePageRequest(CasesQueryParameterDto queryParameters, List<?> asylumCaseList)
			throws PageNotFoundException {

		if (!getCurrentPage(queryParameters, asylumCaseList).iterator().hasNext()) {
			throw new PageNotFoundException("Error: No pages left to access...");
		}
	}

	/**
	 * Checks if the limit and page query parameter values are valid.
	 * @param queryParameters the query parameter dto.
	 * @throws BadRequestException The exception thrown if triggered.
	 */
	private void validateRequestDto(CasesQueryParameterDto queryParameters)
			throws BadRequestException {

		if (queryParameters.getLimit() < 1 || queryParameters.getPage() < 1) {
			throw new BadRequestException("ERROR: The page or limit value cannot be less than 1...");
		}
	}
}
