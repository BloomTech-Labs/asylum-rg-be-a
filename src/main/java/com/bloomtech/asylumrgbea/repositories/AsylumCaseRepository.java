package com.bloomtech.asylumrgbea.repositories;

import com.bloomtech.asylumrgbea.entities.AsylumCase;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsylumCaseRepository extends PagingAndSortingRepository<AsylumCase, Integer> {
}
