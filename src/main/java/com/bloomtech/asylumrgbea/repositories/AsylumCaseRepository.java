package com.bloomtech.asylumrgbea.repositories;

import com.bloomtech.asylumrgbea.entities.AsylumCase;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableScan
@EnableScanCount
public interface AsylumCaseRepository extends DynamoDBPagingAndSortingRepository<AsylumCase, Integer> {

}
